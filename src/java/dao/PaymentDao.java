/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import entity.Booking;
import entity.BookingDetails;
import entity.BookingServices;
import entity.Invoices;
import entity.Payment;
import entity.Room;
import entity.Service;
import entity.User;
import entity.Voucher;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class PaymentDao extends DBContext {

    public List<Payment> getAllPayments() {
        List<Payment> list = new ArrayList<>();

        String sql = """
                     SELECT p.PaymentID, p.BookingID, p.Method, p.TransactionCode, p.BankCode, p.Amount, p.Status,  
                     p.CreatedAt, p.UpdatedAt, p.DeletedBy, p.IsDeleted
                     FROM Payment p
                     WHERE p.IsDeleted = 0
                     ORDER BY p.CreatedAt DESC;""";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(extractPayment(rs));
            }
        } catch (SQLException e) {
            Logger.getLogger(PaymentDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    public List<Payment> getAllPaymentsByUserId(int UserId) {
        List<Payment> list = new ArrayList<>();

        String sql = """
                     SELECT p.PaymentID, p.BookingID, p.Method, p.BankCode,
                        p.TransactionCode, p.Amount, p.Status, p.CreatedAt,
                        p.UpdatedAt, p.DeletedBy, p.IsDeleted
                     FROM Payment p
                        JOIN Booking b ON p.BookingID = b.BookingID
                     WHERE b.UserID = ? AND p.IsDeleted = 0
                     ORDER BY p.CreatedAt DESC;""";

        try (PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setInt(1, UserId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(extractPayment(rs));
            }
        } catch (SQLException e) {
            Logger.getLogger(PaymentDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    public int insertPayment(Payment payment) {
        String sql = "INSERT INTO [dbo].[Payment] "
                + "([BookingID], [Amount], [Method], [Status], [TransactionCode], [BankCode], [GatewayResponse], [QRRef], [CreatedAt], [UpdatedAt], [IsDeleted]) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), GETDATE(), 0)";
        try (PreparedStatement st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setInt(1, payment.getBookingId());
            st.setBigDecimal(2, payment.getAmount());
            st.setString(3, payment.getMethod());
            st.setString(4, payment.getStatus());
            st.setString(5, payment.getTransactionCode());
            st.setString(6, payment.getBankCode());
            st.setString(7, payment.getGatewayResponse());
            st.setString(8, payment.getQrRef());

            int affectedRows = st.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating payment failed, no rows affected.");
            }

            try (ResultSet generatedKeys = st.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Trả về PaymentID vừa tạo
                } else {
                    throw new SQLException("Creating payment failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(PaymentDao.class.getName()).log(Level.SEVERE, null, e);
            return -1;
        }
    }

    public boolean updatePaymentStatus(Payment payment) {
        String sql = "UPDATE [dbo].[Payment] SET [Status] = ?, [UpdatedAt] = GETDATE() WHERE [PaymentID] = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, payment.getStatus());
            st.setInt(2, payment.getPaymentId());
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.getLogger(PaymentDao.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public Payment getPaymentByBookingId(int bookingId) {
        String sql = """
                     SELECT p.PaymentID, p.BookingID, p.Method, p.TransactionCode, p.BankCode, p.Amount, p.Status,
                            p.CreatedAt, p.UpdatedAt, p.DeletedBy, p.IsDeleted, b.BookingID 
                     FROM Payment p 
                        JOIN Booking b ON p.BookingID = b.BookingID
                     WHERE p.BookingID = ? AND p.IsDeleted = 0""";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractPayment(rs);
            }
        } catch (SQLException e) {
            Logger.getLogger(PaymentDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public Invoices getInvoices(int bookingId) {
        Invoices invoice = new Invoices();
        List<BookingServices> bookServices = new ArrayList<>();

        String sql = """
                     SELECT 
                     \tb.BookingID, u.FirstName, u.LastName, u.Email, u.phone, r.RoomID, r.RoomNumber, r.Price AS RoomPrice, 
                     \tbd.BookingDetailID, bd.Nights, s.ServiceID, s.Name AS ServiceName, s.Category, s.Price AS ServicePrice,
                     \tbs.BookingServiceID, bs.Quantity, bs.PriceAtUse, bs.UsedAt, v.VoucherID, v.Code, v.DiscountPercentage,
                     \tv.ValidFrom, v.ValidTo, p.PaymentID, p.Amount, p.Method, p.Status, p.TransactionCode,
                     \tp.BankCode, b.BookingDate, b.CheckInDate, b.CheckOutDate
                     FROM Booking b
                     \tJOIN BookingDetail bd ON bd.BookingID = b.BookingID
                     \tJOIN Room r ON r.RoomID = bd.RoomID
                     \tJOIN Payment p ON p.BookingID = b.BookingID
                     \tJOIN [User] u ON u.UserID = b.UserID
                     \tLEFT JOIN Voucher v ON v.VoucherID = b.VoucherID
                     \tLEFT JOIN BookingService bs ON bs.BookingID = b.BookingID AND bs.IsDeleted = 0
                     \tLEFT JOIN [Service] s ON s.ServiceID = bs.ServiceID
                     WHERE b.BookingID = ?""";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Chỉ cần tạo 1 lần
                if (invoice.getBooking() == null) {
                    // Booking
                    Booking booking = new Booking();
                    booking.setBookingId(rs.getInt("BookingID"));
                    booking.setBookingDate(rs.getTimestamp("BookingDate"));
                    booking.setCheckInDate(rs.getDate("CheckInDate"));
                    booking.setCheckOutDate(rs.getDate("CheckOutDate"));

                    int voucherId = rs.getInt("voucherID");
                    if (voucherId > 0) {
                        Voucher voucher = new Voucher();
                        voucher.setVoucherId(voucherId);
                        voucher.setCode(rs.getString("code"));
                        voucher.setDiscountPercentage(rs.getDouble("discountPercentage"));
                        voucher.setValidFrom(rs.getTimestamp("validFrom"));
                        voucher.setValidTo(rs.getTimestamp("validTo"));

                        booking.setVoucher(voucher);
                    }
                    invoice.setBooking(booking);

                    // User
                    User user = new User();
                    user.setFirstName(rs.getString("FirstName"));
                    user.setLastName(rs.getString("LastName"));
                    user.setEmail(rs.getString("Email"));
                    user.setPhone(rs.getString("Phone"));
                    invoice.setUser(user);

                    // Room
                    Room room = new Room();
                    BookingDetails detail = new BookingDetails();
                    room.setRoomID(rs.getInt("RoomID"));
                    room.setRoomNumber(rs.getString("RoomNumber"));
                    room.setPrice(rs.getDouble("RoomPrice"));
                    detail.setRoom(room);

                    detail.setBookingDetailId(rs.getInt("BookingServiceID"));
                    detail.setNights(rs.getInt("Nights"));
                    invoice.setBookingDetails(detail);

                    // Payment
                    Payment payment = new Payment();
                    payment.setPaymentId(rs.getInt("PaymentID"));
                    payment.setAmount(rs.getBigDecimal("Amount"));
                    payment.setMethod(rs.getString("Method"));
                    payment.setStatus(rs.getString("Status"));
                    payment.setTransactionCode(rs.getString("TransactionCode"));
                    payment.setBankCode(rs.getString("BankCode"));
                    invoice.setPayment(payment);
                }

                // Nếu có dịch vụ
                int serviceId = rs.getInt("ServiceID");
                if (serviceId > 0) {
                    Service service = new Service();
                    service.setServiceId(serviceId);
                    service.setName(rs.getString("ServiceName"));
                    service.setCategory(rs.getString("Category"));
                    service.setPrice(rs.getBigDecimal("ServicePrice"));

                    BookingServices bs = new BookingServices();
                    bs.setBookingServiceId(rs.getInt("BookingServiceID"));
                    bs.setQuantity(rs.getInt("Quantity"));
                    bs.setPriceAtUse(rs.getBigDecimal("PriceAtUse"));
                    bs.setUsedAt(rs.getTimestamp("UsedAt"));
                    bs.setIsPreOrdered(rs.getBoolean("IsPreOrdered"));
                    bs.setService(service);

                    bookServices.add(bs);
                }
            }
            invoice.setBookingServices(bookServices);
        } catch (Exception e) {
            Logger.getLogger(PaymentDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return invoice;
    }

    public List<BookingServices> getBookingServiceByBookingId(int bookingId) {
        List<BookingServices> list = new ArrayList<>();
        String sql = "SELECT BookingServiceID, BookingID, ServiceID, Quantity, PriceAtUse, UsedAt, "
                + " IsPreOrdered, CreatedAt, UpdatedAt, DeletedAt, DeletedBy, IsDeleted"
                + " FROM BookingService WHERE BookingID = ? AND IsDeleted = 0";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                BookingServices bs = new BookingServices();
                bs.setBookingServiceId(rs.getInt("BookingServiceID"));
                bs.setBookingId(rs.getInt("BookingID"));
                bs.setServiceId(rs.getInt("ServiceID"));
                bs.setQuantity(rs.getInt("Quantity"));
                bs.setPriceAtUse(rs.getBigDecimal("PriceAtUse"));
                bs.setUsedAt(rs.getTimestamp("UsedAt"));
                bs.setIsPreOrdered(rs.getBoolean("IsPreOrdered"));
                bs.setCreatedAt(rs.getTimestamp("CreatedAt"));
                bs.setUpdatedAt(rs.getTimestamp("UpdatedAt"));
                bs.setDeletedBy(rs.getInt("DeletedBy"));
                bs.setIsDeleted(rs.getBoolean("IsDeleted"));

                list.add(bs);
            }
        } catch (Exception e) {
            Logger.getLogger(PaymentDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    private Payment extractPayment(ResultSet rs) throws SQLException {
        Payment p = new Payment();
        p.setPaymentId(rs.getInt("PaymentID"));
        p.setBookingId(rs.getInt("BookingID"));
        p.setMethod(rs.getString("Method"));
        p.setTransactionCode(rs.getString("TransactionCode"));
        p.setBankCode(rs.getString("BankCode"));
        p.setAmount(rs.getBigDecimal("Amount"));
        p.setStatus(rs.getString("Status"));
        p.setCreatedAt(rs.getTimestamp("CreatedAt"));
        p.setUpdatedAt(rs.getTimestamp("UpdatedAt"));
        p.setDeletedBy(rs.getInt("DeletedBy"));
        p.setIsDeleted(rs.getBoolean("IsDeleted"));
        return p;
    }
}
