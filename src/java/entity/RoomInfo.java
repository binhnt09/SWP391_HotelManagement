/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author viet7
 */
public class RoomInfo {

    private int roomID;
    private String roomNumber;
    private String status;
    private double price;
    private String roomType;
    private int floorNumber;

    private String guestName;
    private Timestamp checkInDate;
    private Timestamp checkOutDate;
    private String bookingStatus;

    public RoomInfo() {
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

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public Timestamp getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Timestamp checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Timestamp getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Timestamp checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getDisplayStatus() {
        if ("Occupied".equals(this.status)
                && "Checked-in".equalsIgnoreCase(this.bookingStatus)
                && this.checkOutDate != null) {

            // Chuyển checkOutDate và currentDate thành LocalDate để bỏ phần giờ
            LocalDate checkout = this.checkOutDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate today = LocalDate.now();

            if (checkout.isBefore(today)) {
                return "Overdue";
            }
        }
        return this.status;
    }

    @Override
    public String toString() {
        return "RoomInfo{" + "roomID=" + roomID + ", roomNumber=" + roomNumber + ", status=" + status + ", price=" + price + ", roomType=" + roomType + ", floorNumber=" + floorNumber + ", guestName=" + guestName + ", checkInDate=" + checkInDate + ", checkOutDate=" + checkOutDate + ", bookingStatus=" + bookingStatus + '}';
    }

}
