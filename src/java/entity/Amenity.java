/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Admin
 */
public class Amenity {
    private int amenityId;
    private String name;
    private AmenityCategory category;

    public Amenity() {
    }

    public Amenity(int amenityId, String name, AmenityCategory category) {
        this.amenityId = amenityId;
        this.name = name;
        this.category = category;
    }

    public int getAmenityId() {
        return amenityId;
    }

    public void setAmenityId(int amenityId) {
        this.amenityId = amenityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AmenityCategory getCategory() {
        return category;
    }

    public void setCategory(AmenityCategory category) {
        this.category = category;
    }

    
    
}
