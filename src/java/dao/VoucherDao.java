/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import entity.LevelUser;
import entity.Voucher;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class VoucherDao extends DBContext {

    public List<Voucher> getAllVoucher() {
        List<Voucher> list = new ArrayList<>();

        String sql = "SELECT VoucherID, Code, DiscountPercentage, ValidFrom, ValidTo, "
                + " CreatedAt, UpdatedAt, IsDeleted, DeletedBy "
                + " FROM Voucher ORDER BY CreatedAt DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(extractVoucher(rs));
            }
        } catch (SQLException e) {
            Logger.getLogger(VoucherDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

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

    //lấy voucher mà level user nhận được
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
                                  SELECT 1 FROM ClaimVoucher gv
                                  WHERE gv.VoucherID = v.VoucherID AND gv.UserID = u.UserID
                              )
                        UNION
                        SELECT v.* FROM Voucher v
                        WHERE NOT EXISTS (SELECT 1 FROM VoucherLevel vl WHERE vl.VoucherID = v.VoucherID)
                            AND (v.ValidFrom IS NULL OR v.ValidFrom <= GETDATE())
                            AND (v.ValidTo IS NULL OR v.ValidTo >= GETDATE()) AND v.IsDeleted = 0
                            AND NOT EXISTS (
                                SELECT 1 FROM ClaimVoucher gv
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

    //lấy voucher mà user đã claim về 
    public List<Voucher> getClaimedVouchers(int userId) {
        List<Voucher> list = new ArrayList<>();
        String sql = """
                        SELECT v.* FROM Voucher v
                        	JOIN ClaimVoucher gv ON gv.VoucherID = v.VoucherID
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

    //voucher mà user đã claim về và đã sử dụng
    public List<Voucher> getClaimedVouchersIsUsed(int userId) {
        List<Voucher> list = new ArrayList<>();
        String sql = """
                        SELECT v.* FROM Voucher v
                            JOIN ClaimVoucher gv ON gv.VoucherID = v.VoucherID
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

    //voucher user đã claim về và đã hết hạn
    public List<Voucher> getClaimedVouchersExpired(int userId) {
        List<Voucher> list = new ArrayList<>();
        String sql = """
                        SELECT v.* FROM Voucher v
                            	JOIN ClaimVoucher gv ON gv.VoucherID = v.VoucherID
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

    //tính tổng tiền user đã chi tiêu
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

    //tự động tính tổng tiền user sau mỗi lần thanh toán thành công trên hệ thống
    public void updateUserLevelUser(int userId) {
        String sql = """
                        UPDATE [User]
                        SET LevelID = (
                            SELECT TOP 1 CL.LevelID
                            FROM LevelUser CL
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
        String sql = "INSERT INTO ClaimVoucher (VoucherID, UserID, ClaimedAt, IsUsed) VALUES (?, ?, GETDATE(), 0)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, voucherId);
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(VoucherDao.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    //update voucher đã sử dụng với mỗi user
    public void updateIsused(int userId, int voucherId) {
        String sql = "UPDATE ClaimVoucher SET IsUsed = 1 WHERE UserID = ? AND VoucherID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, voucherId);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(VoucherDao.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    //kiểm tra voucher đã được claim chưa
    public boolean hasClaimed(int userId, int voucherId) {
        String sql = "SELECT 1 FROM ClaimVoucher WHERE UserID = ? AND VoucherID = ? AND IsUsed = 1";
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

    //Đếm số lượng voucher sau khi search theo code hoặc tính tổng sl voucher
    public int countSearchResults(String keyword) {
        String sql = "SELECT COUNT(*) FROM Voucher WHERE IsDeleted = 0";
        boolean hasKeyword = keyword != null && !keyword.trim().isEmpty();
        if (hasKeyword) {
            sql += " AND Code LIKE ? ";
        }
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            String searchParam = "%" + keyword + "%";
            if (hasKeyword) {
                stm.setString(1, searchParam);
            }
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1); // Trả về số lượng bản ghi tìm được
            }
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    //seach and sort
    public List<Voucher> searchOrSortVoucher(String searchVoucher, String sortBy, boolean isDecreasing, int start) {
        List<Voucher> list = new ArrayList<>();
        boolean hasKeyword = searchVoucher != null && !searchVoucher.trim().isEmpty();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT VoucherID, Code, DiscountPercentage, ValidFrom, ValidTo, ")
                .append("CreatedAt, UpdatedAt, IsDeleted, DeletedBy FROM Voucher WHERE IsDeleted = 0 ");

        if (hasKeyword) {
            sql.append(" AND Code LIKE ? ");
        }

        if (sortBy != null) {
            sql.append(" ORDER BY ");
            switch (sortBy) {
                case "Code" ->
                    sql.append("Code");
                case "DiscountPercentage" ->
                    sql.append("DiscountPercentage");
                case "validFrom" ->
                    sql.append("validFrom");
                case "validTo" ->
                    sql.append("validTo");
                case "CreatedAt" ->
                    sql.append("CreatedAt");
                default ->
                    sql.append(sortBy);
            }
            if (isDecreasing) {
                sql.append(" DESC");
            }
        } else {
            sql.append(" ORDER BY CreatedAt DESC"); // Tránh lỗi nếu `sortBy` null
        }

        sql.append(" OFFSET ? ROWS FETCH NEXT 6 ROWS ONLY");

        try (PreparedStatement stm = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            if (hasKeyword) {
                stm.setString(paramIndex++, "%" + searchVoucher + "%");
            }
            stm.setInt(paramIndex, (start - 1) * 6);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.add(extractVoucher(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void insertVoucher(String code, double discount, Date validFrom, Date validTo) {
        String sql = "INSERT INTO Voucher (Code, DiscountPercentage, ValidFrom, ValidTo) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, code);
            stmt.setDouble(2, discount);
            stmt.setDate(3, validFrom);
            stmt.setDate(4, validTo);

            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean updateVoucher(Voucher voucher) {
        String sql = "UPDATE Voucher SET Code = ?, DiscountPercentage = ?, ValidFrom = ?, ValidTo = ? WHERE VoucherId = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, voucher.getCode());
            ps.setDouble(2, voucher.getDiscountPercentage());
            ps.setDate(3, voucher.getValidFrom());
            ps.setDate(4, voucher.getValidTo());
            ps.setInt(5, voucher.getVoucherId());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            Logger.getLogger(VoucherDao.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean updateIsdeletedVoucher(int voucherId) {
        String sql = "update Voucher set IsDeleted = 1 WHERE VoucherID = ?";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, voucherId);
            int rowsAffected = stm.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            Logger.getLogger(VoucherDao.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public int getVoucherIdByCode(String code) {
        String sql = "SELECT VoucherID FROM Voucher WHERE Code = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, code);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("VoucherID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public void insertVoucherLevels(String code, String levelUser) {
        int voucherId = getVoucherIdByCode(code);

        String[] levelIds = levelUser.split(", ");

        String sql = "INSERT INTO VoucherLevel (VoucherID, LevelID) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            for (String levelIdStr : levelIds) {
                int levelId = Integer.parseInt(levelIdStr.trim());
                stmt.setInt(1, voucherId);
                stmt.setInt(2, levelId);
                stmt.addBatch();
            }
            stmt.executeBatch(); // execute all at once
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isDuplicateVoucher(String code, double discount, Date validFrom, Date validTo) {
        String sql = """
                     SELECT VoucherID FROM Voucher WHERE Code = ? AND DiscountPercentage = ?
                        AND ValidFrom = ? AND ValidTo = ? AND IsDeleted = 0
                     """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, code);
            ps.setDouble(2, discount);
            ps.setDate(3, validFrom);
            ps.setDate(4, validTo);

            ResultSet rs = ps.executeQuery();
            return rs.next(); // có ít nhất 1 bản ghi trùng
        } catch (SQLException e) {
            Logger.getLogger(VoucherDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    private Voucher extractVoucher(ResultSet rs) throws SQLException {
        Voucher voucher = new Voucher();
        voucher.setVoucherId(rs.getInt("VoucherID"));
        voucher.setCode(rs.getString("Code"));
        voucher.setDiscountPercentage(rs.getFloat("DiscountPercentage"));
        voucher.setValidFrom(rs.getDate("ValidFrom"));
        voucher.setValidTo(rs.getDate("ValidTo"));
        voucher.setCreatedAt(rs.getTimestamp("CreatedAt"));
        voucher.setUpdatedAt(rs.getTimestamp("UpdatedAt"));
        voucher.setDeletedBy(rs.getInt("DeletedBy"));
        voucher.setIsDeleted(rs.getBoolean("IsDeleted"));
        return voucher;
    }

    //LevelUser
    public LevelUser getLevelUserByUserId(int userId) {
        String sql = """
                        SELECT ml.LevelID, ml.LevelName, ml.MinTotal, ml.CreatedAt, ml.UpdatedAt,
                               ml.DeletedAt, ml.DeletedBy, ml.IsDeleted
                        FROM [User] u
                        JOIN LevelUser ml ON u.LevelID = ml.LevelID
                        WHERE u.UserID = ? AND u.IsDeleted = 0 AND ml.IsDeleted = 0
                    """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractLevelUser(rs);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(VoucherDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public List<LevelUser> getAllLevelUser() {
        List<LevelUser> list = new ArrayList<>();
        String sql = """
                        SELECT ml.LevelID, ml.LevelName, ml.MinTotal, ml.CreatedAt, ml.UpdatedAt,
                                ml.DeletedAt, ml.DeletedBy, ml.IsDeleted
                        FROM LevelUser ml 
                        WHERE ml.IsDeleted = 0
                    """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(extractLevelUser(rs));
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(VoucherDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    private LevelUser extractLevelUser(ResultSet rs) throws SQLException {
        LevelUser levelUser = new LevelUser();
        levelUser.setLevelId(rs.getInt("LevelID"));
        levelUser.setLevelName(rs.getString("LevelName"));
        levelUser.setMinTotal(rs.getBigDecimal("MinTotal"));
        levelUser.setCreatedAt(rs.getTimestamp("CreatedAt"));
        levelUser.setUpdatedAt(rs.getTimestamp("UpdatedAt"));
        levelUser.setDeletedAt(rs.getTimestamp("DeletedAt"));
        levelUser.setDeletedBy(rs.getObject("DeletedBy") != null ? rs.getInt("DeletedBy") : null);
        levelUser.setIsDeleted(rs.getBoolean("IsDeleted"));
        return levelUser;
    }
}
