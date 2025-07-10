/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import entity.Voucher;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class VoucherDao extends DBContext {

    public Voucher getByVoucherId(int voucherId) {
        String sql = "SELECT VoucherID, Code, DiscountPercentage, ValidFrom, ValidTo, "
                + " CreatedAt, UpdatedAt "
                + " FROM Voucher WHERE VoucherID = ? AND IsDeleted = 0";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, voucherId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Voucher v = new Voucher();
                v.setVoucherId(rs.getInt("VoucherID"));
                v.setCode(rs.getString("Code"));
                v.setDiscountPercentage(rs.getDouble("DiscountPercentage"));
                v.setValidFrom(rs.getTimestamp("ValidFrom"));
                v.setValidTo(rs.getTimestamp("ValidTo"));
                v.setCreatedAt(rs.getTimestamp("CreatedAt"));
                v.setUpdatedAt(rs.getTimestamp("UpdatedAt"));

                return v;
            }
        } catch (SQLException e) {
            Logger.getLogger(PaymentDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public List<Voucher> getAvailableVouchersForUser(int userId) {
        List<Voucher> list = new ArrayList<>();

        String sql = """
                        SELECT v.*
                        FROM [User] u
                            JOIN VoucherLevel vl ON vl.LevelID = u.LevelID
                            JOIN Voucher v ON v.VoucherID = vl.VoucherID
                        WHERE u.UserID = ?
                            AND (v.ValidFrom IS NULL OR v.ValidFrom <= GETDATE())
                            AND (v.ValidTo IS NULL OR v.ValidTo >= GETDATE())
                            AND v.IsDeleted = 0
                    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Voucher v = new Voucher();
                v.setVoucherId(rs.getInt("VoucherID"));
                v.setCode(rs.getString("Code"));
                v.setDiscountPercentage(rs.getFloat("DiscountPercentage"));
                v.setValidFrom(rs.getTimestamp("ValidFrom"));
                v.setValidTo(rs.getTimestamp("ValidTo"));
                list.add(v);
            }
        } catch (SQLException e) {
            Logger.getLogger(PaymentDao.class.getName()).log(Level.SEVERE, null, e);
        }

        return list;
    }

    public double getTotalPaidByUser(int userId) {
        String sql = """
                        SELECT ISNULL(SUM(p.Amount), 0)
                        FROM Payment p
                            JOIN Booking b ON p.BookingID = b.BookingID
                        WHERE b.UserID = ? AND p.Status = 'Paid'
                    """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(PaymentDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    public void updateUserMembershipLevel(int userId) {
        String sql = """
                        UPDATE [User]
                        SET LevelID = (
                            SELECT TOP 1 CL.LevelID
                            FROM MembershipLevel CL
                            WHERE CL.MinTotal <= (
                                SELECT ISNULL(SUM(p.Amount), 0)
                                FROM Payment p
                                JOIN Booking b ON p.BookingID = b.BookingID
                                WHERE b.UserID = ? AND p.Status = 'Paid'
                            )
                            ORDER BY CL.MinTotal DESC
                        )
                        WHERE UserID = ?
                    """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(PaymentDao.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
