/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import entity.User;
import java.util.ArrayList;
import java.util.List;
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
            return stmt.executeUpdate() >0;
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


    public static void main(String[] args) {
        StaffDAO dao = new StaffDAO();
        List<User> staffs = dao.getStaffByRoleWithPaging( 3, 0, 5);
        System.out.println(dao.countSearchStaff("", 3));
        System.out.println("DANH SÁCH Cleaner:");
        for (User c : staffs) {
            System.out.println("ID: " + c.getUserId()
                    + ", Tên: " + c.getFirstName() + " " + c.getLastName()
                    + ", Email: " + c.getEmail()
                    + ", SĐT: " + c.getPhone()
                    + ", Địa chỉ: " + c.getAddress()
                    + ", Ngày tạo: " + c.getCreatedAt()
                    + ", Role: " + c.getUserRoleId());
        }
    }
}
