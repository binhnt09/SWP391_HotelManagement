/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.sql.Date;

/**
 *
 * @author Admin
 */
public class RoomDetail {

    private int roomDetailID;
    private String bedType; // Loại giường: king , queen ,....
    private double area;
    private int maxGuest;
    private String description;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    private int deletedBy;
    private boolean isDeleted;

    public RoomDetail() {
    }

    public RoomDetail(int roomDetailID, String bedType, double area, int maxGuest, String description, Date createdAt, Date updatedAt, Date deletedAt, int deletedBy, boolean isDeleted) {
        this.roomDetailID = roomDetailID;
        this.bedType = bedType;
        this.area = area;
        this.maxGuest = maxGuest;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.deletedBy = deletedBy;
        this.isDeleted = isDeleted;
    }

    public int getRoomDetailID() {
        return roomDetailID;
    }

    public void setRoomDetailID(int roomDetailID) {
        this.roomDetailID = roomDetailID;
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public int getMaxGuest() {
        return maxGuest;
    }

    public void setMaxGuest(int maxGuest) {
        this.maxGuest = maxGuest;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public int getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(int deletedBy) {
        this.deletedBy = deletedBy;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    
    
    

}
