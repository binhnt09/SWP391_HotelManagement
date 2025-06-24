/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import entity.Booking;
import entity.BookingDetails;
import entity.Room;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
            e.printStackTrace();
        }
        return detail;
    }
    public static void main(String[] args) {
        System.out.println(new dao.BookingDetailDAO().getBookingDetailByBookingId(1).getBookId());
    }
}
