/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import entity.Booking;
import entity.BookingDetails;
import entity.BookingInfo;
import entity.User;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

    public Booking findUserByBookingId(int bookingId) {
        Booking booking = null;

        String sql = "SELECT b.bookingID, b.UserID, b.VoucherID, b.BookingDate, b.CheckInDate, "
                + "b.CheckOutDate, b.TotalAmount, b.Status, b.CreatedAt, b.UpdatedAt, "
                + "b.deletedAt, b.deletedBy, b.isDeleted, u.UserID, u.email "
                + "FROM Booking b "
                + "JOIN [User] u ON b.UserID = u.userID "
                + "WHERE b.bookingID = ? AND b.isDeleted = 0";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    booking = new Booking();
                    booking.setBookingId(rs.getInt("bookingID"));
                    booking.setUserId(rs.getInt("UserID"));
                    booking.setVoucherId(rs.getObject("VoucherID") != null ? rs.getInt("VoucherID") : null);
                    booking.setBookingDate(rs.getTimestamp("BookingDate"));
                    booking.setCheckInDate(rs.getDate("CheckInDate"));
                    booking.setCheckOutDate(rs.getDate("CheckOutDate"));
                    booking.setTotalAmount(rs.getBigDecimal("TotalAmount"));
                    booking.setStatus(rs.getString("Status"));
                    booking.setCreatedAt(rs.getTimestamp("CreatedAt"));
                    booking.setUpdatedAt(rs.getTimestamp("UpdatedAt"));
                    booking.setDeletedAt(rs.getTimestamp("DeletedAt"));
                    booking.setDeletedBy(rs.getObject("DeletedBy") != null ? rs.getInt("DeletedBy") : null);
                    booking.setIsDeleted(rs.getBoolean("isDeleted"));

                    User user = new User();
                    user.setUserId(rs.getInt("userId"));
                    user.setEmail(rs.getString("email"));
                    booking.setUser(user);
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

    public boolean updateActualCheckin(int bookingId) {
        String sql = "UPDATE Booking SET ActualCheckIn = getdate(), UpdatedAt = GETDATE() WHERE BookingID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
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

    public List<BookingInfo> getFutureBookings(int roomId) {
        List<BookingInfo> list = new ArrayList<>();

        String sql = """
        SELECT b.BookingID, b.CheckInDate, b.CheckOutDate, b.Status,
               u.FirstName, u.LastName, u.phone
        FROM BookingDetail bd
        JOIN Booking b ON bd.BookingID = b.BookingID AND b.IsDeleted = 0
        JOIN [User] u ON b.UserID = u.UserID AND u.IsDeleted = 0
        WHERE bd.RoomID = ? AND bd.IsDeleted = 0 and CAST(b.CheckOutDate AS DATE) >= CAST(GETDATE() AS DATE) and b.Status IN ('Checked-in','Confirmed')
        ORDER BY b.CheckInDate ASC
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, roomId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BookingInfo booking = new BookingInfo();
                booking.setBookingID(rs.getInt("BookingID"));
                booking.setCheckInDate(rs.getTimestamp("CheckInDate"));
                booking.setCheckOutDate(rs.getTimestamp("CheckOutDate"));
                booking.setStatus(rs.getString("Status"));

                String fullName = ((rs.getString("FirstName") != null ? rs.getString("FirstName") : "") + " "
                        + (rs.getString("LastName") != null ? rs.getString("LastName") : "")).trim();
                booking.setGuestName(fullName);
                booking.setPhone(rs.getString("phone"));

                list.add(booking);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public BookingInfo getCurrentStay(int roomId) {
        String sql = """
        SELECT TOP 1 
            u.FirstName, u.LastName, u.phone,
            b.CheckInDate, b.CheckOutDate,
            b.TotalAmount, b.Status, b.BookingID
        FROM BookingDetail bd
        JOIN Booking b ON bd.BookingID = b.BookingID
        JOIN [User] u ON b.UserID = u.UserID
        WHERE bd.RoomID = ?
          AND b.IsDeleted = 0
          AND b.Status IN ('Checked-in')
          AND CAST(GETDATE() AS DATE) BETWEEN CAST(b.CheckInDate AS DATE) AND CAST(b.CheckOutDate AS DATE)
        ORDER BY b.CheckInDate DESC
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, roomId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    BookingInfo info = new BookingInfo();
                    String fullName = (rs.getString("FirstName") + " " + rs.getString("LastName")).trim();
                    info.setGuestName(fullName);
                    info.setCheckInDate(rs.getTimestamp("CheckInDate"));
                    info.setCheckOutDate(rs.getTimestamp("CheckOutDate"));
                    info.setTotalAmount(rs.getBigDecimal("TotalAmount"));
                    info.setStatus(rs.getString("Status"));
                    info.setPhone(rs.getString("phone"));
                    info.setBookingID(rs.getInt("BookingID"));
                    return info;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public BookingInfo getClosestReservedBooking(int roomId) {
        String sql = """
        SELECT TOP 1 
            u.FirstName, u.LastName, u.phone,
            b.CheckInDate, b.CheckOutDate,
            b.TotalAmount, b.Status, b.BookingID
        FROM BookingDetail bd
        JOIN Booking b ON bd.BookingID = b.BookingID
        JOIN [User] u ON b.UserID = u.UserID
        WHERE bd.RoomID = ?
          AND b.IsDeleted = 0
          AND b.Status IN ('Confirmed') 
          AND CAST(b.CheckOutDate AS DATE) >= CAST(GETDATE() AS DATE) 
        ORDER BY b.CheckInDate ASC
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, roomId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    BookingInfo info = new BookingInfo();
                    String fullName = (rs.getString("FirstName") + " " + rs.getString("LastName")).trim();
                    info.setGuestName(fullName);
                    info.setCheckInDate(rs.getTimestamp("CheckInDate"));
                    info.setCheckOutDate(rs.getTimestamp("CheckOutDate"));
                    info.setTotalAmount(rs.getBigDecimal("TotalAmount"));
                    info.setStatus(rs.getString("Status"));
                    info.setPhone(rs.getString("phone"));
                    info.setBookingID(rs.getInt("BookingID"));
                    return info;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateRoomStatus(int roomId, String status) {
        String sql = "UPDATE Room SET Status = ?, UpdatedAt = GETDATE() WHERE RoomID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, roomId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int insertBookingForGuest(Booking booking) {
        String sql = "INSERT INTO Booking (UserID, CheckInDate, CheckOutDate, TotalAmount, Status, actualcheckin) VALUES (?, ?, ?, ?, ?, getdate())";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, booking.getUserId());
            ps.setTimestamp(2, Timestamp.valueOf(booking.getCheckInDate().toString() + " 14:00:00")); // Check-in: 14:00
            ps.setTimestamp(3, Timestamp.valueOf(booking.getCheckOutDate().toString() + " 12:00:00")); // Check-out: 12:00
            ps.setBigDecimal(4, booking.getTotalAmount());
            ps.setString(5, booking.getStatus());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // Trả về BookingID vừa insert
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean checkoutBooking(int bookingId, int roomId, int staffId) {
        PreparedStatement ps = null;
        try {
            connection.setAutoCommit(false); // Bắt đầu transaction

            // 1. Cập nhật trạng thái phòng
            String sqlRoom = "UPDATE Room SET Status = 'Checkout', UpdatedAt = GETDATE() WHERE RoomID = ?";
            ps = connection.prepareStatement(sqlRoom);
            ps.setInt(1, roomId);
            ps.executeUpdate();
            ps.close();

            // 2. Cập nhật trạng thái Booking
            String sqlBooking = """
            UPDATE Booking
            SET Status = 'Checked-out',
                ActualCheckOut = GETDATE(),
                UpdatedAt = GETDATE()
            WHERE BookingID = ?
        """;
            ps = connection.prepareStatement(sqlBooking);
            ps.setInt(1, bookingId);
            ps.executeUpdate();

            connection.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback(); // rollback nếu có lỗi
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                connection.setAutoCommit(true); // reset lại auto commit sau khi xong
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean cancelBooking(int bookingId, int roomId) throws SQLException {
        String updateBooking = "UPDATE Booking SET Status = 'Cancelled', UpdatedAt = GETDATE() "
                + "WHERE BookingID = ? AND IsDeleted = 0";

        // Kiểm tra còn booking hợp lệ nào khác không (trừ cái vừa hủy)
        String checkOtherBookings = """
                    SELECT COUNT(*) FROM BookingDetail bd
                    JOIN Booking b ON bd.BookingID = b.BookingID
                    WHERE bd.RoomID = ? AND bd.BookingID != ? AND b.IsDeleted = 0
                    AND b.Status IN ('Confirmed', 'Checked-in')
                    AND b.CheckOutDate >= GETDATE()
                """;

        String updateRoom = "UPDATE Room SET Status = 'Available', UpdatedAt = GETDATE() "
                + "WHERE RoomID = ? AND IsDeleted = 0";

        connection.setAutoCommit(false);
        try (PreparedStatement psBooking = connection.prepareStatement(updateBooking); PreparedStatement psCheck = connection.prepareStatement(checkOtherBookings); PreparedStatement psUpdateRoom = connection.prepareStatement(updateRoom)) {

            psBooking.setInt(1, bookingId);
            int affectedBooking = psBooking.executeUpdate();

            psCheck.setInt(1, roomId);
            psCheck.setInt(2, bookingId);
            ResultSet rs = psCheck.executeQuery();
            boolean shouldSetAvailable = true;
            if (rs.next()) {
                int count = rs.getInt(1);
                shouldSetAvailable = count == 0;
            }

            int affectedRoom = 1;
            if (shouldSetAvailable) {
                psUpdateRoom.setInt(1, roomId);
                affectedRoom = psUpdateRoom.executeUpdate();
            }

            if (affectedBooking > 0 && affectedRoom > 0) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
            }

        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        }
        return false;
    }

    public LocalDate getCheckInDateByBookingId(int bookingId) {
        String sql = "SELECT CheckInDate FROM Booking WHERE BookingID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDate("CheckInDate").toLocalDate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean cancelBooking(int bookingId) {
        String sql = "UPDATE Booking SET status = 'Canceled' WHERE bookingId = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
             
            ps.setInt(1, bookingId);
            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
