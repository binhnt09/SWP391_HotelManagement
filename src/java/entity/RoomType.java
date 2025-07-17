/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.sql.Timestamp;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Admin
 */
public class RoomType {
    private int roomTypeID;
    private String typeName;
    private String description;
    private String imageUrl;
    private int numberPeople;
    private String amenity;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;
    private int deletedBy;
    private boolean isDeleted;

    private List<Amenity> amenities;
    public RoomType() {
    }

    public RoomType(int roomTypeID, String typeName, String description, String imageUrl, Timestamp createdAt, Timestamp updatedAt, Timestamp deletedAt, int deletedBy, boolean isDeleted) {
        this.roomTypeID = roomTypeID;
        this.typeName = typeName;
        this.description = description;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.deletedBy = deletedBy;
        this.isDeleted = isDeleted;
    }

    public RoomType(int roomTypeID, String typeName, String description, String imageUrl, int numberPeople, String amenity, Timestamp createdAt, Timestamp updatedAt, Timestamp deletedAt, int deletedBy, boolean isDeleted) {
        this.roomTypeID = roomTypeID;
        this.typeName = typeName;
        this.description = description;
        this.imageUrl = imageUrl;
        this.numberPeople = numberPeople;
        this.amenity = amenity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.deletedBy = deletedBy;
        this.isDeleted = isDeleted;
    }
    public RoomType(int roomTypeID, String typeName, String description, String imageUrl, int numberPeople, String amenity) {
        this.roomTypeID = roomTypeID;
        this.typeName = typeName;
        this.description = description;
        this.imageUrl = imageUrl;
        this.numberPeople = numberPeople;
        this.amenity = amenity;
    }

    public int getNumberPeople() {
        return numberPeople;
    }
    

    public void setNumberPeople(int numberPeople) {
        this.numberPeople = numberPeople;
    }

    public String getAmenity() {
        return amenity;
    }

    public void setAmenity(String amenity) {
        this.amenity = amenity;
    }

    
    public int getRoomTypeID() {
        return roomTypeID;
    }

    public void setRoomTypeID(int roomTypeID) {
        this.roomTypeID = roomTypeID;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public List<Amenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<Amenity> amenities) {
        this.amenities = amenities;
    }
    
    
}
