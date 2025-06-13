/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import entity.Authentication;
import entity.User;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                    System.out.println("❗ ID " + id + " đã hash, bỏ qua.");
                    continue;
                }

                String hashed = BCrypt.hashpw(currentPass, BCrypt.gensalt(12));
                updateStmt.setString(1, hashed);
                updateStmt.setInt(2, id);
                updateStmt.executeUpdate();

                System.out.println("✔ Đã hash cho ID: " + id);
            }

        } catch (SQLException e) {
            Logger.getLogger(AuthenticationDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public Authentication login(String email) {
        String sql = "SELECT a.AuthenticationID, a.UserKey, a.Password, a.AuthType, a.CreatedAt, a.UpdatedAt, a.DeletedAt, a.DeletedBy, a.IsDeleted,  "
                + " u.UserID, u.FirstName, u.LastName, u.Email, u.Phone, u.Address, u.CreatedAt, u.UpdatedAt, u.DeletedAt, u.DeletedBy, u.IsDeleted, u.IsVerifiedEmail, u.UserRoleID "
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
                        null,
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

    public boolean existEmail(String email) {
        String sql = "SELECT * FROM [User] WHERE Email = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, email);
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
        String sql = "INSERT INTO [User](FirstName, LastName, Email, UserRoleID, IsVerifiedEmail) VALUES (?, ?, ?, 1, 1)";
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

    public static void main(String[] args) {
        AuthenticationDAO dao = new AuthenticationDAO();

        String email = "admin@gmail.com";
        String rawPassword = "123456";

        Authentication auth = dao.login(email);
        if (auth != null && BCrypt.checkpw(rawPassword, auth.getPassword())) {
            System.out.println("✅ Đăng nhập thành công! " + auth.getUserID());
        } else {
            System.out.println("❌ Sai email hoặc mật khẩu!");
        }
    }
}
