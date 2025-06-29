/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import entity.Booking;
import entity.BookingDetails;
import java.math.BigDecimal;
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

    public int insertBooking(Booking booking) {
        String sql = "INSERT INTO Booking (UserID, VoucherID, BookingDate, CheckInDate, "
                + " CheckOutDate, TotalAmount, Status, CreatedAt, UpdatedAt, IsDeleted) "
                + " VALUES (?, ?, GETDATE(), ?, ?, ?, ?, GETDATE(), GETDATE(), 0)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, booking.getUserID());
            if (booking.getVoucherID() != null) {
                ps.setInt(2, booking.getVoucherID());
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

    public List<Booking> getBookings(
            int userRoleId,
            int currentUserId, // ID người đang đăng nhập (nếu là khách)
            String keyword,
            String status,
            String sortBy,
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
                    + "WHERE 1=1"
            );

            // Tìm kiếm theo keyword
            if (keyword != null && !keyword.trim().isEmpty()) {
                sql.append(" AND (CAST(b.BookingID AS NVARCHAR) LIKE ? ")
                        .append(" OR b.Status LIKE ? ")
                        .append(" OR u.FirstName LIKE ? ")
                        .append(" OR u.LastName LIKE ? )");
            }

            // Trạng thái booking
            if (status != null && !status.trim().isEmpty()) {
                sql.append(" AND b.Status = ?");
            }

            // Trạng thái xóa
            if (isDeleted != true) {
                sql.append(" AND b.IsDeleted = ?");
            }

            if (currentUserId == 5) {
                sql.append(" AND b.UserID = ?");
            }

            // Sắp xếp
//            if (sortBy == null || sortBy.isEmpty()) {
//                sortBy = "b.CreatedAt";
//            }
//            sql.append(" ORDER BY ").append(sortBy).append(isAsc ? " ASC" : " DESC");
            // Phân trang
//            sql.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
            // Tạo PreparedStatement và gán tham số
            ps = connection.prepareStatement(sql.toString());
            System.out.println(sql.toString());
            int index = 1;

            if (keyword != null && !keyword.trim().isEmpty()) {
                String kw = "%" + keyword + "%";
                ps.setString(index++, kw);
                ps.setString(index++, kw);
                ps.setString(index++, kw);
                ps.setString(index++, kw);
            }

            if (status != null && !status.trim().isEmpty()) {
                ps.setString(index++, status);
            }

            if (isDeleted != null) {
                ps.setBoolean(index++, isDeleted);
            }

            if (currentUserId == 5) {
                ps.setInt(index++, userRoleId); // truyền vào ID user đăng nhập
            }

//            ps.setInt(index++, pageIndex * pageSize);
//            ps.setInt(index++, pageSize);
            rs = ps.executeQuery();
            while (rs.next()) {

                Booking b = new Booking(rs.getInt("bookingid"),
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
                        rs.getBoolean("IsDeleted"));
                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public Booking getBookingById(int id) {
        Booking book = null;
        String sql = "select Bookingid ,UserID, VoucherID, BookingDate, CheckInDate, "
                + " CheckOutDate, TotalAmount, Status, CreatedAt, UpdatedAt, IsDeleted ,deletedat, deletedby"
                + " from booking"
                + " where bookingid = ?";
        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setInt(1, id);
            try (ResultSet rs = pre.executeQuery()) {
                if (rs.next()) {
                    book = new Booking(rs.getInt("bookingid"),
                            rs.getInt("userId"),
                            rs.getInt("voucherId"),
                            rs.getTimestamp("bookingdate"),
                            rs.getDate("checkindate"),
                            rs.getDate("checkoutdate"),
                            rs.getBigDecimal("totalamount"), 
                            rs.getString("status"),
                            rs.getTimestamp("CreatedAt"),
                            rs.getTimestamp("UpdatedAt"),
                            rs.getTimestamp("DeletedAt"),
                            rs.getInt("DeletedBy"),
                            rs.getBoolean("IsDeleted"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    public static void main(String[] args) {
        List<Booking> list= new dao.BookingDao().getBookings(3,5, null, null, null, true, 0, 0, false);
        for (Booking a : list) {
            System.out.println(a.getUserID());
        }
        System.out.println(new dao.BookingDao().getBookingById(1).getBookingID());
    }

}
