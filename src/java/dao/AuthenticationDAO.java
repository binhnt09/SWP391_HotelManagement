/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import entity.Authentication;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

//    public Authentication Login(String email, String pass) {
//        String sql = "select * from Authentication a\n"
//                + "	JOIN [User] u ON a.UserID = u.UserID\n"
//                + "	WHERE u.Email = ? "
//                + "	AND a.IsDeleted = 0 AND u.IsDeleted = 0";
//        try {
//            PreparedStatement stm = connection.prepareStatement(sql);
//            stm.setString(1, email);
//
//            ResultSet rs = stm.executeQuery();
//            if (rs.next()) {
//                String hashedPassword = rs.getString("Password");
//                if (PasswordUtil.checkPassword(pass, hashedPassword)) {
//                    return new Authentication(
//                            rs.getInt("AuthenticationID"),
//                            rs.getInt("UserID"),
//                            rs.getString("UserKey"),
//                            hashedPassword,
//                            rs.getString("AuthType"),
//                            rs.getTimestamp("CreatedAt"),
//                            rs.getTimestamp("UpdatedAt"),
//                            rs.getTimestamp("DeletedAt"),
//                            rs.getInt("DeletedBy"),
//                            rs.getBoolean("IsDeleted")
//                    );
//                }
//                System.out.println("Raw: " + pass);
//                System.out.println("From DB: " + hashedPassword);
//                System.out.println("Match? " + BCrypt.checkpw(pass, hashedPassword));
//            }
//        } catch (SQLException e) {
//            Logger.getLogger(AuthenticationDAO.class.getName()).log(Level.SEVERE, null, e);
//        }
//
//        return null;
//    }
    public Authentication login(String email) {
        String sql = "SELECT a.AuthenticationID, a.UserID, a.Password "
                + "FROM Authentication a "
                + "JOIN [User] u ON a.UserID = u.UserID "
                + "WHERE u.Email = ? AND a.IsDeleted = 0 AND u.IsDeleted = 0";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                return new Authentication(
                        rs.getInt("AuthenticationID"),
                        rs.getInt("UserID"),
                        null, // UserKey không cần dùng ở đây
                        rs.getString("Password"),
                        null, // AuthType
                        null, null, null, null, false // Bỏ các trường không cần
                );
            }

        } catch (SQLException e) {
            Logger.getLogger(AuthenticationDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public static void main(String[] args) {
//        String rawPassword = "123456";
//        String hashed = BCrypt.hashpw(rawPassword, BCrypt.gensalt(12));
//        System.out.println("Mã hóa: " + hashed);
        AuthenticationDAO dao = new AuthenticationDAO();
//        dao.hashAllPlaintextPasswords();

//        Authentication acc = dao.Login("tranthib@example.com", "123456");
//        if (acc != null) {
//            System.out.println("Đăng nhập thành công. UserID: " + acc.getUserID());
//        } else {
//            System.out.println("Sai email hoặc mật khẩu!");
//        }
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
