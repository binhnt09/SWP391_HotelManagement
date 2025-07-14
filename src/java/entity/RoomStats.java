/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author viet7
 */
public class RoomStats {

    private int total;
    private int availableCount;
    private int occupiedCount;
    private int reservedCount;
    private int checkoutCount;
    private int cleaningCount;
    private int nonAvailableCount;
    private int dueTodayCount;
    private int overdueCount;
    private int waitingGuestCount;

    public RoomStats() {
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(int availableCount) {
        this.availableCount = availableCount;
    }

    public int getOccupiedCount() {
        return occupiedCount;
    }

    public void setOccupiedCount(int occupiedCount) {
        this.occupiedCount = occupiedCount;
    }

    public int getReservedCount() {
        return reservedCount;
    }

    public void setReservedCount(int reservedCount) {
        this.reservedCount = reservedCount;
    }

    public int getCheckoutCount() {
        return checkoutCount;
    }

    public void setCheckoutCount(int checkoutCount) {
        this.checkoutCount = checkoutCount;
    }

    public int getCleaningCount() {
        return cleaningCount;
    }

    public void setCleaningCount(int cleaningCount) {
        this.cleaningCount = cleaningCount;
    }

    public int getNonAvailableCount() {
        return nonAvailableCount;
    }

    public void setNonAvailableCount(int nonAvailableCount) {
        this.nonAvailableCount = nonAvailableCount;
    }

    public int getDueTodayCount() {
        return dueTodayCount;
    }

    public void setDueTodayCount(int dueTodayCount) {
        this.dueTodayCount = dueTodayCount;
    }

    public int getOverdueCount() {
        return overdueCount;
    }

    public void setOverdueCount(int overdueCount) {
        this.overdueCount = overdueCount;
    }

    public int getWaitingGuestCount() {
        return waitingGuestCount;
    }

    public void setWaitingGuestCount(int waitingGuestCount) {
        this.waitingGuestCount = waitingGuestCount;
    }
    
    
}
