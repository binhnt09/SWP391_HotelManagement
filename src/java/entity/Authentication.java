/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.sql.Timestamp;

/**
 *
 * @author ASUS
 */
public class Authentication {

    private int authenticationID;
    private String userKey;
    private String password;
    private String authType;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;
    private Integer deletedBy;
    private boolean isDeleted;
    private User user;

    public Authentication() {
    }

    public Authentication(int authenticationID, String userKey, String password, String authType, Timestamp createdAt, Timestamp updatedAt, Timestamp deletedAt, Integer deletedBy, boolean isDeleted, User user) {
        this.authenticationID = authenticationID;
        this.userKey = userKey;
        this.password = password;
        this.authType = authType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.deletedBy = deletedBy;
        this.isDeleted = isDeleted;
        this.user = user;
    }

    public Authentication(User user) {
        this.user = user;
    }

    public int getAuthenticationID() {
        return authenticationID;
    }

    public void setAuthenticationID(int authenticationID) {
        this.authenticationID = authenticationID;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Authentication{" + "authenticationID=" + authenticationID + ", userKey=" + userKey + ", password=" + password + ", authType=" + authType + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", deletedAt=" + deletedAt + ", deletedBy=" + deletedBy + ", isDeleted=" + isDeleted + ", user=" + user + '}';
    }

}
