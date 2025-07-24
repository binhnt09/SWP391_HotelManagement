/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import entity.Authentication;
import entity.User;
import java.sql.Timestamp;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author ASUS
 */
public class AuthenticationDAO extends DBContext {

    //hàm hash lại password nếu insert chưa hash
    public void hashAllPlaintextPasswords() {
        String selectSQL = "SELECT AuthenticationID, Password FROM Authentication";
        String updateSQL = "UPDATE Authentication SET Password = ? WHERE AuthenticationID = ?";

        try {
            PreparedStatement selectStmt = connection.prepareStatement(selectSQL);
            ResultSet rs = selectStmt.executeQuery();
            PreparedStatement updateStmt = connection.prepareStatement(updateSQL);
            while (rs.next()) {
                int id = rs.getInt("AuthenticationID");
                String currentPass = rs.getString("Password");

                // Nếu đã hash rồi thì bỏ qua
                if (currentPass.startsWith("$2a$") || currentPass.startsWith("$2b$") || currentPass.startsWith("$2y$")) {
                    Logger.getLogger(AuthenticationDAO.class.getName()).log(Level.INFO, "\u2757 ID {0} \u0111\u00e3 hash, b\u1ecf qua.", id);
                    continue;
                }

                String hashed = BCrypt.hashpw(currentPass, BCrypt.gensalt(12));
                updateStmt.setString(1, hashed);
                updateStmt.setInt(2, id);
                updateStmt.executeUpdate();

                Logger.getLogger(AuthenticationDAO.class.getName()).log(Level.INFO, "\u2714 \u0110\u00e3 hash cho ID: {0}", id);
            }

        } catch (SQLException e) {
            Logger.getLogger(AuthenticationDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public Authentication login(String email) {
        String sql = "SELECT a.AuthenticationID, a.UserKey, a.Password, a.AuthType, a.CreatedAt, a.UpdatedAt, a.DeletedAt, a.DeletedBy, a.IsDeleted,  "
                + " u.UserID, u.FirstName, u.LastName, u.Email, u.Phone, u.Sex, u.BirthDay, u.Address, u.CreatedAt, u.UpdatedAt, u.DeletedAt, u.DeletedBy, u.IsDeleted, u.IsVerifiedEmail, u.UserRoleID "
                + " FROM Authentication a "
                + " JOIN [User] u ON a.UserID = u.UserID"
                + " WHERE u.Email = ? AND a.AuthType = 'local' AND a.IsDeleted = 0 AND u.IsDeleted = 0";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                User user = new User(
                        rs.getInt("UserID"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Email"),
                        rs.getString("Phone"),
                        rs.getString("Sex"),
                        rs.getTimestamp("BirthDay"),
                        rs.getString("Address"),
                        rs.getTimestamp("CreatedAt"),
                        rs.getTimestamp("UpdatedAt"),
                        rs.getTimestamp("DeletedAt"),
                        (Integer) rs.getObject("DeletedBy"),
                        rs.getBoolean("IsDeleted"),
                        rs.getBoolean("IsVerifiedEmail"),
                        rs.getInt("UserRoleID")
                );

                Authentication auth = new Authentication(
                        rs.getInt("AuthenticationID"),
                        null,
                        rs.getString("Password"),
                        rs.getString("AuthType"),
                        rs.getTimestamp("CreatedAt"),
                        rs.getTimestamp("UpdatedAt"),
                        rs.getTimestamp("DeletedAt"),
                        (Integer) rs.getObject("DeletedBy"),
                        rs.getBoolean("IsDeleted"),
                        user
                );

                return auth;
            }

        } catch (SQLException e) {
            Logger.getLogger(AuthenticationDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public boolean isValidLogin(String email, String pass) {
        Authentication auth = login(email);
        if (auth == null) {
            return false;
        }
        return BCrypt.checkpw(pass, auth.getPassword());
    }

    public Authentication findAuthTypeByEmail(String email) {
        Authentication auth = null;
        String sql = "SELECT a.AuthenticationID, a.AuthType, a.IsDeleted,  "
                + " u.UserID,  u.IsDeleted FROM Authentication a "
                + " JOIN [User] u ON a.UserID = u.UserID"
                + " WHERE u.Email = ? AND a.IsDeleted = 0 AND u.IsDeleted = 0";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                auth = new Authentication();
                auth.setAuthenticationID(rs.getInt("AuthenticationID"));
                auth.setAuthType(rs.getString("AuthType"));
            }

        } catch (SQLException e) {
            Logger.getLogger(AuthenticationDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return auth;
    }

    public boolean existEmail(String email) {
        String sql = "SELECT email FROM [User] WHERE Email = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, email);
            ResultSet rs = st.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            Logger.getLogger(AuthenticationDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    public boolean existPhone(String phone, int userId) {
        String sql = "SELECT UserId, phone FROM [User] WHERE phone = ?";

        if (userId > 0) {
            sql += " AND UserId != ? ";
        }
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, phone);
            if (userId > 0) {
                st.setInt(2, userId);
            }
            ResultSet rs = st.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            Logger.getLogger(AuthenticationDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    public void insertVerification(String email, String code) {
        String sql = "INSERT INTO EmailVerification(Email, Code, ExpiredAt) VALUES (?, ?, DATEADD(SECOND, 60, GETDATE()))";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, code);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(AuthenticationDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public boolean verifyCode(String email, String code) {
        String sql = "SELECT VerificationID FROM EmailVerification WHERE Email = ? AND Code = ? AND IsUsed = 0 AND ExpiredAt > GETDATE()";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                markUsed(rs.getInt("VerificationID"));
                return true;
            }
        } catch (SQLException e) {
            Logger.getLogger(AuthenticationDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    public boolean verifyCodeForgotPass(String email, String code, String purpose) {
        String sql = "SELECT VerificationID FROM EmailVerification WHERE Email = ? AND Code = ? AND Purpose = ? AND IsUsed = 0 AND ExpiredAt > GETDATE()";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, code);
            ps.setString(3, purpose);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                markUsed(rs.getInt("VerificationID"));
                return true;
            }
        } catch (SQLException e) {
            Logger.getLogger(AuthenticationDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    public void resetPasswordByEmail(String email, String newPassword) {
        String sql = "UPDATE Authentication SET Password = ? WHERE UserID = (SELECT UserID FROM [User] WHERE Email = ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

            ps.setString(1, hashedPassword);
            ps.setString(2, email);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(AuthenticationDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public boolean changePassword(int userId, String oldPassword, String newPassword) {
        String currentHashedPassword = null;
        String getHashSql = "SELECT Password FROM Authentication WHERE UserID = ?";

        try (PreparedStatement ps = connection.prepareStatement(getHashSql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                currentHashedPassword = rs.getString("Password");
            } else {
                return false;
            }
        } catch (SQLException e) {
            Logger.getLogger(AuthenticationDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }

        // So sánh mật khẩu cũ
        if (!BCrypt.checkpw(oldPassword, currentHashedPassword)) {
            return false;
        }

        // Cập nhật mật khẩu mới
        String updateSql = "UPDATE Authentication SET Password = ?, UpdatedAt = GETDATE() WHERE UserID = ?";
        try (PreparedStatement ps = connection.prepareStatement(updateSql)) {
            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            ps.setString(1, hashedPassword);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.getLogger(AuthenticationDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

//    change google account password
//    public boolean setPasswordForGoogleUser(int userId, String newPassword) {
//        String sql = "UPDATE Authentication SET Password = ?, UpdatedAt = GETDATE() WHERE UserID = ? AND AuthType = 'google'";
//        try (PreparedStatement ps = connection.prepareStatement(sql)) {
//            String hashed = BCrypt.hashpw(newPassword, BCrypt.gensalt());
//            ps.setString(1, hashed);
//            ps.setInt(2, userId);
//            return ps.executeUpdate() > 0;
//        } catch (SQLException e) {
//            Logger.getLogger(AuthenticationDAO.class.getName()).log(Level.SEVERE, null, e);
//            return false;
//        }
//    }
    public void invalidateOldCodes(String email) {
        String sql = "UPDATE EmailVerification SET IsUsed = 1 WHERE Email = ? AND IsUsed = 0";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(AuthenticationDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void markUsed(int id) {
        String sql = "UPDATE EmailVerification SET IsUsed = 1 WHERE VerificationID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(AuthenticationDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public int createUser(String email, String firstName, String lastName) throws Exception {
        String sql = "INSERT INTO [User](FirstName, LastName, Email, UserRoleID, IsVerifiedEmail) VALUES (?, ?, ?, 5, 1)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, email);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            throw new Exception("Không lấy được UserID");
        }
    }

    public User findUserById(int userId) {
        User user = null;
        String sql = "SELECT UserID, FirstName, LastName, Email, Phone, Sex, BirthDay, Address, UserRoleID, "
                + " CreatedAt, UpdatedAt, DeletedAt, DeletedBy, IsDeleted, IsVerifiedEmail FROM [User] WHERE UserID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("UserID"));
                user.setFirstName(rs.getString("FirstName"));
                user.setLastName(rs.getString("LastName"));
                user.setEmail(rs.getString("Email"));
                user.setPhone(rs.getString("Phone"));
                user.setSex(rs.getString("Sex"));
                user.setBirthDay(rs.getTimestamp("BirthDay"));
                user.setAddress(rs.getString("Address"));
                user.setUserRoleId(rs.getInt("UserRoleID"));
                user.setCreatedAt(rs.getTimestamp("CreatedAt"));
                user.setUpdatedAt(rs.getTimestamp("UpdatedAt"));
                user.setDeletedAt(rs.getTimestamp("DeletedAt"));
                user.setDeletedBy(rs.getInt("DeletedBy"));
                user.setIsDeleted(rs.getBoolean("IsDeleted"));
                user.setIsVerifiedEmail(rs.getBoolean("IsVerifiedEmail"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AuthenticationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return user;
    }

    public void createGoogleAuth(int userId) throws Exception {
        String sql = "INSERT INTO Authentication(UserID, Password, AuthType, UserKey) VALUES (?, NULL, 'google', ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, UUID.randomUUID().toString()); // tạo userKey
            ps.executeUpdate();
        }
    }

    public void createAuth(int userId, String password) throws Exception {
        String sql = "INSERT INTO Authentication(UserID, Password, AuthType, UserKey) VALUES (?, ?, 'local', ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            // Mã hóa mật khẩu bằng BCrypt
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            ps.setInt(1, userId);
            ps.setString(2, hashedPassword);
            ps.setString(3, UUID.randomUUID().toString()); // tạo userKey
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AuthenticationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getUserIdByEmail(String email) {
        String sql = "SELECT UserID FROM [User] WHERE Email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("UserID");
            }
        } catch (SQLException e) {
            Logger.getLogger(AuthenticationDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return -1;
    }

    public List<String> getEmailByLevelId(int levelId) {
        List<String> list = new ArrayList<>();
        String sql = "SELECT Email FROM [User] WHERE LevelID = ? AND UserRoleID = 5";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, levelId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("Email"));
            }
        } catch (SQLException e) {
            Logger.getLogger(AuthenticationDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    public boolean updateUser(User user) {
        String sql = """
                     UPDATE [dbo].[User]
                        SET [FirstName] = ?, [LastName] = ?, [Phone] = ?, [Sex] = ?, [BirthDay] = ?, [Address] = ?, [UpdatedAt] = GETDATE() WHERE UserID = ?""";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, user.getFirstName());
            st.setString(2, user.getLastName());
            st.setString(3, user.getPhone());
            st.setString(4, user.getSex());
            st.setTimestamp(5, user.getBirthDay());
            st.setString(6, user.getAddress());
            st.setInt(7, user.getUserId());

            int row = st.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(AuthenticationDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Authentication> searchWithPagination(String keyword, String role, String status, String createdFrom, String createdTo, int page, int pageSize) {
        List<Authentication> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("""
        SELECT a.*, u.Email, r.RoleName
        FROM Authentication a
        JOIN [User] u ON a.UserID = u.UserID
        JOIN UserRole r ON u.UserRoleID = r.UserRoleID
        WHERE 1 = 1
    """);

        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.isEmpty()) {
            sql.append(" AND (u.Email LIKE ? OR a.UserKey LIKE ? OR a.AuthType LIKE ?)\n");
            String kw = "%" + keyword + "%";
            params.add(kw);
            params.add(kw);
            params.add(kw);
        }

        if (role != null && !role.isEmpty()) {
            sql.append(" AND r.RoleName = ?\n");
            params.add(role);
        }

        if (status != null && !status.isEmpty()) {
            sql.append(" AND a.a.IsDeleted = ?\n");
            params.add(status);
        }

        if (createdFrom != null && !createdFrom.isEmpty()) {
            sql.append(" AND a.CreatedAt >= ?\n");
            params.add(Date.valueOf(createdFrom) + " 00:00:00"); 
        }

        if (createdTo != null && !createdTo.isEmpty()) {
            sql.append(" AND a.CreatedAt <= ?\n");
            params.add(Date.valueOf(createdTo) + " 23:59:59");
        }

        sql.append(" ORDER BY a.CreatedAt DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
        params.add((page - 1) * pageSize);
        params.add(pageSize);

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Authentication auth = extractAuthentication(rs);
                auth.getUser().setEmail(rs.getString("Email"));
                auth.setRoleName(rs.getString("RoleName")); // phải có setRoleName trong model
                list.add(auth);
            }
        } catch (SQLException e) {
            Logger.getLogger(AuthenticationDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        return list;
    }

    public int countSearch(String keyword, String role, String status, String createdFrom, String createdTo) {
        StringBuilder sql = new StringBuilder("""
        SELECT COUNT(*)
        FROM Authentication a
        JOIN [User] u ON a.UserID = u.UserID
        JOIN UserRole r ON u.UserRoleID = r.UserRoleID
        WHERE 1 = 1
    """);

        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.isEmpty()) {
            sql.append(" AND (u.Email LIKE ? OR a.UserKey LIKE ? OR a.AuthType LIKE ?)\n");
            String kw = "%" + keyword + "%";
            params.add(kw);
            params.add(kw);
            params.add(kw);
        }

        if (role != null && !role.isEmpty()) {
            sql.append(" AND r.RoleName = ?\n");
            params.add(role);
        }

        if (status != null && !status.isEmpty()) {
            sql.append(" AND a.IsDeleted = ?\n");
            params.add(status);
        }

        if (createdFrom != null && !createdFrom.isEmpty()) {
            sql.append(" AND a.CreatedAt >= ?\n");
            params.add(Date.valueOf(createdFrom)+ " 00:00:00");
        }

        if (createdTo != null && !createdTo.isEmpty()) {
            sql.append(" AND a.CreatedAt <= ?\n");
            params.add(Date.valueOf(createdTo) + " 23:59:59");
        }

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(AuthenticationDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        return 0;
    }

    public boolean softDeleteAuth(int authId, int deletedBy) {
        String sql = "UPDATE Authentication SET IsDeleted = 1, DeletedAt = GETDATE(), DeletedBy = ? WHERE AuthenticationID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, deletedBy);
            ps.setInt(2, authId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.getLogger(AuthenticationDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    public boolean restoreAuth(int authId) {
        String sql = "UPDATE Authentication SET IsDeleted = 0, DeletedAt = NULL, DeletedBy = NULL WHERE AuthenticationID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, authId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.getLogger(AuthenticationDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }


    private Authentication extractAuthentication(ResultSet rs) throws SQLException {
        Authentication auth = new Authentication();
        auth.setAuthenticationID(rs.getInt("AuthenticationID"));
        User user = new User();
        user.setUserId(rs.getInt("UserID"));
        auth.setUser(user);
        auth.setUserKey(rs.getString("UserKey"));
        auth.setPassword(rs.getString("Password"));
        auth.setAuthType(rs.getString("AuthType"));
        auth.setCreatedAt(rs.getTimestamp("CreatedAt"));
        auth.setUpdatedAt(rs.getTimestamp("UpdatedAt"));
        auth.setDeletedAt(rs.getTimestamp("DeletedAt"));
        auth.setDeletedBy(rs.getObject("DeletedBy") != null ? rs.getInt("DeletedBy") : null);
        auth.setIsDeleted(rs.getBoolean("IsDeleted"));
        return auth;
    }

    public boolean createAuthForAdmin(int userId, String userKey, String password) {
        String sql = "INSERT INTO Authentication (UserID, UserKey, Password) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, userKey);
            ps.setString(3, password);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.getLogger(AuthenticationDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    public void logCreateUser(int targetUserId, int createdByUserId, String roleName) {
        String sql = "INSERT INTO AccountAuditLog (Action, TargetUserID, CreatedByUserID, Details) "
                + "VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "CREATE_ACCOUNT");
            stmt.setInt(2, targetUserId);
            stmt.setInt(3, createdByUserId);
            stmt.setString(4, "{\"role\": \"" + roleName + "\"}");
            stmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(AuthenticationDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public boolean usernameExists(String username) {
    String sql = "SELECT 1 FROM Authentication WHERE Username = ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setString(1, username);
        try (ResultSet rs = ps.executeQuery()) {
            return rs.next();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
}
