/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import entity.Staff;
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

    public List<Staff> getStaffByRoleWithPaging(String roleName, int offset, int limit) {
        List<Staff> staffList = new ArrayList<>();
        String sql = "SELECT u.*, r.RoleName FROM [User] u JOIN UserRole r ON u.UserRoleID = r.UserRoleID WHERE r.RoleName = ? AND u.IsDeleted = 0 ORDER BY u.CreatedAt DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, roleName);
            stmt.setInt(2, offset);
            stmt.setInt(3, limit);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                staffList.add(extractStaff(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return staffList;
    }

    private Staff extractStaff(ResultSet rs) throws SQLException {
        Staff staff = new Staff();
        staff.setUserId(rs.getInt("UserID"));
        staff.setFirstName(rs.getString("FirstName"));
        staff.setLastName(rs.getString("LastName"));
        staff.setEmail(rs.getString("Email"));
        staff.setPhone(rs.getString("Phone"));
        staff.setAddress(rs.getString("Address"));
        staff.setCreatedAt(rs.getTimestamp("CreatedAt"));
        staff.setRoleName(rs.getString("RoleName"));
        return staff;
    }

    public int countTotalStaffByRole(String roleName) {
        String sql = "SELECT COUNT(*) FROM [User] u JOIN UserRole r ON u.UserRoleID = r.UserRoleID"
                + " WHERE r.RoleName = ? AND u.IsDeleted = 0";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, roleName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public List<Staff> searchStaffByPage(String keyword, String roleName, int offset, int limit) {
        List<Staff> list = new ArrayList<>();
        String sql = "SELECT * FROM [User] u JOIN UserRole r ON u.UserRoleID = r.UserRoleID WHERE r.RoleName = ? AND u.IsDeleted = 0 "
                + "AND (u.FirstName LIKE ? OR u.LastName LIKE ? OR u.Email LIKE ? OR Phone LIKE ?) "
                + "ORDER BY u.CreatedAt DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            String kw = "%" + keyword + "%";
            stmt.setString(1, roleName);
            stmt.setString(2, kw);
            stmt.setString(3, kw);
            stmt.setString(4, kw);
            stmt.setString(5, kw);
            stmt.setInt(6, offset);
            stmt.setInt(7, limit);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Staff s = new Staff();
                s.setUserId(rs.getInt("UserID"));
                s.setFirstName(rs.getString("FirstName"));
                s.setLastName(rs.getString("LastName"));
                s.setEmail(rs.getString("Email"));
                s.setPhone(rs.getString("Phone"));
                s.setAddress(rs.getString("Address"));
                s.setCreatedAt(rs.getTimestamp("CreatedAt"));
                s.setRoleName(rs.getString("RoleName"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countSearchStaff(String keyword, String roleName) {
        String sql = "SELECT COUNT(*) FROM [User] u JOIN UserRole r ON u.UserRoleID = r.UserRoleID"
                + " WHERE r.RoleName = ? AND u.IsDeleted = 0  "
                + "AND (u.FirstName LIKE ? OR u.LastName LIKE ? OR u.Email LIKE ? OR Phone LIKE ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String kw = "%" + keyword + "%";
            stmt.setString(1, roleName);
            stmt.setString(2, kw);
            stmt.setString(3, kw);
            stmt.setString(4, kw);
            stmt.setString(5, kw);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void deleteStaff(int id, int deletedBy) {
        String sql = "UPDATE [User] SET IsDeleted = 1, DeletedAt = GETDATE(), DeletedBy = ? WHERE UserID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, deletedBy);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        StaffDAO dao = new StaffDAO();
        List<Staff> staffs = dao.searchStaffByPage("", "Cleaner", 0, 5);
        System.out.println(dao.countSearchStaff("", "Cleaner"));
        System.out.println("DANH SÁCH Cleaner:");
        for (Staff c : staffs) {
            System.out.println("ID: " + c.getUserId()
                    + ", Tên: " + c.getFirstName() + " " + c.getLastName()
                    + ", Email: " + c.getEmail()
                    + ", SĐT: " + c.getPhone()
                    + ", Địa chỉ: " + c.getAddress()
                    + ", Ngày tạo: " + c.getCreatedAt()
                    + ", Role: " + c.getRoleName());
        }
    }
}
