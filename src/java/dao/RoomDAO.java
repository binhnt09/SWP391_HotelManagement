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

    public List<Room> getRoomsByPage(int offset, int pageSize) {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT r.*, rt.TypeName, rd.BedType, rd.Area, rd.MaxGuest "
                + "FROM Room r "
                + "JOIN RoomType rt ON r.RoomTypeID = rt.RoomTypeID "
                + "JOIN RoomDetail rd ON r.RoomDetailID = rd.RoomDetailID "
                + "WHERE r.IsDeleted = 0 "
                + "ORDER BY r.CreatedAt DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, offset);
            stmt.setInt(2, pageSize);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Room r = new Room();
                r.setRoomID(rs.getInt("RoomID"));
                r.setRoomNumber(rs.getString("RoomNumber"));
                r.setStatus(rs.getString("Status"));
                r.setPrice(rs.getDouble("Price"));
                r.setCreatedAt(rs.getDate("CreatedAt"));
                r.setRoomTypeID(new RoomType());
                r.getRoomTypeID().setTypeName(rs.getString("TypeName"));
                r.setRoomDetail(new RoomDetail());
                r.getRoomDetail().setBedType(rs.getString("BedType"));
                r.getRoomDetail().setArea(rs.getFloat("Area"));
                r.getRoomDetail().setMaxGuest(rs.getInt("MaxGuest"));
                list.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countAllRooms() {
        String sql = "SELECT COUNT(*) FROM Room WHERE IsDeleted = 0";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Room> searchRoomsByPage(String keyword, int offset, int pageSize) {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT r.*, rt.TypeName, rd.BedType, rd.Area, rd.MaxGuest "
                + "FROM Room r "
                + "JOIN RoomType rt ON r.RoomTypeID = rt.RoomTypeID "
                + "JOIN RoomDetail rd ON r.RoomDetailID = rd.RoomDetailID "
                + "WHERE r.IsDeleted = 0 AND (r.RoomNumber LIKE ? OR rt.TypeName LIKE ? OR rd.BedType LIKE ?) "
                + "ORDER BY r.CreatedAt DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String kw = "%" + keyword + "%";
            stmt.setString(1, kw);
            stmt.setString(2, kw);
            stmt.setString(3, kw);
            stmt.setInt(4, offset);
            stmt.setInt(5, pageSize);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Room r = new Room();
                r.setRoomID(rs.getInt("RoomID"));
                r.setRoomNumber(rs.getString("RoomNumber"));
                r.setStatus(rs.getString("Status"));
                r.setPrice(rs.getDouble("Price"));
                r.setCreatedAt(rs.getDate("CreatedAt"));
                r.setRoomTypeID(new RoomType());
                r.getRoomTypeID().setTypeName(rs.getString("TypeName"));
                r.setRoomDetail(new RoomDetail());
                r.getRoomDetail().setBedType(rs.getString("BedType"));
                r.getRoomDetail().setArea(rs.getFloat("Area"));
                r.getRoomDetail().setMaxGuest(rs.getInt("MaxGuest"));
                list.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countSearchRooms(String keyword) {
        String sql = "SELECT COUNT(*) "
                + "FROM Room r "
                + "JOIN RoomType rt ON r.RoomTypeID = rt.RoomTypeID "
                + "JOIN RoomDetail rd ON r.RoomDetailID = rd.RoomDetailID "
                + "WHERE r.IsDeleted = 0 AND (r.RoomNumber LIKE ? OR rt.TypeName LIKE ? OR rd.BedType LIKE ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String kw = "%" + keyword + "%";
            stmt.setString(1, kw);
            stmt.setString(2, kw);
            stmt.setString(3, kw);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void deleteRoom(int id, int deletedBy) {
        String sql = "UPDATE Room SET IsDeleted = 1, DeletedAt = GETDATE(), DeletedBy = ? WHERE RoomID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, deletedBy);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        List<Room> list = new dao.RoomDAO().getListRoom();
//        for(int i =0 ;  i < list.size() ; i++){
//            System.out.println(list.get(i).getPrice());
//        }

        RoomDAO dao = new RoomDAO();
        List<Room> rooms = dao.getRoomsByPage(1, 10);
        System.out.println(dao.countAllRooms());
        for (Room r : rooms) {
            System.out.println("Room ID: " + r.getRoomID());
            System.out.println("Room Number: " + r.getRoomNumber());
            System.out.println("Status: " + r.getStatus());
            System.out.println("Price: " + r.getPrice());
            System.out.println("Created At: " + r.getCreatedAt());
            System.out.println("Type: " + r.getRoomTypeID().getTypeName());
            System.out.println("Bed Type: " + r.getRoomDetail().getBedType());
            System.out.println("Area: " + r.getRoomDetail().getArea());
            System.out.println("Max Guest: " + r.getRoomDetail().getMaxGuest());
            System.out.println("---------------------------");
        }
    }
}
