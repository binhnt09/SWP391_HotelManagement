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
public class EmailVerification {

    private int verificationID;
    private String email;
    private String code;
    private String purpose;
    private Timestamp expiredAt;
    private boolean isUsed;
    private Timestamp createdAt;

    public EmailVerification() {
    }

    public EmailVerification(int verificationID, String email, String code, String purpose, Timestamp expiredAt, boolean isUsed, Timestamp createdAt) {
        this.verificationID = verificationID;
        this.email = email;
        this.code = code;
        this.purpose = purpose;
        this.expiredAt = expiredAt;
        this.isUsed = isUsed;
        this.createdAt = createdAt;
    }

    public int getVerificationID() {
        return verificationID;
    }

    public void setVerificationID(int verificationID) {
        this.verificationID = verificationID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Timestamp getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Timestamp expiredAt) {
        this.expiredAt = expiredAt;
    }

    public boolean isIsUsed() {
        return isUsed;
    }

    public void setIsUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "EmailVerification{" + "verificationID=" + verificationID + ", email=" + email + ", code=" + code + ", purpose=" + purpose + ", expiredAt=" + expiredAt + ", isUsed=" + isUsed + ", createdAt=" + createdAt + '}';
    }

}
