/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class Invoice {

    private int invoiceId;
    private int paymentId;
    private int bookingId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;

    private String voucherCode;
    private double discountAmount;

    private String roomNumber;
    private double roomPrice;
    private int nights;
    private BigDecimal totalRoomPrice;

    private Timestamp issueDate;
    private String note;
    private String pdfUrl;
    private String status;

    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;
    private Integer deletedBy;
    private boolean isDeleted;

    private Payment payment;
    private List<InvoiceServiceDetail> listService;

    public Invoice() {
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(double roomPrice) {
        this.roomPrice = roomPrice;
    }

    public int getNights() {
        return nights;
    }

    public void setNights(int nights) {
        this.nights = nights;
    }

    public BigDecimal getTotalRoomPrice() {
        return totalRoomPrice;
    }

    public void setTotalRoomPrice(BigDecimal totalRoomPrice) {
        this.totalRoomPrice = totalRoomPrice;
    }

    public Timestamp getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Timestamp issueDate) {
        this.issueDate = issueDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public List<InvoiceServiceDetail> getListService() {
        return listService;
    }

    public void setListService(List<InvoiceServiceDetail> listService) {
        this.listService = listService;
    }

    @Override
    public String toString() {
        return "Invoice{" + "invoiceId=" + invoiceId + ", paymentId=" + paymentId + ", bookingId=" + bookingId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", phone=" + phone + ", address=" + address + ","
                + " voucherCode=" + voucherCode + ", discountAmount=" + discountAmount + ", roomNumber=" + roomNumber + ", roomPrice=" + roomPrice + ", nights=" + nights + ", totalRoomPrice=" + totalRoomPrice + ", issueDate=" + issueDate + ", "
                + "note=" + note + ", pdfUrl=" + pdfUrl + ", status=" + status + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", deletedAt=" + deletedAt + ", deletedBy=" + deletedBy + ", isDeleted=" + isDeleted + ", payment=" + payment + ", listService=" + listService + '}';
    }

}
