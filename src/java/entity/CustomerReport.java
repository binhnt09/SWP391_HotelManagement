/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.math.BigDecimal;

/**
 *
 * @author viet7
 */
public class CustomerReport {

    private int userId;
    private String firstName;
    private String lastName;
    private int totalBookings;
    private BigDecimal totalSpent;

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

    @Override
    public String toString() {
        return "CustomerReport{" + "userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", totalBookings=" + totalBookings + ", totalSpent=" + totalSpent + '}';
    }
    
    
    
}
