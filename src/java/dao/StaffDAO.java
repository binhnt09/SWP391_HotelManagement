/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import entity.User;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author viet7
 */
public class StaffDAO extends DBContext {

    public List<User> getStaffByRoleWithPaging(int roleID, int offset, int limit) {
        List<User> staffList = new ArrayList<>();
        String sql = "SELECT * FROM [User] "
                + "WHERE UserRoleID = ? AND IsDeleted = 0 "
                + "ORDER BY CreatedAt DESC "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, roleID);
            stmt.setInt(2, offset);
            stmt.setInt(3, limit);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                User staff = new User();
                staff.setUserId(rs.getInt("UserID"));
                staff.setFirstName(rs.getString("FirstName"));
                staff.setLastName(rs.getString("LastName"));
                staff.setEmail(rs.getString("Email"));
                staff.setPhone(rs.getString("Phone"));
                staff.setAddress(rs.getString("Address"));
                staff.setUserRoleId(roleID);
                staff.setRoleName(getRoleName(roleID));
                staff.setCreatedAt(rs.getTimestamp("CreatedAt"));
                staffList.add(staff);
            }

        } catch (SQLException e) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        return staffList;
    }

    public int countTotalStaffByRole(int roleID) {
        String sql = "SELECT COUNT(*) FROM [User]"
                + " WHERE UserRoleID = ? AND IsDeleted = 0";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, roleID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        return 0;
    }

    public List<User> searchStaffByPage(String keyword, int roleID, int offset, int limit) {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM [User] WHERE UserRoleID = ? AND IsDeleted = 0 "
                + "AND (FirstName LIKE ? OR LastName LIKE ? OR Email LIKE ? OR Phone LIKE ? OR Address LIKE ?) "
                + "ORDER BY CreatedAt DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            String kw = "%" + keyword + "%";
            stmt.setInt(1, roleID);
            stmt.setString(2, kw);
            stmt.setString(3, kw);
            stmt.setString(4, kw);
            stmt.setString(5, kw);
            stmt.setString(6, kw);
            stmt.setInt(7, offset);
            stmt.setInt(8, limit);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User s = new User();
                s.setUserId(rs.getInt("UserID"));
                s.setFirstName(rs.getString("FirstName"));
                s.setLastName(rs.getString("LastName"));
                s.setEmail(rs.getString("Email"));
                s.setPhone(rs.getString("Phone"));
                s.setAddress(rs.getString("Address"));
                s.setUserRoleId(roleID);
                s.setRoleName(getRoleName(roleID));
                s.setCreatedAt(rs.getTimestamp("CreatedAt"));
                list.add(s);
            }
        } catch (Exception e) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    public int countSearchStaff(String keyword, int roleID) {
        String sql = "SELECT COUNT(*) FROM [User]"
                + " WHERE UserRoleID = ? AND IsDeleted = 0  "
                + "AND (FirstName LIKE ? OR LastName LIKE ? OR Email LIKE ? OR Phone LIKE ? OR Address LIKE ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String kw = "%" + keyword + "%";
            stmt.setInt(1, roleID);
            stmt.setString(2, kw);
            stmt.setString(3, kw);
            stmt.setString(4, kw);
            stmt.setString(5, kw);
            stmt.setString(6, kw);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    public boolean deleteStaff(int id, int deletedBy) {
        String sql = "UPDATE [User] SET IsDeleted = 1, DeletedAt = GETDATE(), DeletedBy = ? WHERE UserID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, deletedBy);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    public boolean insertUser(User user) {
        String sql = "INSERT INTO [User] (FirstName, LastName, Email, Phone, Address, UserRoleID, CreatedAt, UpdatedAt, IsDeleted) VALUES (?, ?, ?, ?, ?, ?, getdate(), getdate(), 0)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getAddress());
            ps.setInt(6, user.getUserRoleId());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    public boolean updateUser(User user) {
        String sql = "UPDATE [User] SET FirstName=?, LastName=?, Email=?, Phone=?, Address=?, UpdatedAt= getdate(), UserRoleID=?  WHERE UserID=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getAddress());
            ps.setInt(6, user.getUserRoleId());
            ps.setInt(7, user.getUserId());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    public String getRoleName(int roleId) {
        switch (roleId) {
            case 3:
                return "Receptionist";
            case 4:
                return "Cleaner";
            default:
                return "Unknown";
        }
    }

    public int insertUserBasic(String firstName, String lastName, String email, int roleId) {
        String sql = "INSERT INTO [User] (FirstName, LastName, Email, UserRoleID) VALUES (?, ?, ?, ?)";
        try (
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, email);
            ps.setInt(4, roleId);

            int affected = ps.executeUpdate();
            if (affected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return -1;
    }

    private boolean checkUserHasAccount(int userId) {
        String sql = "SELECT COUNT(*) FROM Authentication WHERE UserID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    public int countUsers(String keyword, Integer roleID) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM [User] WHERE 1=1");

        if (roleID != null) {
            sql.append(" AND UserRoleID = ?");
        }

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (FirstName LIKE ? OR LastName LIKE ? OR Email LIKE ?");
            try {
                Integer.parseInt(keyword.trim());
                sql.append(" OR userID = ?");
            } catch (NumberFormatException e) {
            }
            sql.append(")");
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            int index = 1;

            if (roleID != null) {
                stmt.setInt(index++, roleID);
            }

            if (keyword != null && !keyword.trim().isEmpty()) {
                String kw = "%" + keyword + "%";
                for (int i = 0; i < 3; i++) {
                    stmt.setString(index++, kw);
                }

                try {
                    int idValue = Integer.parseInt(keyword.trim());
                    stmt.setInt(index++, idValue);
                } catch (NumberFormatException e) {
                }
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        return 0;
    }

    public List<User> getUsersWithPaging(String keyword, Integer roleID, int offset, int limit) {
        List<User> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM [User] WHERE 1=1");

        if (roleID != null) {
            sql.append(" AND UserRoleID = ?");
        }

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (FirstName LIKE ? OR LastName LIKE ? OR Email LIKE ?");
            try {
                Integer.parseInt(keyword.trim());
                sql.append(" OR userID = ?");
            } catch (NumberFormatException e) {
            }
            sql.append(")");
        }

        sql.append(" ORDER BY CreatedAt DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            int index = 1;

            if (roleID != null) {
                stmt.setInt(index++, roleID);
            }

            if (keyword != null && !keyword.trim().isEmpty()) {
                String kw = "%" + keyword + "%";
                for (int i = 0; i < 3; i++) {
                    stmt.setString(index++, kw);
                }

                try {
                    int idValue = Integer.parseInt(keyword.trim());
                    stmt.setInt(index++, idValue);
                } catch (NumberFormatException e) {
                }
            }

            stmt.setInt(index++, offset);
            stmt.setInt(index, limit);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User s = new User();
                int userId = rs.getInt("UserID");

                s.setUserId(userId);
                s.setFirstName(rs.getString("FirstName"));
                s.setLastName(rs.getString("LastName"));
                s.setEmail(rs.getString("Email"));
                s.setPhone(rs.getString("Phone"));
                s.setAddress(rs.getString("Address"));
                s.setUserRoleId(rs.getInt("UserRoleID"));
                s.setRoleName(getRoleName(rs.getInt("UserRoleID")));
                s.setCreatedAt(rs.getTimestamp("CreatedAt"));
                s.setIsDeleted((rs.getInt("isDeleted") == 1));
                s.setDeletedBy(rs.getInt("DeletedBy"));

                s.setHasAccount(checkUserHasAccount(userId));

                list.add(s);
            }
        } catch (Exception e) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        return list;
    }

    public boolean restoreUser(int userId) {
        String sql = "UPDATE [User] SET isDeleted = 0 WHERE userId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateUserRole(int userId, int newRoleId) {
        String sql = "UPDATE [User] SET UserRoleID = ?, UpdatedAt = GETDATE() WHERE UserID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, newRoleId);
            ps.setInt(2, userId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }
    
}
