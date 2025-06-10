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
public class CustomerDAO extends DBContext {

    private int roleID = 5;

    public List<User> getCustomersByPage(int offset, int limit) {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM [User] WHERE IsDeleted = 0 AND UserRoleID = ? "
                + "ORDER BY CreatedAt DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, roleID);
            stmt.setInt(2, offset);
            stmt.setInt(3, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User c = new User();
                c.setUserId(rs.getInt("UserID"));
                c.setFirstName(rs.getString("FirstName"));
                c.setLastName(rs.getString("LastName"));
                c.setEmail(rs.getString("Email"));
                c.setPhone(rs.getString("Phone"));
                c.setAddress(rs.getString("Address"));
                c.setCreatedAt(rs.getTimestamp("CreatedAt"));
                list.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getTotalCustomerCount() {
        String sql = "SELECT COUNT(*) FROM [User] WHERE IsDeleted = 0 AND UserRoleID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, roleID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<User> searchCustomersByPage(String keyword, int offset, int limit) {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM [User] WHERE UserRoleID = 5 AND IsDeleted = 0 "
                + "AND (FirstName LIKE ? OR LastName LIKE ? OR Email LIKE ? OR Phone LIKE ? ) "
                + "ORDER BY CreatedAt DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            String kw = "%" + keyword + "%";
            stmt.setString(1, kw);
            stmt.setString(2, kw);
            stmt.setString(3, kw);
            stmt.setString(4, kw);
            stmt.setInt(5, offset);
            stmt.setInt(6, limit);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User c = new User();
                c.setUserId(rs.getInt("UserID"));
                c.setFirstName(rs.getString("FirstName"));
                c.setLastName(rs.getString("LastName"));
                c.setEmail(rs.getString("Email"));
                c.setPhone(rs.getString("Phone"));
                c.setAddress(rs.getString("Address"));
                c.setCreatedAt(rs.getTimestamp("CreatedAt"));
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getTotalCustomerSearchCount(String keyword) {
        String sql = "SELECT COUNT(*) FROM [User] WHERE IsDeleted = 0 AND UserRoleID = ? "
                + "AND (FirstName LIKE ? OR LastName LIKE ? OR Email LIKE ? OR Phone LIKE ? ) ";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String kw = "%" + keyword + "%";
            stmt.setInt(1, roleID);
            stmt.setString(2, kw);
            stmt.setString(3, kw);
            stmt.setString(4, kw);
            stmt.setString(5, kw);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void deleteCustomer(int id, int deletedBy) {
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
        CustomerDAO dao = new CustomerDAO();
        List<User> customers = dao.searchCustomersByPage("", 0, 5);
        System.out.println(dao.getTotalCustomerSearchCount(""));
        System.out.println("DANH SÁCH CUSTOMER:");
        for (User c : customers) {
            System.out.println("ID: " + c.getUserId()
                    + ", Tên: " + c.getFirstName() + " " + c.getLastName()
                    + ", Email: " + c.getEmail()
                    + ", SĐT: " + c.getPhone()
                    + ", Địa chỉ: " + c.getAddress()
                    + ", Ngày tạo: " + c.getCreatedAt());
        }
    }
}
