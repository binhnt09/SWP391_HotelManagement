/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import entity.CustomerReport;
import java.math.BigDecimal;
import java.sql.Timestamp;
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
public class ReportDAO extends DBContext {

    public List<CustomerReport> getCustomerReport() {
        List<CustomerReport> list = new ArrayList<>();
        String sql = "SELECT u.UserID, u.FirstName, u.LastName, u.Email, \n"
                + "       COUNT(b.BookingID) AS TotalBookings,\n"
                + "       SUM(b.TotalAmount) AS TotalSpent,\n"
                + "       MAX(b.BookingDate) AS LastBookingDate,\n"
                + "       u.CreatedAt AS RegisterDate\n"
                + "FROM [User] u\n"
                + "LEFT JOIN Booking b ON u.UserID = b.UserID \n"
                + "WHERE u.UserRoleID = 5 and u.IsDeleted = 0\n"
                + "GROUP BY u.UserID, u.FirstName, u.LastName, u.Email, u.CreatedAt";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CustomerReport cr = new CustomerReport();
                cr.setUserId(rs.getInt("UserID"));
                cr.setFirstName(rs.getString("FirstName"));
                cr.setLastName(rs.getString("LastName"));
                cr.setEmail(rs.getString("Email"));
                cr.setTotalBookings(rs.getInt("TotalBookings"));
                cr.setTotalSpent(rs.getBigDecimal("TotalSpent") == null ? BigDecimal.ZERO : rs.getBigDecimal("TotalSpent"));
                cr.setLastBookingDate(rs.getTimestamp("LastBookingDate"));
                cr.setRegisterDate(rs.getTimestamp("RegisterDate"));
                list.add(cr);
            }
        } catch (SQLException e) {
            Logger.getLogger(ReportDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    public List<CustomerReport> getCustomerReportWithPaging(String keyword, int offset, int limit) {
        List<CustomerReport> list = new ArrayList<>();
        String sql = "SELECT u.UserID, u.FirstName, u.LastName, u.Email, u.CreatedAt, "
                + "COUNT(b.BookingID) AS TotalBookings, "
                + "SUM(b.TotalAmount) AS TotalSpent, "
                + "MAX(b.BookingDate) AS LastBookingDate "
                + "FROM [User] u "
                + "LEFT JOIN Booking b ON u.UserID = b.UserID "
                + "WHERE u.UserRoleID = 5 AND u.IsDeleted = 0 AND (u.FirstName LIKE ? OR u.LastName LIKE ? OR u.Email LIKE ?)"
                + "GROUP BY u.UserID, u.FirstName, u.LastName, u.Email, u.CreatedAt "
                + "ORDER BY TotalSpent DESC "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ps.setString(3, "%" + keyword + "%");
            ps.setInt(4, offset);
            ps.setInt(5, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CustomerReport cr = new CustomerReport();
                cr.setUserId(rs.getInt("UserID"));
                cr.setFirstName(rs.getString("FirstName"));
                cr.setLastName(rs.getString("LastName"));
                cr.setEmail(rs.getString("Email"));
                cr.setRegisterDate(rs.getTimestamp("CreatedAt"));
                cr.setTotalBookings(rs.getInt("TotalBookings"));
                cr.setTotalSpent(rs.getBigDecimal("TotalSpent"));
                cr.setLastBookingDate(rs.getTimestamp("LastBookingDate"));
                list.add(cr);
            }
        } catch (Exception e) {
            Logger.getLogger(ReportDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    public List<CustomerReport> getCustomerReportWithPaging(
            String keyword,
            int bookingMin, int bookingMax,
            long spentMin, long spentMax,
            Timestamp registerStart, Timestamp registerEnd,
            Timestamp bookingStart, Timestamp bookingEnd,
            String sort, String order,
            int offset, int limit
    ) {
        List<CustomerReport> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT u.UserID, u.FirstName, u.LastName, u.Email, u.CreatedAt, ");
        sql.append("COUNT(b.BookingID) AS TotalBookings, ");
        sql.append("ISNULL(SUM(b.TotalAmount), 0) + ISNULL(SUM(bs.PriceAtUse * bs.Quantity), 0) AS TotalSpent, ");
        sql.append("MAX(b.BookingDate) AS LastBooking ");
        sql.append("FROM [User] u ");
        sql.append("LEFT JOIN Booking b ON u.UserID = b.UserID ");
        sql.append("LEFT JOIN BookingService bs ON b.BookingID = bs.BookingID AND bs.IsDeleted = 0 ");
        sql.append("WHERE u.IsDeleted = 0 and (u.UserRoleID = 5 or u.UserRoleID = 6)");

        // Keyword
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (u.FirstName LIKE ? OR u.LastName LIKE ? OR u.Email LIKE ?) ");
        }

        // Tier is handled after totalSpent is calculated
        sql.append("GROUP BY u.UserID, u.FirstName, u.LastName, u.Email, u.CreatedAt ");
        sql.append("HAVING COUNT(b.BookingID) BETWEEN ? AND ? ");
        sql.append("AND ISNULL(SUM(b.TotalAmount), 0) BETWEEN ? AND ? ");

        if (registerStart != null) {
            sql.append(" AND u.CreatedAt >= ? ");
        }
        if (registerEnd != null) {
            sql.append(" AND u.CreatedAt <= ? ");
        }
        if (bookingStart != null) {
            sql.append(" AND MAX(b.BookingDate) >= ? ");
        }
        if (bookingEnd != null) {
            sql.append(" AND MAX(b.BookingDate) <= ? ");
        }

        // Sorting
        if (sort != null && !sort.isEmpty()) {
            if (sort.equals("totalSpent") || sort.equals("totalBookings") || sort.equals("registerDate")) {
                sql.append(" ORDER BY " + (sort.equals("registerDate") ? "u.CreatedAt" : sort));
                if ("desc".equalsIgnoreCase(order)) {
                    sql.append(" DESC");
                } else {
                    sql.append(" ASC");
                }
            }
        } else {
            sql.append(" ORDER BY u.UserID ASC ");
        }
        sql.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int index = 1;

            if (keyword != null && !keyword.trim().isEmpty()) {
                String likeKeyword = "%" + keyword + "%";
                ps.setString(index++, likeKeyword);
                ps.setString(index++, likeKeyword);
                ps.setString(index++, likeKeyword);
            }

            ps.setInt(index++, bookingMin);
            ps.setInt(index++, bookingMax);
            ps.setLong(index++, spentMin);
            ps.setLong(index++, spentMax);

            if (registerStart != null) {
                ps.setTimestamp(index++, registerStart);
            }
            if (registerEnd != null) {
                ps.setTimestamp(index++, registerEnd);
            }
            if (bookingStart != null) {
                ps.setTimestamp(index++, bookingStart);
            }
            if (bookingEnd != null) {
                ps.setTimestamp(index++, bookingEnd);
            }

            ps.setInt(index++, offset);
            ps.setInt(index++, limit);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CustomerReport c = new CustomerReport();
                c.setUserId(rs.getInt("UserID"));
                c.setFirstName(rs.getString("FirstName"));
                c.setLastName(rs.getString("LastName"));
                c.setEmail(rs.getString("Email"));
                c.setRegisterDate(rs.getTimestamp("CreatedAt"));
                c.setTotalBookings(rs.getInt("TotalBookings"));
                c.setTotalSpent(rs.getBigDecimal("TotalSpent"));
                c.setLastBookingDate(rs.getTimestamp("LastBooking"));
                c.setTotalPaid(getTotalSpentByUser(rs.getInt("UserID")));
                list.add(c);
            }
        } catch (SQLException e) {
            Logger.getLogger(ReportDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    public int getCustomerReportCount(
            String keyword,
            int bookingMin, int bookingMax,
            long spentMin, long spentMax,
            Timestamp registerStart, Timestamp registerEnd,
            Timestamp bookingStart, Timestamp bookingEnd
    ) {
        int total = 0;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) FROM ( ");
        sql.append("SELECT u.UserID FROM [User] u ");
        sql.append("LEFT JOIN Booking b ON u.UserID = b.UserID ");
        sql.append("WHERE u.IsDeleted = 0 and (u.UserRoleID = 5 or u.UserRoleID = 6) ");

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (u.FirstName LIKE ? OR u.LastName LIKE ? OR u.Email LIKE ?) ");
        }

        sql.append("GROUP BY u.UserID, u.FirstName, u.LastName, u.Email, u.CreatedAt ");
        sql.append("HAVING COUNT(b.BookingID) BETWEEN ? AND ? ");
        sql.append("AND ISNULL(SUM(b.TotalAmount), 0) BETWEEN ? AND ? ");

        if (registerStart != null) {
            sql.append(" AND u.CreatedAt >= ? ");
        }
        if (registerEnd != null) {
            sql.append(" AND u.CreatedAt <= ? ");
        }
        if (bookingStart != null) {
            sql.append(" AND MAX(b.BookingDate) >= ? ");
        }
        if (bookingEnd != null) {
            sql.append(" AND MAX(b.BookingDate) <= ? ");
        }

        sql.append(") AS FilteredResults");

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int index = 1;

            if (keyword != null && !keyword.trim().isEmpty()) {
                String likeKeyword = "%" + keyword + "%";
                ps.setString(index++, likeKeyword);
                ps.setString(index++, likeKeyword);
                ps.setString(index++, likeKeyword);
            }

            ps.setInt(index++, bookingMin);
            ps.setInt(index++, bookingMax);
            ps.setLong(index++, spentMin);
            ps.setLong(index++, spentMax);

            if (registerStart != null) {
                ps.setTimestamp(index++, registerStart);
            }
            if (registerEnd != null) {
                ps.setTimestamp(index++, registerEnd);
            }
            if (bookingStart != null) {
                ps.setTimestamp(index++, bookingStart);
            }
            if (bookingEnd != null) {
                ps.setTimestamp(index++, bookingEnd);
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(ReportDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        return total;
    }

    public List<CustomerReport> exportCustomerReport(
            String keyword,
            int bookingMin, int bookingMax,
            long spentMin, long spentMax,
            Timestamp registerStart, Timestamp registerEnd,
            Timestamp bookingStart, Timestamp bookingEnd,
            String sort, String order
    ) {
        List<CustomerReport> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT u.UserID, u.FirstName, u.LastName, u.Email, u.CreatedAt, ");
        sql.append("COUNT(b.BookingID) AS TotalBookings, ");
        sql.append("ISNULL(SUM(b.TotalAmount), 0) AS TotalSpent, ");
        sql.append("MAX(b.BookingDate) AS LastBooking ");
        sql.append("FROM [User] u ");
        sql.append("LEFT JOIN Booking b ON u.UserID = b.UserID ");
        sql.append("WHERE u.IsDeleted = 0 and (u.UserRoleID = 5 or u.UserRoleID = 6)");

        // Keyword
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (u.FirstName LIKE ? OR u.LastName LIKE ? OR u.Email LIKE ?) ");
        }

        // Tier is handled after totalSpent is calculated
        sql.append("GROUP BY u.UserID, u.FirstName, u.LastName, u.Email, u.CreatedAt ");
        sql.append("HAVING COUNT(b.BookingID) BETWEEN ? AND ? ");
        sql.append("AND ISNULL(SUM(b.TotalAmount), 0) BETWEEN ? AND ? ");

        if (registerStart != null) {
            sql.append(" AND u.CreatedAt >= ? ");
        }
        if (registerEnd != null) {
            sql.append(" AND u.CreatedAt <= ? ");
        }
        if (bookingStart != null) {
            sql.append(" AND MAX(b.BookingDate) >= ? ");
        }
        if (bookingEnd != null) {
            sql.append(" AND MAX(b.BookingDate) <= ? ");
        }

        // Sorting
        if (sort != null && !sort.isEmpty()) {
            if (sort.equals("totalSpent") || sort.equals("totalBookings") || sort.equals("registerDate")) {
                sql.append(" ORDER BY " + (sort.equals("registerDate") ? "u.CreatedAt" : sort));
                if ("desc".equalsIgnoreCase(order)) {
                    sql.append(" DESC");
                } else {
                    sql.append(" ASC");
                }
            }
        } else {
            sql.append(" ORDER BY u.UserID ASC ");
        }

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int index = 1;

            if (keyword != null && !keyword.trim().isEmpty()) {
                String likeKeyword = "%" + keyword + "%";
                ps.setString(index++, likeKeyword);
                ps.setString(index++, likeKeyword);
                ps.setString(index++, likeKeyword);
            }

            ps.setInt(index++, bookingMin);
            ps.setInt(index++, bookingMax);
            ps.setLong(index++, spentMin);
            ps.setLong(index++, spentMax);

            if (registerStart != null) {
                ps.setTimestamp(index++, registerStart);
            }
            if (registerEnd != null) {
                ps.setTimestamp(index++, registerEnd);
            }
            if (bookingStart != null) {
                ps.setTimestamp(index++, bookingStart);
            }
            if (bookingEnd != null) {
                ps.setTimestamp(index++, bookingEnd);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CustomerReport c = new CustomerReport();
                c.setUserId(rs.getInt("UserID"));
                c.setFirstName(rs.getString("FirstName"));
                c.setLastName(rs.getString("LastName"));
                c.setEmail(rs.getString("Email"));
                c.setRegisterDate(rs.getTimestamp("CreatedAt"));
                c.setTotalBookings(rs.getInt("TotalBookings"));
                c.setTotalSpent(rs.getBigDecimal("TotalSpent"));
                c.setLastBookingDate(rs.getTimestamp("LastBooking"));
                c.setTotalPaid(getTotalSpentByUser(rs.getInt("UserID")));
                list.add(c);
            }
        } catch (SQLException e) {
            Logger.getLogger(ReportDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    public BigDecimal getTotalSpentByUser(int userId) {
        BigDecimal total = BigDecimal.ZERO;
        String sql = "SELECT "
                + "ISNULL(SUM(b.TotalAmount), 0) AS TotalBookingAmount, "
                + "ISNULL(SUM(bs.PriceAtUse * bs.Quantity), 0) AS TotalServiceAmount "
                + "FROM Booking b "
                + "LEFT JOIN BookingService bs ON b.BookingID = bs.BookingID AND bs.IsDeleted = 0 "
                + "WHERE b.UserID = ? AND b.IsDeleted = 0";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                BigDecimal bookingAmount = rs.getBigDecimal("TotalBookingAmount");
                BigDecimal serviceAmount = rs.getBigDecimal("TotalServiceAmount");
                total = bookingAmount.add(serviceAmount);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // hoặc ghi log tùy bạn
        }

        return total;
    }
}
