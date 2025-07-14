/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 *
 * @author ASUS
 */
public class InvoiceServiceDetail {

    private int id;
    private int invoiceId;
    private String serviceName;
    private BigDecimal price;
    private int quantity;
    private BigDecimal priceAtUse;
    private Timestamp usedAt;

    public InvoiceServiceDetail() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPriceAtUse() {
        return priceAtUse;
    }

    public void setPriceAtUse(BigDecimal priceAtUse) {
        this.priceAtUse = priceAtUse;
    }

    public Timestamp getUsedAt() {
        return usedAt;
    }

    public void setUsedAt(Timestamp usedAt) {
        this.usedAt = usedAt;
    }

    @Override
    public String toString() {
        return "InvoiceServiceDetail{" + "id=" + id + ", invoiceId=" + invoiceId + ", serviceName=" + serviceName
                + ", price=" + price + ", quantity=" + quantity + ", priceAtUse=" + priceAtUse + ", usedAt=" + usedAt + '}';
    }

}
