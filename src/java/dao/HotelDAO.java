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

/**
 *
 * @author Admin
 */
public class HotelDAO extends DBContext {
    
    

    public Hotel getHotelByID(int hotelID) {
        String sql = "select * from Hotel where HotelID = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)){
            stm.setInt(1, hotelID);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {

                return new Hotel(
                        rs.getInt("HotelID"),
                        rs.getString("Name"),
                        rs.getString("Address"),
                        rs.getString("Description"),
                        rs.getString("Facilities"),
                        rs.getTimestamp("CreatedAt"),
                        rs.getTimestamp("UpdatedAt"),
                        rs.getTimestamp("DeletedAt"),
                        rs.getInt("DeletedBy"),  
                        rs.getBoolean("IsDeleted")
                );

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
