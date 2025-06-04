/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import entity.Room;
import entity.RoomDetail;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class RoomDetailDAO extends DBContext {

    public List<RoomDetail> getListRoomDetail() {
        List<RoomDetail> list = new ArrayList();
        String sql = "select * from RoomDetail";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {

                list.add(new RoomDetail(
                        rs.getInt("RoomDetailID"),
                        rs.getString("BedType"),
                        rs.getDouble("Area"),
                        rs.getInt("MaxGuest"),
                        rs.getString("Description"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"),
                        rs.getDate("DeletedAt"),
                        rs.getInt("DeletedBy"),  
                        rs.getBoolean("IsDeleted")
                ));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public RoomDetail getRoomDetailByID(int roomDetailID){
        String sql = "select * from RoomDetail where RoomDetailID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, roomDetailID);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {

                return new RoomDetail(rs.getInt("RoomDetailID"), 
                        rs.getString("BedType"), 
                        rs.getDouble("Area"), 
                        rs.getInt("MaxGuest"), 
                        rs.getString("Description"),rs.getDate(6), 
                        rs.getDate(7), rs.getDate(8), 
                        rs.getInt(9), rs.getBoolean(10));
            }
        } catch (SQLException e) {

        }
        return null;
    }

    public static void main(String[] args) {
        List<RoomDetail> t = new dao.RoomDetailDAO().getListRoomDetail();
        System.out.println("Số phần tử trong list: " + t.size());

        System.out.println(new dao.RoomDetailDAO().getRoomDetailByID(1).getBedType());
    }
}
