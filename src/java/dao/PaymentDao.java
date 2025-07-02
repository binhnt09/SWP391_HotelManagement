/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import entity.Booking;
import entity.BookingDetails;
import entity.BookingServices;
import entity.Invoice;
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

    public int insertPayment(Payment payment) {
        String sql = "INSERT INTO [dbo].[Payment] "
                + "([BookingID], [Amount], [Method], [Status], [TransactionCode], [BankCode], [GatewayResponse], [QRRef], [CreatedAt], [UpdatedAt], [IsDeleted]) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), GETDATE(), 0)";
        try (PreparedStatement st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setInt(1, payment.getBookingID());
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
            st.setInt(2, payment.getPaymentID());
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.getLogger(PaymentDao.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public Invoice getInvoice(int bookingId) {
        Invoice invoice = new Invoice();
        List<BookingServices> bookServices = new ArrayList<>();

        String sql = "SELECT \n"
                + "	b.BookingID, u.FirstName, u.LastName, u.Email, u.phone, r.RoomID, r.RoomNumber, r.Price AS RoomPrice, \n"
                + "	bd.BookingDetailID, bd.Nights, s.ServiceID, s.Name AS ServiceName, s.Category, s.Price AS ServicePrice,\n"
                + "	bs.BookingServiceID, bs.Quantity, bs.PriceAtUse, bs.UsedAt, v.VoucherID, v.Code, v.DiscountPercentage,\n"
                + "	v.ValidFrom, v.ValidTo, p.PaymentID, p.Amount, p.Method, p.Status, p.TransactionCode,\n"
                + "	p.BankCode, b.BookingDate, b.CheckInDate, b.CheckOutDate\n"
                + "FROM Booking b\n"
                + "	JOIN BookingDetail bd ON bd.BookingID = b.BookingID\n"
                + "	JOIN Room r ON r.RoomID = bd.RoomID\n"
                + "	JOIN Payment p ON p.BookingID = b.BookingID\n"
                + "	JOIN [User] u ON u.UserID = b.UserID\n"
                + "	LEFT JOIN Voucher v ON v.VoucherID = b.VoucherID\n"
                + "	LEFT JOIN BookingService bs ON bs.BookingID = b.BookingID AND bs.IsDeleted = 0\n"
                + "	LEFT JOIN [Service] s ON s.ServiceID = bs.ServiceID\n"
                + "WHERE b.BookingID = ?";

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
                    // BookingDetail
//                    detail.getRoom().setRoomID(rs.getInt("RoomID"));
//                    detail.getRoom().setRoomNumber(rs.getString("RoomNumber"));
//                    detail.getRoom().setPrice(rs.getDouble("RoomPrice"));
                    detail.setBookingDetailId(rs.getInt("BookingServiceID"));
                    detail.setNights(rs.getInt("Nights"));
                    invoice.setBookingDetails(detail);

                    // Payment
                    Payment payment = new Payment();
                    payment.setPaymentID(rs.getInt("PaymentID"));
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
}
