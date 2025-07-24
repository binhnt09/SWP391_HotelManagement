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
public class Notifications {

    private int NotificationId;
    private User userId;
    private String Message;
    private String Type;
    private boolean IsRead;
    private Timestamp createdAt;
    private Timestamp deletedAt;

    public Notifications() {
    }

    public Notifications(int NotificationId, User userId, String Message, String Type, boolean IsRead, Timestamp createdAt, Timestamp deletedAt) {
        this.NotificationId = NotificationId;
        this.userId = userId;
        this.Message = Message;
        this.Type = Type;
        this.IsRead = IsRead;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }

    public int getNotificationId() {
        return NotificationId;
    }

    public void setNotificationId(int NotificationId) {
        this.NotificationId = NotificationId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public boolean isIsRead() {
        return IsRead;
    }

    public void setIsRead(boolean IsRead) {
        this.IsRead = IsRead;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }
    
    
    

}
