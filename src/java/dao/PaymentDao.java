/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import entity.BookingServices;
import entity.Payment;
import java.math.BigDecimal;
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
    
    public BigDecimal calculateDiscountedTotal(BigDecimal baseAmount, Integer voucherId) {
        BigDecimal autoDiscount = BigDecimal.ZERO;
        BigDecimal voucherDiscount = BigDecimal.ZERO;

        // Auto discount
//        if (baseAmount.compareTo(new BigDecimal("500000")) >= 0) {
//            autoDiscount = baseAmount.multiply(new BigDecimal("0.10"));
//        }

        // Voucher discount nếu có
        if (voucherId != null) {
            String sql = """
                SELECT DiscountPercentage FROM Voucher
                WHERE VoucherID = ? AND IsDeleted = 0
                  AND (ValidFrom IS NULL OR ValidFrom <= GETDATE())
                  AND (ValidTo IS NULL OR ValidTo >= GETDATE())
            """;
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, voucherId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    BigDecimal percent = rs.getBigDecimal("DiscountPercentage");
                    voucherDiscount = baseAmount.multiply(percent).divide(new BigDecimal("100"));

                }
            } catch (SQLException e) {
                Logger.getLogger(PaymentDao.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return baseAmount.subtract(autoDiscount).subtract(voucherDiscount);
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