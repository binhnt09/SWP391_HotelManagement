/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.sql.Timestamp;

public class User {

    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String sex;
    private Timestamp birthDay;
    private String address;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;
    private Integer deletedBy;
    private boolean isDeleted;

    private String roleName;
    private boolean isVerifiedEmail;
    private int userRoleId;
    private int levelId;

    public User() {
    }

    public User(int userId, String firstName, String lastName, String email, String phone, String sex, Timestamp birthDay, String address, Timestamp createdAt, Timestamp updatedAt, Timestamp deletedAt, Integer deletedBy, boolean isDeleted, boolean isVerifiedEmail, int userRoleId) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.sex = sex;
        this.birthDay = birthDay;
        this.address = address;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.deletedBy = deletedBy;
        this.isDeleted = isDeleted;
        this.isVerifiedEmail = isVerifiedEmail;
        this.userRoleId = userRoleId;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Timestamp getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Timestamp birthDay) {
        this.birthDay = birthDay;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public boolean isIsVerifiedEmail() {
        return isVerifiedEmail;
    }

    public void setIsVerifiedEmail(boolean isVerifiedEmail) {
        this.isVerifiedEmail = isVerifiedEmail;
    }

    public int getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(int userRoleId) {
        this.userRoleId = userRoleId;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public String getRoleName() {
        return switch (this.userRoleId) {
            case 1 ->
                "Admin System";
            case 2 ->
                "Manager";
            case 3 ->
                "Receptionist";
            case 4 ->
                "Cleaner";
            default ->
                "Customer";
        };
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "User{" + "userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", phone=" + phone + ", sex=" + sex + ", birthDay=" + birthDay + ", address=" + address + ","
                + " createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", deletedAt=" + deletedAt + ", deletedBy=" + deletedBy + ", isDeleted=" + isDeleted + ", roleName=" + roleName + ", isVerifiedEmail=" + isVerifiedEmail + ","
                + " userRoleId=" + userRoleId + ", levelId=" + levelId + '}';
    }

}
