/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import constant.PdfGenerator;
import dal.DBContext;
import entity.Booking;
import entity.BookingDetails;
import entity.BookingServices;
import entity.Invoice;
import entity.InvoiceServiceDetail;
import entity.Payment;
import entity.Room;
import entity.Service;
import entity.User;
import entity.Voucher;
import java.io.File;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Statement;

/**
 *
 * @author ASUS
 */
public class InvoiceDao extends DBContext {

    public int insertInvoice(Invoice invoice) {
        String sql = """
                        INSERT INTO Invoice (
                            PaymentID, BookingID, FirstName, LastName, Email, Phone, Address,
                            VoucherCode, DiscountAmount, RoomNumber, RoomPrice, Nights, TotalRoomPrice,
                            IssueDate, Note, PdfUrl, Status, CreatedAt, UpdatedAt, IsDeleted
                        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), GETDATE(), 0)
                    """;

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, invoice.getPaymentId());
            ps.setInt(2, invoice.getBookingId());
            ps.setString(3, invoice.getFirstName());
            ps.setString(4, invoice.getLastName());
            ps.setString(5, invoice.getEmail());
            ps.setString(6, invoice.getPhone());
            ps.setString(7, invoice.getAddress());
            ps.setString(8, invoice.getVoucherCode());
            ps.setDouble(9, invoice.getDiscountAmount());
            ps.setString(10, invoice.getRoomNumber());
            ps.setDouble(11, invoice.getRoomPrice());
            ps.setInt(12, invoice.getNights());
            ps.setBigDecimal(13, invoice.getTotalRoomPrice());
            ps.setTimestamp(14, invoice.getIssueDate());
            ps.setString(15, invoice.getNote());
            ps.setString(16, invoice.getPdfUrl());
            ps.setString(17, invoice.getStatus());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // Trả về InvoiceID mới được sinh ra
                    }
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(InvoiceDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return -1;
    }

    public void insertInvoiceServiceDetails(int invoiceID, List<InvoiceServiceDetail> serviceDetails) {
        String sql = "INSERT INTO InvoiceServiceDetail (InvoiceID, ServiceName, Price, Quantity, PriceAtUse, UsedAt) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (InvoiceServiceDetail detail : serviceDetails) {
                ps.setInt(1, invoiceID);
                ps.setString(2, detail.getServiceName());
                ps.setBigDecimal(3, detail.getPrice());
                ps.setInt(4, detail.getQuantity());
                ps.setBigDecimal(5, detail.getPriceAtUse());
                ps.setTimestamp(6, detail.getUsedAt());
                ps.addBatch();
            }
            ps.executeBatch(); // Chạy nhiều câu lệnh cùng lúc
        } catch (SQLException e) {
            Logger.getLogger(InvoiceDao.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void updateInvoicePdfUrl(int invoiceId, String pdfUrl) {
        String sql = "UPDATE Invoice SET PdfUrl = ?, UpdatedAt = GETDATE() WHERE InvoiceID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, pdfUrl);
            ps.setInt(2, invoiceId);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(InvoiceDao.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public int generateInvoice(int bookingId) throws Exception {
        int invoiceId = -1;
        try {
            connection.setAutoCommit(false);

            // 1. tạo đối tượng Invoice
            Invoice invoice = buildInvoiceObject(bookingId);

            // 2. insert invoice vào DB
            invoiceId = insertInvoice(invoice);

            // 3. tạo va insert list service vào bảng InvoiceServiceDetail
            insertServiceDetailsForInvoice(invoiceId, bookingId);

            // 4. tạo file PDF và cập nhật đường dẫn PDF
            generateInvoicePdfFile(invoiceId);

            connection.commit();

        } catch (Exception e) {
            Logger.getLogger(InvoiceDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return invoiceId;
    }

    private void generateInvoicePdfFile(int bookingId) throws Exception {
        Invoice createdInvoice = getInvoice(bookingId);
        String folder = PdfGenerator.getInvoiceFolderPathStatic();
        new File(folder).mkdirs();
        String pdfPath = folder + "/" + createdInvoice.getInvoiceId() + ".pdf";

        File pdfFile = new File(pdfPath);
        if (!pdfFile.exists()) {
            PdfGenerator.generateInvoicePdf(createdInvoice, pdfPath);
            updateInvoicePdfUrl(createdInvoice.getInvoiceId(), "/invoices/" + createdInvoice.getInvoiceId() + ".pdf");
        }
    }

    private Invoice buildInvoiceObject(int bookingId) throws Exception {
        BookingDao dao = new BookingDao();
        PaymentDao payDao = new PaymentDao();

        Booking booking = dao.getBookingById(bookingId);
        Payment payment = payDao.getPaymentByBookingId(bookingId);

        if (payment == null) {
            throw new Exception("Không tìm thấy thanh toán cho bookingID = " + bookingId);
        }

        User user = new AuthenticationDAO().findUserById(booking.getUserId());
        Voucher voucher = null;
        if (booking.getVoucherId() != null) {
            voucher = new VoucherDao().getByVoucherId(booking.getVoucherId());
        }

        BookingDetails detail = new BookingDetailDAO().getDetailByBookingId(bookingId);
        if (detail == null) {
            throw new Exception("Không tìm thấy BookingDetail cho bookingId = " + bookingId);
        }
        Room room = new RoomDAO().getRoomByIdDetail(detail.getRoomId());
        if (room == null) {
            throw new Exception("Không tìm thấy Room với ID = " + detail.getRoomId());
        }

        Invoice invoice = new Invoice();
        invoice.setPaymentId(payment.getPaymentId());
        invoice.setBookingId(bookingId);

        invoice.setFirstName(user.getFirstName());
        invoice.setLastName(user.getLastName());
        invoice.setEmail(user.getEmail());
        invoice.setPhone(user.getPhone());
        invoice.setAddress(user.getAddress());

        invoice.setVoucherCode(voucher != null ? voucher.getCode() : null);
        invoice.setDiscountAmount(voucher != null ? voucher.getDiscountPercentage() : 0);

        invoice.setRoomNumber(room.getRoomNumber());
        invoice.setRoomPrice(room.getPrice());
        invoice.setNights(detail.getNights());
        invoice.setTotalRoomPrice(detail.getPrice());

        invoice.setIssueDate(new Timestamp(System.currentTimeMillis()));
        invoice.setNote(null);
        invoice.setPdfUrl(null);
        invoice.setStatus(payment.getStatus());

        return invoice;
    }

    private void insertServiceDetailsForInvoice(int invoiceId, int bookingId) throws Exception {
        List<Service> bookingServices = new PaymentDao().getServicesByBookingId(bookingId);
        List<InvoiceServiceDetail> serviceDetails = new ArrayList<>();

        for (Service bs : bookingServices) {
            Service s = new ServiceDAO().getServiceById(bs.getServiceId());

            InvoiceServiceDetail detail = new InvoiceServiceDetail();
            detail.setInvoiceId(invoiceId);
            detail.setServiceName(s.getName());
            detail.setPrice(s.getPrice());
            detail.setQuantity(1);
                
            serviceDetails.add(detail);
        }

        if (!serviceDetails.isEmpty()) {
            insertInvoiceServiceDetails(invoiceId, serviceDetails);
        }
    }

    public Invoice getInvoice(int invoiceId) {
        Invoice invoice = null;
        List<InvoiceServiceDetail> bookServices = new ArrayList<>();

        String sql = """
                     SELECT 
                        i.InvoiceID, i.PaymentId, i.BookingId, i.FirstName, i.LastName, i.Email, i.Phone, i.Address,
                        i.RoomNumber, i.RoomPrice, i.Nights, i.TotalRoomPrice,
                        i.VoucherCode, i.DiscountAmount, i.IssueDate, i.Note, i.Status,

                        p.PaymentID, p.Amount, p.Method, p.Status as PaymentStatus, p.TransactionCode, p.BankCode,
                        b.BookingID, b.BookingDate, b.CheckInDate, b.CheckOutDate,

                        isd.ID, isd.ServiceName, isd.Price, isd.Quantity, isd.PriceAtUse, isd.UsedAt
                     FROM Invoice i
                        JOIN Payment p ON i.PaymentID = p.PaymentID
                        JOIN Booking b ON b.BookingID = p.BookingID
                        LEFT JOIN InvoiceServiceDetail isd ON isd.InvoiceID = i.InvoiceID
                     WHERE i.InvoiceID = ?""";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, invoiceId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (invoice == null) {
                    invoice = extractInvoiceFromResultSet(rs);
                }

                InvoiceServiceDetail service = extractServiceDetailFromResultSet(rs);
                if (service != null) {
                    bookServices.add(service);
                }
            }
            if (invoice != null) {
                invoice.setListService(bookServices);
            }
        } catch (Exception e) {
            Logger.getLogger(InvoiceDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return invoice;
    }

    public Invoice getInvoiceByPaymentId(int paymentId) {
        Invoice invoice = null;
        List<InvoiceServiceDetail> bookServices = new ArrayList<>();

        String sql = """
                     SELECT 
                        i.InvoiceID, i.PaymentId, i.BookingId, i.FirstName, i.LastName, i.Email, i.Phone, i.Address,
                        i.RoomNumber, i.RoomPrice, i.Nights, i.TotalRoomPrice,
                        i.VoucherCode, i.DiscountAmount, i.IssueDate, i.Note, i.Status,

                        p.PaymentID, p.Amount, p.Method, p.Status as PaymentStatus, p.TransactionCode, p.BankCode,
                        b.BookingID, b.BookingDate, b.CheckInDate, b.CheckOutDate,

                        isd.ID, isd.ServiceName, isd.Price, isd.Quantity, isd.PriceAtUse, isd.UsedAt
                     FROM Invoice i
                        JOIN Payment p ON i.PaymentID = p.PaymentID
                        JOIN Booking b ON b.BookingID = p.BookingID
                        LEFT JOIN InvoiceServiceDetail isd ON isd.InvoiceID = i.InvoiceID
                     WHERE i.PaymentID = ?""";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, paymentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (invoice == null) {
                    invoice = extractInvoiceFromResultSet(rs);
                }

                InvoiceServiceDetail service = extractServiceDetailFromResultSet(rs);
                if (service != null) {
                    bookServices.add(service);
                }
            }
            if (invoice != null) {
                invoice.setListService(bookServices);
            }
        } catch (Exception e) {
            Logger.getLogger(InvoiceDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return invoice;
    }

    public Integer getInvoiceIdByPaymentId(int paymentId) {
        String sql = "SELECT InvoiceID FROM Invoice WHERE PaymentID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, paymentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("InvoiceID");
                }
            }
        } catch (Exception e) {
            Logger.getLogger(InvoiceDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public List<InvoiceServiceDetail> getByInvoiceId(int invoiceId) {
        List<InvoiceServiceDetail> list = new ArrayList<>();
        String sql = "SELECT id, InvoiceId, ServiceName,"
                + " Price, Quantity, PriceAtUse, UsedAt"
                + " FROM InvoiceServiceDetail WHERE InvoiceId = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, invoiceId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    InvoiceServiceDetail detail = new InvoiceServiceDetail();
                    detail.setId(rs.getInt("Id"));
                    detail.setInvoiceId(rs.getInt("InvoiceId"));
                    detail.setServiceName(rs.getString("ServiceName"));
                    detail.setPrice(rs.getBigDecimal("Price"));
                    detail.setQuantity(rs.getInt("Quantity"));
                    detail.setPriceAtUse(rs.getBigDecimal("PriceAtUse"));
                    detail.setUsedAt(rs.getTimestamp("UsedAt"));
                    list.add(detail);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(InvoiceDao.class.getName()).log(Level.SEVERE, null, e);
        }

        return list;
    }

    public Invoice extractInvoiceFromResultSet(ResultSet rs) throws SQLException {
        Invoice invoice = new Invoice();

        invoice.setInvoiceId(rs.getInt("InvoiceID"));
        invoice.setPaymentId(rs.getInt("PaymentId"));
        invoice.setBookingId(rs.getInt("BookingId"));
        invoice.setFirstName(rs.getString("FirstName"));
        invoice.setLastName(rs.getString("LastName"));
        invoice.setEmail(rs.getString("Email"));
        invoice.setPhone(rs.getString("Phone"));
        invoice.setAddress(rs.getString("Address"));
        invoice.setRoomNumber(rs.getString("RoomNumber"));
        invoice.setRoomPrice(rs.getDouble("RoomPrice"));
        invoice.setNights(rs.getInt("Nights"));
        invoice.setTotalRoomPrice(rs.getBigDecimal("TotalRoomPrice"));
        invoice.setVoucherCode(rs.getString("VoucherCode"));
        invoice.setDiscountAmount(rs.getDouble("DiscountAmount"));
        invoice.setIssueDate(rs.getTimestamp("IssueDate"));
        invoice.setNote(rs.getString("Note"));
        invoice.setStatus(rs.getString("Status"));

        Payment payment = new Payment();
        payment.setPaymentId(rs.getInt("PaymentID"));
        payment.setAmount(rs.getBigDecimal("Amount"));
        payment.setMethod(rs.getString("Method"));
        payment.setStatus(rs.getString("PaymentStatus"));
        payment.setTransactionCode(rs.getString("TransactionCode"));
        payment.setBankCode(rs.getString("BankCode"));

        Booking booking = new Booking();
        booking.setBookingId(rs.getInt("BookingID"));
        booking.setBookingDate(rs.getTimestamp("BookingDate"));
        booking.setCheckInDate(rs.getDate("CheckInDate"));
        booking.setCheckOutDate(rs.getDate("CheckOutDate"));

        payment.setBooking(booking);
        invoice.setPayment(payment);

        return invoice;
    }

    public InvoiceServiceDetail extractServiceDetailFromResultSet(ResultSet rs) throws SQLException {
        String serviceName = rs.getString("serviceName");
        if (serviceName == null || serviceName.isEmpty()) {
            return null;
        }

        InvoiceServiceDetail service = new InvoiceServiceDetail();
        service.setId(rs.getInt("ID"));
        service.setServiceName(serviceName);
        service.setPrice(rs.getBigDecimal("Price"));
        service.setQuantity(rs.getInt("Quantity"));
        service.setPriceAtUse(rs.getBigDecimal("PriceAtUse"));
        service.setUsedAt(rs.getTimestamp("UsedAt"));
        return service;
    }

}
