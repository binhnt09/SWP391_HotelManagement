/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.sql.Timestamp;

/**
 *
 * @author Admin
 */
public class Room {
    private int roomID;
    private String roomNumber ; // Tên phòng: VD 101 ,...
    private RoomDetail roomDetail;
    private RoomType roomTypeID;
    private String status;
    private double price;
    
    private Hotel hotel;
    
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;
    private int deletedBy;
    private boolean isDeleted;

    public Room() {
    }

    public Room(int roomID, String roomNumber, RoomDetail roomDetail, RoomType roomTypeID, String status, double price) {
        this.roomID = roomID;
        this.roomNumber = roomNumber;
        this.roomDetail = roomDetail;
        this.roomTypeID = roomTypeID;
        this.status = status;
        this.price = price;
    }
    

    public Room(int roomID, String roomNumber, RoomDetail roomDetail, String status, double price, Hotel hotel, Timestamp createdAt, Timestamp updatedAt, Timestamp deletedAt, int deletedBy, boolean isDeleted) {
        this.roomID = roomID;
        this.roomNumber = roomNumber;
        this.roomDetail = roomDetail;
        this.status = status;
        this.price = price;
        this.hotel = hotel;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.deletedBy = deletedBy;
        this.isDeleted = isDeleted;
    }

    public Room(int roomID, String roomNumber, RoomDetail roomDetail, RoomType roomTypeID, String status, double price, Hotel hotel, Timestamp createdAt, Timestamp updatedAt, Timestamp deletedAt, int deletedBy, boolean isDeleted) {
        this.roomID = roomID;
        this.roomNumber = roomNumber;
        this.roomDetail = roomDetail;
        this.roomTypeID = roomTypeID;
        this.status = status;
        this.price = price;
        this.hotel = hotel;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.deletedBy = deletedBy;
        this.isDeleted = isDeleted;
    }

    public RoomType getRoomTypeID() {
        return roomTypeID;
    }

    public void setRoomTypeID(RoomType roomTypeID) {
        this.roomTypeID = roomTypeID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public RoomDetail getRoomDetail() {
        return roomDetail;
    }

    public void setRoomDetail(RoomDetail roomDetail) {
        this.roomDetail = roomDetail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
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

    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
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
