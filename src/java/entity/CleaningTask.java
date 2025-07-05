/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.sql.Timestamp;

/**
 *
 * @author viet7
 */
public class CleaningTask {
    private int cleaningId;
    private int roomId;
    private String roomNumber;
    private String roomType;
    private String status;
    private Timestamp startTime;
    private String note;

    public CleaningTask() {
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getCleaningId() {
        return cleaningId;
    }

    public void setCleaningId(int cleaningHistoryId) {
        this.cleaningId = cleaningHistoryId;
    }

    
    @Override
    public String toString() {
        return "CleaningTask{" + "roomId=" + roomId + ", roomNumber=" + roomNumber + ", roomType=" + roomType + ", status=" + status + ", startTime=" + startTime + ", note=" + note + '}';
    }
    
    
}
