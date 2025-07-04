/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import entity.CleaningHistory;
import entity.Room;
import entity.RoomType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author viet7
 */
public class CleaningHistoryDAO extends DBContext {

    public boolean startCleaning(int roomId, int cleanerId) {
        String sql = "INSERT INTO CleaningHistory (RoomID, CleanerID, StartTime, Status) VALUES (?, ?, GETDATE(), 'InProgress')";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, roomId);
            ps.setInt(2, cleanerId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.getLogger(CleaningHistoryDAO.class.getName()).log(Level.SEVERE, null, e);

        }

        String updateRoom = "UPDATE Room SET Status = 'Cleaning', UpdatedAt = GETDATE() WHERE RoomID = ?";
        try (PreparedStatement ps = connection.prepareStatement(updateRoom)) {
            ps.setInt(1, roomId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.getLogger(CleaningHistoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    public boolean finishCleaning(int roomId, int cleaneingId, String note) {
        String sql = "UPDATE CleaningHistory SET EndTime = GETDATE(), Note = ?, Status = 'Completed', UpdatedAt = GETDATE() "
                + "WHERE CleaningID = ?";
        String updateRoom = "UPDATE Room SET Status = 'Available', UpdatedAt = GETDATE() WHERE RoomID = ?";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement ps1 = connection.prepareStatement(sql)) {
                ps1.setString(1, note);
                ps1.setInt(2, cleaneingId);
                int updatedHistory = ps1.executeUpdate();

                if (updatedHistory == 0) {
                    connection.rollback();
                    return false;
                }
            }

            try (PreparedStatement ps2 = connection.prepareStatement(updateRoom)) {
                ps2.setInt(1, roomId);
                int updatedRoom = ps2.executeUpdate();

                if (updatedRoom == 0) {
                    connection.rollback();
                    return false;
                }
            }

            connection.commit();
            return true;

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(CleaningHistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CleaningHistoryDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                Logger.getLogger(CleaningHistoryDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        return false;
    }

    public List<Room> getListRoomForCleaner() {
        List<Room> list = new ArrayList<>();
        String sql = "select * from room r\n"
                + "join RoomType rt on r.RoomTypeID =rt.RoomTypeID\n"
                + "where (r.status = 'Checkout' or r.status ='Cleaning') and r.IsDeleted =0";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Room r = new Room();
                r.setRoomID(rs.getInt("RoomID"));
                r.setRoomNumber(rs.getString("RoomNumber"));
                r.setStatus(rs.getString("Status"));
                RoomType rt = new RoomType();
                rt.setRoomTypeID(rs.getInt("RoomTypeID"));
                rt.setTypeName(rs.getString("TypeName"));
                r.setRoomType(rt);
                list.add(r);
            }
        } catch (SQLException e) {
            Logger.getLogger(CleaningHistoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    public boolean updateRoomStatus(int roomid, String status) {
        String sql = "update room\n"
                + "set status = ? \n"
                + "where RoomID=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, roomid);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.getLogger(CleaningHistoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    public List<CleaningHistory> getCleaningHistoryByCleanerInProgress(int cleanerId) {
        List<CleaningHistory> list = new ArrayList<>();
        String sql = "select * from CleaningHistory c\n"
                + "join Room r on c.RoomID = r.RoomID\n"
                + "join RoomType rt on r.RoomTypeID = rt.RoomTypeID\n"
                + "where cleanerID = ? and c.status = 'InProgress' ";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, cleanerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CleaningHistory ch = new CleaningHistory();
                ch.setCleaningID(rs.getInt("CleaningID"));
                Room r = new Room();
                r.setRoomID(rs.getInt("RoomID"));
                r.setRoomNumber(rs.getString("RoomNumber"));
                RoomType rt = new RoomType();
                rt.setTypeName(rs.getString("TypeName"));
                r.setRoomType(rt);
                ch.setRoom(r);
                ch.setStartTime(rs.getTimestamp("StartTime"));
                ch.setEndTime(rs.getTimestamp("EndTime"));
                ch.setNote(rs.getString("Note"));
                ch.setStatus(rs.getString("Status"));
                ch.setCreatedAt(rs.getTimestamp("CreatedAt"));
                ch.setUpdatedAt(rs.getTimestamp("UpdatedAt"));
                list.add(ch);
            }
        } catch (SQLException e) {
            Logger.getLogger(CleaningHistoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    public static void main(String[] args) {
        CleaningHistoryDAO dao = new CleaningHistoryDAO();

        List<Room> rooms = dao.getListRoomForCleaner();

        if (rooms.isEmpty()) {
            System.out.println("Không có phòng nào cần dọn.");
        } else {
            System.out.println("Danh sách phòng cần dọn:");
            for (Room room : rooms) {
                System.out.println("Room ID: " + room.getRoomID());
                System.out.println("Room Number: " + room.getRoomNumber());
                System.out.println("Room Type: " + room.getRoomType().getTypeName());
                System.out.println("---------------------------");
            }
        }

        dao.finishCleaning(20, 53, "oke");
    }

}
