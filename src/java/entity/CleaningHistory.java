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
public class CleaningHistory {

    private int cleaningID;
    private int roomID;
    private int cleanerID;
    private Timestamp startTime;
    private Timestamp endTime;
    private String note;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Room room;

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public CleaningHistory() {
    }

    public int getCleaningID() {
        return cleaningID;
    }

    public void setCleaningID(int cleaningID) {
        this.cleaningID = cleaningID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getCleanerID() {
        return cleanerID;
    }

    public void setCleanerID(int cleanerID) {
        this.cleanerID = cleanerID;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "CleaningHistory{" + "cleaningID=" + cleaningID + ", roomID=" + roomID + ", cleanerID=" + cleanerID + ", startTime=" + startTime + ", endTime=" + endTime + ", note=" + note + ", status=" + status + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }
    
    
}
