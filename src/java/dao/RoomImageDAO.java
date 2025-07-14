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
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                RoomDetail detail = new dao.RoomDetailDAO().getRoomDetailByID(id);
                list.add(new RoomImage(rs.getInt("ImageID"), detail, rs.getString("imageURL"), rs.getString("Caption"),
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
    public RoomImage getImgByImgId(int id){
        RoomImage  img = null;
        String sql = "select * from RoomImage where imageid = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return new RoomImage(rs.getInt("ImageID"), rs.getString("imageURL"), rs.getString("Caption"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return img;
    }

    public boolean saveImageToDB(int roomDetailId, String imageUrl, String caption) {
        String sql = "INSERT INTO RoomImage (RoomDetailID, ImageURL, Caption , UpdatedAt) VALUES (?, ?, ?,GETDATE())";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, roomDetailId);
            ps.setString(2, imageUrl);
            ps.setString(3, caption);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteRoomImages(List<Integer> imageIds) {
        if (imageIds == null || imageIds.isEmpty()) {
            return false;
        }

        StringBuilder sql = new StringBuilder("DELETE FROM RoomImage WHERE ImageID IN (");
        for (int i = 0; i < imageIds.size(); i++) {
            sql.append("?");
            if (i < imageIds.size() - 1) {
                sql.append(", ");
            }
        }
        sql.append(")");
        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < imageIds.size(); i++) {
                ps.setInt(i + 1, imageIds.get(i));
            }
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
