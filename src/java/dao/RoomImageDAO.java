/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import entity.RoomDetail;
import entity.RoomImage;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author Admin
 */
public class RoomImageDAO extends DBContext {

    public List<RoomImage> getListRoomImgByDetailID(int id) {
        List<RoomImage> list = new ArrayList<>();
        String sql = "Select * from RoomImage where RoomDetailId = ?";
        try{
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                RoomDetail detail = new dao.RoomDetailDAO().getRoomDetailByID(id);
                list.add(new RoomImage(rs.getInt("ImageID"),detail , rs.getString("imageURL"), rs.getString("Caption"),rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"),
                        rs.getDate("DeletedAt"),
                        rs.getInt("DeletedBy"),  
                        rs.getBoolean("IsDeleted")));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return list;
    }
    public static void main(String[] args) {
        List<RoomImage> list = new dao.RoomImageDAO().getListRoomImgByDetailID(1);
        for (RoomImage roomImage : list) {
            System.out.println(roomImage.getCaption());
        }
    }
}
