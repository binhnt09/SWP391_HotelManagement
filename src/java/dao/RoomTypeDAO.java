/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import entity.RoomType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class RoomTypeDAO extends DBContext {

    public RoomType getRoomTypeById(int id) {
        String sql = "select * from roomtype";
        if (id != -1) {
            sql += "   where RoomTypeID = ?";
        }
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            if (id != -1) {
                pre.setInt(1, id);
            }
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                return new RoomType(rs.getInt("RoomTypeID"),
                        rs.getString("TypeName"),
                        rs.getString("description"),
                        rs.getString("imageurl"),
                        rs.getInt("NumberPeople"),
                        rs.getString("Amenity"),
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

    public List<RoomType> getListRoomType() {
        List<RoomType> list = new ArrayList<>();
        String sql = "select * from RoomType";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                list.add(new RoomType(rs.getInt("RoomTypeID"),
                        rs.getString("TypeName"),
                        rs.getString("description"),
                        rs.getString("imageurl"),
                        rs.getInt("numberPeople"),
                        rs.getString("amenity"),
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

    public boolean updateRoomType(RoomType type) {
        String sql = "UPDATE RoomType SET TypeName = ?, description = ?, imageurl = ?, numberPeople = ?, amenity = ?, UpdatedAt = GETDATE() WHERE RoomTypeID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql);){
            ps.setString(1, type.getTypeName());
            ps.setString(2, type.getDescription());
            ps.setString(3, type.getImageUrl());
            ps.setInt(4, type.getNumberPeople());
            ps.setString(5, type.getAmenity());
            ps.setInt(6, type.getRoomTypeID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addNewRoomType(String typeName, int numberPeople, String amenity, String description) {
        String sql = "insert into RoomType (TypeName,NumberPeople,amenity,description,ImageURL,createdAt) values (?,?,?,?,?, GETDATE())";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, typeName);
            ps.setInt(2, numberPeople);
            ps.setString(3, amenity);
            ps.setString(4, description);
            ps.setString(5, "");

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        List<RoomType> list = new dao.RoomTypeDAO().getListRoomType();
        System.out.println(new dao.RoomTypeDAO().getRoomTypeById(1).getDescription());
        for (RoomType roomType : list) {
            System.out.println(roomType.getAmenity());
        }
        System.out.println(new dao.RoomTypeDAO().updateRoomType(new RoomType(15, "hieu dep trai", "jlsaf shf ", "", 4, "anh , hiue có,wifi ,free , khogn the")));
    }
}
