/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import entity.Room;
import entity.RoomReview;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class RoomReviewDAO extends DBContext {

    public RoomReview getReviewByUserAndRoom(int userId, int roomId) {
        String sql = "SELECT * FROM RoomReview WHERE RoomID = ? AND UserID = ? AND IsDeleted = 0";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, roomId);
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Room r = new dao.RoomDAO().getRoomByRoomID(roomId);
                RoomReview rr = new RoomReview(rs.getInt("roomreviewid"),
                        r, rs.getInt("userId"), rs.getDouble("rating"),
                        rs.getString("comment"));
                return rr;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertRating(int roomId, int userId, int rating) {
        String sql = "INSERT INTO RoomReview (RoomID, UserID, Rating, CreatedAt, IsDeleted) VALUES (?, ?, ?, GETDATE(), 0)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, roomId);
            ps.setInt(2, userId);
            ps.setInt(3, rating);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getRatingByRoomId(int roomId, int userId) {
        String sql = "select rating from roomreview where roomid = ? and userid = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, roomId);
            ps.setInt(2, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("rating");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getAverageRatingByRoomId(int roomId) {
        String sql = "SELECT AVG(CAST(Rating AS FLOAT)) AS AvgRating FROM RoomReview WHERE RoomID = ? AND IsDeleted = 0";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, roomId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("AvgRating"); // Nếu không có kết quả, sẽ trả về 0.0
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0; // Trả về 0 nếu lỗi hoặc không có đánh giá
    }

    public static void main(String[] args) {
        System.out.println(new dao.RoomReviewDAO().getReviewByUserAndRoom(1, 1).getComment());
        System.out.println(new dao.RoomReviewDAO().insertRating(4, 4, 5));
        System.out.println(new dao.RoomReviewDAO().getRatingByRoomId(1, 52));
        System.out.println(new dao.RoomReviewDAO().getAverageRatingByRoomId(1));
    }

}
