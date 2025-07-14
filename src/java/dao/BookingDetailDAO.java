/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import entity.Booking;
import entity.BookingDetails;
import entity.Room;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class BookingDetailDAO extends DBContext {

    public BookingDetails getBookingDetailByBookingId(int id) {
        BookingDetails detail = null;
        String sql = "select bookingdetailid, bookingid ,roomid, price,nights,createdat,updatedat,deletedat,deletedby,isdeleted "
                + "from bookingdetail "
                + "where bookingid = ? ";
        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setInt(1, id);
            try (ResultSet rs = pre.executeQuery()) {
                if (rs.next()) {
                    Room room = new dao.RoomDAO().getRoomByRoomID(rs.getInt("roomid"));
                    Booking book = new dao.BookingDao().getBookingById(rs.getInt("bookingid"));

                    detail = new BookingDetails(rs.getInt("bookingdetailid"),
                            rs.getBigDecimal("price"),
                            rs.getInt("nights"),
                            rs.getTimestamp("CreatedAt"),
                            rs.getTimestamp("UpdatedAt"),
                            rs.getTimestamp("DeletedAt"),
                            rs.getInt("DeletedBy"),
                            rs.getBoolean("IsDeleted"), book, room);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(BookingDetailDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return detail;
    }

    public BookingDetails getDetailByBookingId(int id) {
        String sql = """
                     select bd.bookingdetailid, bd.bookingid , bd.roomid, bd.price, bd.nights, bd.createdat, 
                        bd.updatedat, bd.deletedat, bd.deletedby, bd.isdeleted , b.BookingID 
                     from bookingdetail bd
                        JOIN Booking b ON bd.BookingID = b.BookingID
                     where bd.bookingid = ? """;
        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setInt(1, id);
            try (ResultSet rs = pre.executeQuery()) {
                if (rs.next()) {
                    return extractBookingDetails(rs);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(BookingDetailDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    private BookingDetails extractBookingDetails(ResultSet rs) throws SQLException {
        BookingDetails bd = new BookingDetails();
        bd.setBookingDetailId(rs.getInt("BookingDetailID"));
        bd.setBookingId(rs.getInt("bookingid"));
        bd.setRoomId(rs.getInt("roomid"));
        bd.setPrice(rs.getBigDecimal("price"));
        bd.setNights(rs.getInt("nights"));
        bd.setCreatedAt(rs.getTimestamp("CreatedAt"));
        bd.setUpdatedAt(rs.getTimestamp("UpdatedAt"));
        bd.setDeletedAt(rs.getTimestamp("DeletedAt"));
        bd.setDeletedBy(rs.getInt("DeletedBy"));
        bd.setIsDeleted(rs.getBoolean("IsDeleted"));
        return bd;
    }

    public List<Integer> getAllRoomIdByBookingDetail() {
        List<Integer> list = new ArrayList<>();
        String sql = "select DISTINCT roomid from BookingDetail";
        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            try (ResultSet rs = pre.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getInt("roomid"));
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(BookingDetailDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    public boolean insertBookingDetail(BookingDetails bd) {
        String sql = "INSERT INTO BookingDetail (BookingID, RoomID, Price, Nights) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, bd.getBookingId());
            ps.setInt(2, bd.getRoomId());
            ps.setBigDecimal(3, bd.getPrice());
            ps.setInt(4, bd.getNights());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
