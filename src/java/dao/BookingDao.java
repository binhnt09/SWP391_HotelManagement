/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import entity.Booking;
import entity.BookingDetails;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class BookingDao extends DBContext {

    public Booking getBookingById(int bookingId) {
        Booking booking = null;

        String sql = "SELECT bookingID, UserID, VoucherID, BookingDate, CheckInDate, "
                + " CheckOutDate, TotalAmount, Status, CreatedAt, UpdatedAt, deletedAt, deletedBy, isDeleted"
                + " FROM Booking WHERE bookingID = ? AND isDeleted = 0";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    booking = new Booking();
                    booking.setBookingId(rs.getInt("bookingID"));
                    booking.setUserId(rs.getInt("userID"));
                    booking.setVoucherId(rs.getObject("voucherID") != null ? rs.getInt("voucherID") : null);
                    booking.setBookingDate(rs.getTimestamp("bookingDate"));
                    booking.setCheckInDate(rs.getDate("checkInDate"));
                    booking.setCheckOutDate(rs.getDate("checkOutDate"));
                    booking.setTotalAmount(rs.getBigDecimal("totalAmount"));
                    booking.setStatus(rs.getString("status"));
                    booking.setCreatedAt(rs.getTimestamp("createdAt"));
                    booking.setUpdatedAt(rs.getTimestamp("updatedAt"));
                    booking.setDeletedAt(rs.getTimestamp("deletedAt"));
                    booking.setDeletedBy(rs.getObject("deletedBy") != null ? rs.getInt("deletedBy") : null);
                    booking.setIsDeleted(rs.getBoolean("isDeleted"));
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(BookingDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return booking;
    }

    public int insertBooking(Booking booking) {
        String sql = "INSERT INTO Booking (UserID, VoucherID, BookingDate, CheckInDate, "
                + " CheckOutDate, TotalAmount, Status, CreatedAt, UpdatedAt, IsDeleted) "
                + " VALUES (?, ?, GETDATE(), ?, ?, ?, ?, GETDATE(), GETDATE(), 0)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, booking.getUserId());
            if (booking.getVoucherId() != null) {
                ps.setInt(2, booking.getVoucherId());
            } else {
                ps.setNull(2, java.sql.Types.INTEGER);
            }
            ps.setDate(3, booking.getCheckInDate());
            ps.setDate(4, booking.getCheckOutDate());
            ps.setBigDecimal(5, booking.getTotalAmount());
            ps.setString(6, booking.getStatus());

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(BookingDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return -1;
    }

    public boolean updateStatus(int bookingId, String status) {
        String sql = "UPDATE Booking SET Status = ? WHERE BookingID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, bookingId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.getLogger(BookingDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    public boolean insertBookingDetail(BookingDetails detail) {
        String sql = "INSERT INTO BookingDetail (BookingID, RoomID, Price, Nights, CreatedAt, UpdatedAt, IsDeleted) "
                + "VALUES (?, ?, ?, ?, GETDATE(), GETDATE(), 0)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, detail.getBookingID());
            ps.setInt(2, detail.getRoomID());
            ps.setBigDecimal(3, detail.getPrice());
            ps.setInt(4, detail.getNights());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.getLogger(BookingDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    public String getStatusById(int bookingId) {
        String status = null;
        String sql = "SELECT Status FROM Booking WHERE bookingID = ? AND isDeleted = 0";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    status = rs.getString("Status");
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(BookingDao.class.getName()).log(Level.SEVERE, null, e);
        }

        return status;
    }

}
