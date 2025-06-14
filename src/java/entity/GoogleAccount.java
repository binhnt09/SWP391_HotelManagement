/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author ASUS
 */
public class GoogleAccount {
    private String id;
    private String email;
    private String name;
    private String firstName;
    private String givenName;
    private String familyName;
    private String picture;
    private boolean verifiedEmail;

    public GoogleAccount() {
    }

    public GoogleAccount(String id, String email, String name, String firstName, String givenName, String familyName, String picture, boolean verifiedEmail) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.firstName = firstName;
        this.givenName = givenName;
        this.familyName = familyName;
        this.picture = picture;
        this.verifiedEmail = verifiedEmail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public boolean isVerifiedEmail() {
        return verifiedEmail;
    }

    public void setVerifiedEmail(boolean verifiedEmail) {
        this.verifiedEmail = verifiedEmail;
    }

    @Override
    public String toString() {
        return "GoogleAccount{" + "id=" + id + ", email=" + email + ", name=" + name + ", firstName=" + firstName + ", givenName=" + givenName + ", familyName=" + familyName + ", picture=" + picture + ", verifiedEmail=" + verifiedEmail + '}';
    }

}
