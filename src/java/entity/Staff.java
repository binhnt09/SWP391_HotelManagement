/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.sql.Date;

/**
 *
 * @author viet7
 */
public class Staff {

    private String staffID;
    private String firstName, lastName;
    private String position;
    private double salary;
    private Date dateOfBirth;
    private String address, city, country;
    private String phone, email;
    private Date hireDate;

    public Staff() {
    }

    public Staff(String staffID, String phone, String email) {
        this.staffID = staffID;
        this.phone = phone;
        this.email = email;
    }

    public Staff(String staffID, String firstName, String lastName, String position, double salary, Date dateOfBirth, String address, String city, String country, String phone, String email, Date hireDate) {
        this.staffID = staffID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.salary = salary;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.city = city;
        this.country = country;
        this.phone = phone;
        this.email = email;
        this.hireDate = hireDate;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    @Override
    public String toString() {
        return "Staff{" + "staffID=" + staffID + ", firstName=" + firstName + ", lastName=" + lastName + ", position=" + position + ", salary=" + salary + ", dateOfBirth=" + dateOfBirth + ", address=" + address + ", city=" + city + ", country=" + country + ", phone=" + phone + ", email=" + email + ", hireDate=" + hireDate + '}';
    }

    public String toString1() {
        return "phone=" + phone + ", email=" + email;
    }
}
