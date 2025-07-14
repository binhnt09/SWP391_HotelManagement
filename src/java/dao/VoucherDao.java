/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import entity.MembershipLevel;
import entity.Voucher;
import java.math.BigDecimal;
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
                + " CreatedAt, UpdatedAt, IsDeleted, DeletedBy "
                + " FROM Voucher WHERE VoucherID = ? AND IsDeleted = 0";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, voucherId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractVoucher(rs);
            }
        } catch (SQLException e) {
            Logger.getLogger(VoucherDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public List<Voucher> getAvailableVouchersForUser(int userId) {
        List<Voucher> list = new ArrayList<>();
        String sql = """
                        SELECT DISTINCT v.* FROM [User] u
                            JOIN VoucherLevel vl ON vl.LevelID <= u.LevelID
                            JOIN Voucher v ON v.VoucherID = vl.VoucherID
                        WHERE u.UserID = ?
                            AND (v.ValidFrom IS NULL OR v.ValidFrom <= GETDATE())
                            AND (v.ValidTo IS NULL OR v.ValidTo >= GETDATE()) AND v.IsDeleted = 0
                            AND NOT EXISTS (
                                  SELECT 1 FROM GetVoucher gv
                                  WHERE gv.VoucherID = v.VoucherID AND gv.UserID = u.UserID
                              )
                        UNION
                        SELECT v.* FROM Voucher v
                        WHERE NOT EXISTS (SELECT 1 FROM VoucherLevel vl WHERE vl.VoucherID = v.VoucherID)
                            AND (v.ValidFrom IS NULL OR v.ValidFrom <= GETDATE())
                            AND (v.ValidTo IS NULL OR v.ValidTo >= GETDATE()) AND v.IsDeleted = 0
                            AND NOT EXISTS (
                                SELECT 1 FROM GetVoucher gv
                                WHERE gv.VoucherID = v.VoucherID AND gv.UserID = ?
                            );
                    """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(extractVoucher(rs));
            }
        } catch (SQLException e) {
            Logger.getLogger(VoucherDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    public List<Voucher> getClaimedVouchers(int userId) {
        List<Voucher> list = new ArrayList<>();
        String sql = """
                        SELECT v.* FROM Voucher v
                        	JOIN GetVoucher gv ON gv.VoucherID = v.VoucherID
                        WHERE gv.UserID = ? AND gv.IsUsed = 0 
                            AND (v.ValidFrom IS NULL OR v.ValidFrom <= GETDATE())
                            AND (v.ValidTo IS NULL OR v.ValidTo >= GETDATE())
                            AND v.IsDeleted = 0
                    """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(extractVoucher(rs));
            }
        } catch (SQLException e) {
            Logger.getLogger(VoucherDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    public List<Voucher> getClaimedVouchersIsUsed(int userId) {
        List<Voucher> list = new ArrayList<>();
        String sql = """
                        SELECT v.* FROM Voucher v
                            JOIN GetVoucher gv ON gv.VoucherID = v.VoucherID
                        WHERE gv.UserID = ?
                            AND gv.IsUsed = 1 AND v.IsDeleted = 0
                    """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(extractVoucher(rs));
            }
        } catch (SQLException e) {
            Logger.getLogger(VoucherDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    public List<Voucher> getClaimedVouchersExpired(int userId) {
        List<Voucher> list = new ArrayList<>();
        String sql = """
                        SELECT v.* FROM Voucher v
                            	JOIN GetVoucher gv ON gv.VoucherID = v.VoucherID
                            WHERE gv.UserID = ?
                              AND gv.IsUsed = 0 AND v.IsDeleted = 0
                              AND v.ValidTo IS NOT NULL 
                              AND v.ValidTo < GETDATE()
                    """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(extractVoucher(rs));
            }
        } catch (SQLException e) {
            Logger.getLogger(VoucherDao.class.getName()).log(Level.SEVERE, null, e);
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
            Logger.getLogger(VoucherDao.class.getName()).log(Level.SEVERE, null, e);
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
            Logger.getLogger(VoucherDao.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void insert(int userId, int voucherId) {
        String sql = "INSERT INTO GetVoucher (VoucherID, UserID, ClaimedAt, IsUsed) VALUES (?, ?, GETDATE(), 0)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, voucherId);
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(VoucherDao.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void updateIsused(int userId, int voucherId) {
        String sql = "UPDATE GetVoucher SET IsUsed = 1 WHERE UserID = ? AND VoucherID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, voucherId);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(VoucherDao.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public boolean hasClaimed(int userId, int voucherId) {
        String sql = "SELECT 1 FROM GetVoucher WHERE UserID = ? AND VoucherID = ? AND IsUsed = 1";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, voucherId);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            Logger.getLogger(VoucherDao.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    private Voucher extractVoucher(ResultSet rs) throws SQLException {
        Voucher voucher = new Voucher();
        voucher.setVoucherId(rs.getInt("VoucherID"));
        voucher.setCode(rs.getString("Code"));
        voucher.setDiscountPercentage(rs.getFloat("DiscountPercentage"));
        voucher.setValidFrom(rs.getTimestamp("ValidFrom"));
        voucher.setValidTo(rs.getTimestamp("ValidTo"));
        voucher.setCreatedAt(rs.getTimestamp("CreatedAt"));
        voucher.setUpdatedAt(rs.getTimestamp("UpdatedAt"));
        voucher.setDeletedBy(rs.getInt("DeletedBy"));
        voucher.setIsDeleted(rs.getBoolean("IsDeleted"));
        return voucher;
    }

    //membership
    public MembershipLevel getMembershipByUserId(int userId) {
        MembershipLevel membership = null;
        String sql = """
                        SELECT ml.LevelID, ml.LevelName, ml.MinTotal, ml.CreatedAt, ml.UpdatedAt,
                               ml.DeletedAt, ml.DeletedBy, ml.IsDeleted
                        FROM [User] u
                        JOIN MembershipLevel ml ON u.LevelID = ml.LevelID
                        WHERE u.UserID = ? AND u.IsDeleted = 0 AND ml.IsDeleted = 0
                    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    membership = new MembershipLevel();
                    membership.setLevelId(rs.getInt("LevelID"));
                    membership.setLevelName(rs.getString("LevelName"));
                    membership.setMinTotal(rs.getBigDecimal("MinTotal"));
                    membership.setCreatedAt(rs.getTimestamp("CreatedAt"));
                    membership.setUpdatedAt(rs.getTimestamp("UpdatedAt"));
                    membership.setDeletedAt(rs.getTimestamp("DeletedAt"));
                    membership.setDeletedBy(rs.getObject("DeletedBy") != null ? rs.getInt("DeletedBy") : null);
                    membership.setIsDeleted(rs.getBoolean("IsDeleted"));
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(VoucherDao.class.getName()).log(Level.SEVERE, null, e);
        }

        return membership;
    }

}
