/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 *
 * @author viet7
 */
public class CustomerReport {

    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private int totalBookings;
    private BigDecimal totalSpent;
    private Timestamp lastBookingDate;
    private Timestamp registerDate;
    private String tier;

    private BigDecimal totalPaid;

    public CustomerReport() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(int totalBookings) {
        this.totalBookings = totalBookings;
    }

    public BigDecimal getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(BigDecimal totalSpent) {
        this.totalSpent = totalSpent;
    }

    public Timestamp getLastBookingDate() {
        return lastBookingDate;
    }

    public void setLastBookingDate(Timestamp lastBookingDate) {
        this.lastBookingDate = lastBookingDate;
    }

    public Timestamp getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Timestamp registerDate) {
        this.registerDate = registerDate;
    }

    public String getTier() {
        if (totalPaid == null) {
            return "Unranked";
        }
        if (totalPaid.compareTo(new BigDecimal("50000000")) >= 0) {
            return "Platinum";
        }
        if (totalPaid.compareTo(new BigDecimal("20000000")) >= 0) {
            return "Gold";
        }
        if (totalPaid.compareTo(new BigDecimal("5000000")) >= 0) {
            return "Silver";
        }
        return "Bronze";
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public BigDecimal getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(BigDecimal totalPaid) {
        this.totalPaid = totalPaid;
    } 
    
    @Override
    public String toString() {
        return "CustomerReport{" + "userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", totalBookings=" + totalBookings + ", totalSpent=" + totalSpent + ", lastBookingDate=" + lastBookingDate + ", registerDate=" + registerDate + ", tier=" + tier + '}';
    }

}
