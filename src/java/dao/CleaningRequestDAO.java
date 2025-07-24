/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import entity.CleaningRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author viet7
 */
public class CleaningRequestDAO extends DBContext {

    // 1. Thêm yêu cầu dọn phòng mới
    public boolean createRequest(int bookingId, int roomId, String note) {
        String sql = """
            INSERT INTO CleaningRequest (BookingID, RoomID, Note)
            VALUES (?, ?, ?)
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            ps.setInt(2, roomId);
            ps.setString(3, note);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy danh sách yêu cầu dọn phòng (cho cleaner)
    public List<CleaningRequest> getAllRequestsForCleaner() {
        List<CleaningRequest> list = new ArrayList<>();
        String sql = """
            SELECT cr.RequestID, cr.RoomID, cr.RequestedAt, cr.Note, cr.Status,
                   r.RoomNumber, rt.TypeName AS RoomTypeName
            FROM CleaningRequest cr
            JOIN Room r ON cr.RoomID = r.RoomID
            JOIN RoomType rt ON r.RoomTypeID = rt.RoomTypeID
            WHERE cr.IsDeleted = 0 AND cr.status != 'Completed'
            ORDER BY cr.Status, cr.RequestedAt DESC
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CleaningRequest cr = new CleaningRequest();
                cr.setRequestID(rs.getInt("RequestID"));
                cr.setRoomID(rs.getInt("RoomID"));
                cr.setRequestedAt(rs.getTimestamp("RequestedAt"));
                cr.setNote(rs.getString("Note"));
                cr.setStatus(rs.getString("Status"));
                cr.setRoomNumber(rs.getString("RoomNumber"));
                cr.setRoomTypeName(rs.getString("RoomTypeName"));
                list.add(cr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean startCleaning(int roomId, int cleanerId, int requestId) {
        String insertHistory = "INSERT INTO CleaningHistory (RoomID, CleanerID, StartTime, Status, RequestID) "
                + "VALUES (?, ?, GETDATE(), 'InProgress', ?)";

        String updateRequest = "UPDATE CleaningRequest SET Status = 'InProgress', UpdatedAt = GETDATE() "
                + "WHERE RequestID = ?";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement ps1 = connection.prepareStatement(insertHistory)) {
                ps1.setInt(1, roomId);
                ps1.setInt(2, cleanerId);
                ps1.setInt(3, requestId);
                int inserted = ps1.executeUpdate();
                if (inserted == 0) {
                    connection.rollback();
                    return false;
                }
            }

            try (PreparedStatement ps2 = connection.prepareStatement(updateRequest)) {
                ps2.setInt(1, requestId);
                int updated = ps2.executeUpdate();
                if (updated == 0) {
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
                Logger.getLogger(CleaningRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CleaningRequestDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                Logger.getLogger(CleaningRequestDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        return false;
    }

    public boolean finishCleaning(int requestId, int cleaningId, String note) {
        String updateCleaning = "UPDATE CleaningHistory SET EndTime = GETDATE(), Note = ?, Status = 'Completed', UpdatedAt = GETDATE() WHERE CleaningID = ?";

        String updateRequest = "UPDATE CleaningRequest SET Status = 'Completed', UpdatedAt = GETDATE() WHERE RequestID = ?";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement ps1 = connection.prepareStatement(updateCleaning)) {
                ps1.setString(1, note);
                ps1.setInt(2, cleaningId);
                int rows = ps1.executeUpdate();
                if (rows == 0) {
                    connection.rollback();
                    return false;
                }
            }

            try (PreparedStatement ps2 = connection.prepareStatement(updateRequest)) {
                ps2.setInt(1, requestId);
                int rows = ps2.executeUpdate();
                if (rows == 0) {
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
                Logger.getLogger(CleaningRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CleaningRequestDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                Logger.getLogger(CleaningRequestDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        return false;
    }
}
