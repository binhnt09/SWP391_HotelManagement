/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import entity.Hotel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import entity.Room;
import entity.RoomDetail;
import entity.RoomType;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class RoomDAO extends DBContext {

    public List<Room> getListRoom(Date checkin, Date checkout, double from, double to, int numberPeople, int roomTypeID) {
        List<Room> list = new ArrayList();
        String sql = "select r.RoomID , r.RoomNumber , r.RoomDetailID, r.RoomTypeID , r.Status , r.Price, r.HotelID , r.CreatedAt , r.UpdatedAt,r.DeletedAt , r.DeletedBy,r.IsDeleted\n"
                + "from Room r\n"
                + "inner join roomtype rt on rt.RoomTypeID = r.RoomTypeID\n"
                + "inner join RoomDetail rd on rd.RoomDetailID = r.RoomDetailID\n"
                + "where r.Status = 'available'\n";
        if (roomTypeID != -1) {
            sql += " and rt.RoomTypeID = ? ";
        }
        if (from != -1.0 || to != -1.0) {
            if (from != -1 && to != -1) {
                sql += " and (? <= r.Price and r.Price <= ?)";
            } else if (from == -1.0) {
                sql += " and r.price <= ?";
            } else if (to == -1) {
                sql += " and r.price >= ?";
            }
        }
        if (numberPeople != -1) {
            sql += " and rd.MaxGuest >= ?";
        }
        if (checkin != null || checkout != null) {
            sql += "	and r.RoomID not in(\n"
                    + "		select bd.RoomID from BookingDetail bd\n"
                    + "		inner join Booking b on b.BookingID = bd.BookingID "
                    + "   WHERE 1=1 ";
            if (checkin != null && checkout != null) {
                sql += " and (? < b.CheckOutDate and ? > b.CheckInDate)";
            } else if (checkin == null) {
                sql += " and ? > b.CheckInDate ";
            } else if (checkout == null) {
                sql += " and ? < b.CheckOutDate ";
            }
            sql += ")";
        }
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            int count = 1;

            if (roomTypeID != -1) {
                stm.setInt(count++, roomTypeID);
            }
            if (from != -1.0 || to != -1.0) {
                if (from != -1 && to != -1) {
                    stm.setDouble(count++, from);
                    stm.setDouble(count++, to);
                } else if (from == -1.0) {
                    stm.setDouble(count++, to);
                } else if (to == -1) {
                    stm.setDouble(count++, from);
                }
            }
            if (numberPeople != -1) {
                stm.setInt(count++, numberPeople);
            }
            if (checkin != null || checkout != null) {
                if (checkin != null && checkout != null) {

                    stm.setDate(count++, checkin);
                    stm.setDate(count++, checkout);
                } else if (checkin == null) {

                    stm.setDate(count++, checkout);
                } else if (checkout == null) {

                    stm.setDate(count++, checkin);
                }
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Hotel hotel = new dao.HotelDAO().getHotelByID(rs.getInt("HotelID"));
                RoomDetail roomDetail = new dao.RoomDetailDAO().getRoomDetailByID(rs.getInt("RoomDetailID"));
                RoomType roomType = new dao.RoomTypeDAO().getRoomTypeById(roomTypeID);

                list.add(new Room(rs.getInt("roomID"),
                        rs.getString("roomNumber"),
                        roomDetail, roomType,
                        rs.getString("status"),
                        rs.getDouble("price"), hotel,
                        rs.getTimestamp("CreatedAt"),
                        rs.getTimestamp("UpdatedAt"),
                        rs.getTimestamp("DeletedAt"),
                        rs.getInt("DeletedBy"),  
                        rs.getBoolean("IsDeleted")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public Room getRoomByRoomID(int id) {
        String sql = "select * from Room where RoomID =?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                Hotel hotel = new dao.HotelDAO().getHotelByID(rs.getInt("HotelID"));
                RoomDetail roomDetail = new dao.RoomDetailDAO().getRoomDetailByID(rs.getInt("RoomDetailID"));
                RoomType roomType = new dao.RoomTypeDAO().getRoomTypeById(rs.getInt("roomtypeid"));
                return new Room(rs.getInt("roomID"),
                        rs.getString("roomNumber"),
                        roomDetail, roomType,
                        rs.getString("status"),
                        rs.getDouble("price"), hotel,
                        rs.getTimestamp("CreatedAt"),
                        rs.getTimestamp("UpdatedAt"),
                        rs.getTimestamp("DeletedAt"),
                        rs.getInt("DeletedBy"),  
                        rs.getBoolean("IsDeleted"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Room> getAllRoom() {
        List<Room> list = new ArrayList<>();
        String sql = "Select * from room";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Hotel hotel = new dao.HotelDAO().getHotelByID(rs.getInt("HotelID"));
                RoomDetail roomDetail = new dao.RoomDetailDAO().getRoomDetailByID(rs.getInt("RoomDetailID"));
                RoomType roomType = new dao.RoomTypeDAO().getRoomTypeById(rs.getInt("roomtypeid"));
                list.add(new Room(rs.getInt("roomID"),
                        rs.getString("roomNumber"),
                        roomDetail, roomType,
                        rs.getString("status"),
                        rs.getDouble("price"), hotel,
                        rs.getTimestamp("CreatedAt"),
                        rs.getTimestamp("UpdatedAt"),
                        rs.getTimestamp("DeletedAt"),
                        rs.getInt("DeletedBy"),  
                        rs.getBoolean("IsDeleted")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
//        List<Room> list = new dao.RoomDAO().getListRoom(null, null, -1, -1, -1, 1);
//        for (Room room : list) {
//            System.out.println(room.getRoomTypeID());
//        }
        System.out.println(new dao.RoomDAO().getRoomByRoomID(5).getPrice());
    }

}
