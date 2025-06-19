/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import entity.Payment;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

}
