/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.List;

/**
 *
 * @author ASUS
 */
public class Invoices {

    private User user;
    private Booking booking;
    private BookingDetails bookingDetails;
    private Payment payment;
    private Room rooms;
    private List<BookingServices> bookingServices;

    public Invoices() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public BookingDetails getBookingDetails() {
        return bookingDetails;
    }

    public void setBookingDetails(BookingDetails bookingDetails) {
        this.bookingDetails = bookingDetails;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Room getRooms() {
        return rooms;
    }

    public void setRooms(Room rooms) {
        this.rooms = rooms;
    }

    public List<BookingServices> getBookingServices() {
        return bookingServices;
    }

    public void setBookingServices(List<BookingServices> bookingServices) {
        this.bookingServices = bookingServices;
    }

    @Override
    public String toString() {
        return "Invoice{" + "user=" + user + ", booking=" + booking + ", bookingDetails=" + bookingDetails + ", payment=" + payment + ", rooms=" + rooms + ", bookingServices=" + bookingServices + '}';
    }

}
