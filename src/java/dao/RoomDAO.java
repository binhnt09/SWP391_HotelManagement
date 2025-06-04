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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class RoomDAO extends DBContext {

//    public List<Room> getListRoom() {
//        List<Room> list = new ArrayList();
//        String sql = "select * from Room";
//        try {
//            PreparedStatement stm = connection.prepareStatement(sql);
//            ResultSet rs = stm.executeQuery();
//            while (rs.next()) {
//                Hotel hotel = new dao.HotelDAO().getHotelByID(rs.getInt("HotelID"));
//                RoomDetail roomDetail = new dao.RoomDetailDAO().getRoomDetailByID(rs.getInt("RoomDetailID"));
//
//                list.add(new Room(rs.getInt("RoomID"),
//                        rs.getString("RoomNumber"),
//                        roomDetail,
//                        rs.getString("Status"),
//                        rs.getDouble("Price"),
//                        hotel, rs.getDate("CreatedAt"),
//                        rs.getDate("UpdatedAt"),
//                        rs.getDate("DeletedAt"),
//                        rs.getInt("DeletedBy"),
//                        rs.getBoolean("IsDeleted")));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return list;
//    }
    public List<Room> getListRoom() {
        List<Room> list = new ArrayList();
        String sql = "select * from Room";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Hotel hotel = new dao.HotelDAO().getHotelByID(rs.getInt("HotelID"));
                RoomDetail roomDetail = new dao.RoomDetailDAO().getRoomDetailByID(rs.getInt("RoomDetailID"));
                RoomType roomType = new dao.RoomTypeDAO().getRoomTypeById(rs.getInt("roomtypeid"));

                list.add(new Room(rs.getInt("RoomID"),
                        rs.getString("RoomNumber"),
                        roomDetail,
                        roomType,
                        rs.getString("Status"),
                        rs.getDouble("Price"),
                        hotel, rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"),
                        rs.getDate("DeletedAt"),
                        rs.getInt("DeletedBy"),
                        rs.getBoolean("IsDeleted")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

 
    public static void main(String[] args) {
        List<Room> list = new dao.RoomDAO().getListRoom();
        for(int i =0 ;  i < list.size() ; i++){
            System.out.println(list.get(i).getPrice());
        }
    }

}
