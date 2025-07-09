/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import entity.Booking;
import entity.BookingDetails;
import entity.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
        String sql = "UPDATE Booking SET Status = ?, UpdatedAt = GETDATE() WHERE BookingID = ?";
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
            ps.setInt(1, detail.getBookingId());
            ps.setInt(2, detail.getRoomId());
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

    public List<Booking> getBookings(
            int userRoleId,
            int currentUserId,
            int roomId,
            String status,
            String sortBy,
            String keyword,
            boolean isAsc,
            int pageIndex,
            int pageSize,
            Boolean isDeleted
    ) {
        List<Booking> list = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            StringBuilder sql = new StringBuilder(
                    "SELECT b.* FROM Booking b "
                    + "JOIN [User] u ON b.UserID = u.UserID "
                    + "Join bookingdetail bd on bd.BookingID = b.BookingID WHERE 1=1"
            );

            if (roomId != -1) {
                sql.append(" AND bd.RoomID = ?  ");
            }

            if (keyword != null && !keyword.trim().isEmpty()) {
                sql.append(" AND (u.FirstName LIKE ? OR u.LastName LIKE ? ) ");
            }
            if (status != null && !status.trim().isEmpty()) {
                sql.append(" AND b.Status = ?");
            }

            if (isDeleted != null && !isDeleted) {
                sql.append(" AND b.IsDeleted = ?");
            }

            if (userRoleId == 5) { // giả sử roleId=3 là guest
                sql.append(" AND b.UserID = ?");
            }

            if (sortBy == null || sortBy.isEmpty()) {
                sortBy = "b.CreatedAt";
            }

            sql.append(" ORDER BY ").append(sortBy).append(isAsc ? " ASC" : " DESC");
            if (pageSize > 0) {
                sql.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
            }

            System.out.println(sql.toString());

            ps = connection.prepareStatement(sql.toString());

            int index = 1;

            if (roomId != -1) {
                ps.setInt(index++, roomId);
            }

            if (keyword != null && !keyword.trim().isEmpty()) {
                String kw = "%" + keyword + "%";
                ps.setString(index++, kw);
                ps.setString(index++, kw);
            }

            if (status != null && !status.trim().isEmpty()) {
                ps.setString(index++, status);
            }

            if (isDeleted != null && !isDeleted) {
                ps.setBoolean(index++, isDeleted);
            }

            if (userRoleId == 5) {
                ps.setInt(index++, currentUserId);
            }

            if (pageSize > 0) {
                ps.setInt(index++, pageIndex * pageSize);
                ps.setInt(index++, pageSize);
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                Booking b = new Booking(
                        rs.getInt("bookingid"),
                        rs.getInt("userid"),
                        rs.getInt("voucherid"),
                        rs.getTimestamp("bookingdate"),
                        rs.getDate("checkindate"),
                        rs.getDate("checkoutdate"),
                        rs.getBigDecimal("totalamount"),
                        rs.getString("status"),
                        rs.getTimestamp("CreatedAt"),
                        rs.getTimestamp("UpdatedAt"),
                        rs.getTimestamp("DeletedAt"),
                        rs.getInt("DeletedBy"),
                        rs.getBoolean("IsDeleted")
                );
                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public int countBookings(String keyword, String status, int roomId) {
        int total = 0;
        try {
            StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM Booking b "
                    + "JOIN [User] u ON b.UserID = u.UserID "
                    + "Join bookingdetail bd on bd.bookingid = b.bookingid "
                    + "WHERE 1=1 ");

            if (roomId != -1) {
                sql.append(" AND bd.RoomID = ?  ");
            }
            if (keyword != null && !keyword.trim().isEmpty()) {
                sql.append(" AND ( u.FirstName LIKE ? OR u.LastName LIKE ?)");
            }

            if (status != null && !status.trim().isEmpty()) {
                sql.append(" AND b.Status = ?");
            }

            sql.append(" AND b.IsDeleted = 0");

            PreparedStatement ps = connection.prepareStatement(sql.toString());
            int i = 1;
            if (roomId != -1) {
                ps.setInt(i++, roomId);
            }
            if (keyword != null && !keyword.trim().isEmpty()) {
                String kw = "%" + keyword + "%";
                ps.setString(i++, kw);
                ps.setString(i++, kw);
            }
            if (status != null && !status.trim().isEmpty()) {
                ps.setString(i++, status);
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }

    public User getUserByBookingId(int bookingId) {
        User user = null;
        String sql = "select * from [user] where userid = (select userid from booking where bookingid = ?)";
        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setInt(1, bookingId);
            try (ResultSet rs = pre.executeQuery()) {
                if (rs.next()) {
                    user = new User(rs.getInt("userid"),
                            rs.getString("firstName"),
                            rs.getString("lastname"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("sex"),
                            rs.getTimestamp("birthDay"),
                            rs.getString("address"),
                            rs.getTimestamp("CreatedAt"),
                            rs.getTimestamp("UpdatedAt"),
                            rs.getTimestamp("DeletedAt"),
                            rs.getInt("DeletedBy"),
                            rs.getBoolean("IsDeleted"),
                            rs.getBoolean("isverifiedemail"),
                            rs.getInt("userroleid"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

}
