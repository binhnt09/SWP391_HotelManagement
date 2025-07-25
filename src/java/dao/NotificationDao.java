/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import entity.Notifications;
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
public class NotificationDao extends DBContext {

    public List<Notifications> getNotificationsByUserId(int userId) {
        List<Notifications> list = new ArrayList<>();
        String sql = "SELECT * FROM Notifications WHERE UserID = ? ORDER BY CreatedAt DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql);) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Notifications noti = new Notifications();
                noti.setNotificationId(rs.getInt("NotificationId"));
                noti.setUserId(new dao.AuthenticationDAO().findUserById(rs.getInt("NotificationID")));
                noti.setMessage(rs.getString("Message"));
                noti.setType(rs.getString("Type"));
                noti.setCreatedAt(rs.getTimestamp("CreatedAt"));
                list.add(noti);
            }
            rs.close();
            ps.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean deleteNotificationById(int id) {
        String sql = "DELETE FROM Notifications WHERE NotificationID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

     
    public boolean addNotifications(int userId, String mess, String type) {
        String sql = "INSERT INTO Notifications (userid, Message, type, createdat) "
                + "VALUES (?, ?, ?, GETDATE())";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            String title = switch (type.toLowerCase()) {
                case "success" ->
                    "Thành công!";
                case "error" ->
                    "Lỗi!";
                case "warning" ->
                    "Cảnh báo!";
                default ->
                    "Thông báo";
            };

            ps.setInt(1, userId);
            ps.setString(2, mess);
            ps.setString(3, title);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public int countNotification(int userId) {
        String sql = "Select count(*) from Notifications where userID = ? ";
        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setInt(1, userId);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NotificationDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public static void main(String[] args) {
        System.out.println(new dao.NotificationDao().UpdateNotificationReadById(120));
    }

    public boolean UpdateNotificationReadById(int id) {
        String sql = "UPDATE Notifications set IsRead = 1 where NotificationId = ?;";
        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setInt(1, id);
            return pre.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(NotificationDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    

}
