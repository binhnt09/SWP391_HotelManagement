/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import entity.CustomerReport;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.SQLException;

/**
 *
 * @author viet7
 */
public class ReportDAO extends DBContext{
    
        public List<CustomerReport> getCustomerReport() {
        List<CustomerReport> list = new ArrayList<>();
        String sql = "SELECT u.UserID, u.FirstName, u.LastName, COUNT(b.BookingID) AS TotalBookings, SUM(b.TotalAmount) AS TotalSpent " +
                     "FROM [User] u " +
                     "LEFT JOIN Booking b ON u.UserID = b.UserID AND b.IsDeleted = 0 " +
                     "WHERE u.IsDeleted = 0 " +
                     "GROUP BY u.UserID, u.FirstName, u.LastName " +
                     "ORDER BY TotalSpent DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CustomerReport cr = new CustomerReport();
                cr.setUserId(rs.getInt("UserID"));
                cr.setFirstName(rs.getString("FirstName"));
                cr.setLastName(rs.getString("LastName"));
                cr.setTotalBookings(rs.getInt("TotalBookings"));
                cr.setTotalSpent(rs.getBigDecimal("TotalSpent") == null ? BigDecimal.ZERO : rs.getBigDecimal("TotalSpent"));
                list.add(cr);
            }
        } catch (SQLException e) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }
}
