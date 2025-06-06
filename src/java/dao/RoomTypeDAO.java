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
        if(id != -1){
            sql += "   where RoomTypeID = ?";
        }
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            if(id != -1){
                pre.setInt(1, id);
            }
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                return new RoomType(rs.getInt("RoomTypeID"),
                        rs.getString("TypeName"),
                        rs.getString("description"),
                        rs.getString("imageurl"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"),
                        rs.getDate("DeletedAt"),
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
                        rs.getDate("CreatedAt"),
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
        List<RoomType> list = new dao.RoomTypeDAO().getListRoomType();
        System.out.println(new dao.RoomTypeDAO().getRoomTypeById(1).getDescription());
        for (RoomType roomType : list) {
            System.out.println(roomType.getTypeName());
        }
    }
}
