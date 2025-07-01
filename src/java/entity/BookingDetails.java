/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 *
 * @author ASUS
 */
public class BookingDetails {
    private int bookingDetailID;
    private int bookingID;
    private int roomID;
    private BigDecimal price;
    private int nights;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;
    private Integer deletedBy;
    private boolean isDeleted;
    private Booking book ;
    private Room room;
    
    public BookingDetails() {
    }

    public BookingDetails(int bookingDetailID,BigDecimal price, int nights, Timestamp createdAt, Timestamp updatedAt, Timestamp deletedAt, Integer deletedBy, boolean isDeleted, Booking bookId, Room room) {
        this.bookingDetailID = bookingDetailID;
        this.price = price;
        this.nights = nights;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.deletedBy = deletedBy;
        this.isDeleted = isDeleted;
        this.book= bookId;
        this.room = room;
    }

 
    
    //hieu them
    public BookingDetails(int bookingDetailID, int bookingID, int roomID, BigDecimal price, int nights, Timestamp createdAt, Timestamp updatedAt, Timestamp deletedAt, Integer deletedBy, boolean isDeleted) {
        this.bookingDetailID = bookingDetailID;
        this.bookingID = bookingID;
        this.roomID = roomID;
        this.price = price;
        this.nights = nights;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.deletedBy = deletedBy;
        this.isDeleted = isDeleted;
    }


    
    

    public Booking getBookId() {
        return book;
    }

    public void setBookId(Booking bookId) {
        this.book = bookId;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
    
    public int getBookingDetailID() {
        return bookingDetailID;
    }

    public void setBookingDetailID(int bookingDetailID) {
        this.bookingDetailID = bookingDetailID;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getNights() {
        return nights;
    }

    public void setNights(int nights) {
        this.nights = nights;
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

    public Integer getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(Integer deletedBy) {
        this.deletedBy = deletedBy;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "BookingDetails{" + "bookingDetailID=" + bookingDetailID + ", bookingID=" + bookingID + ", roomID=" + roomID + ", price=" + price + ", nights=" + nights + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", deletedAt=" + deletedAt + ", deletedBy=" + deletedBy + ", isDeleted=" + isDeleted + '}';
    }
    
}
