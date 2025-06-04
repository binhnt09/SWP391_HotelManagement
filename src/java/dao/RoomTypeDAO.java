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
public class RoomTypeDAO extends DBContext{
    
    public RoomType getRoomTypeById(int id){
        String sql = "select * from roomtype where RoomTypeID = ?";
        try{
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            if(rs.next()){
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
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    public static void main(String[] args) {
        System.out.println(new dao.RoomTypeDAO().getRoomTypeById(1).getDescription());
    }
}
