package model;

import enums.STATE;

import java.time.LocalDate;

/**
 * Thông tin thanh toán
 */
public class Payment {
    private static int idCounter = 1;
    private int id;
    private long amount;
    private LocalDate paymentDate;
    private String state;
    private int billId;

    public Payment(long amount, LocalDate paymentDate, String state, int billId) {
        this.id = idCounter++;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.state = state;
        this.billId = billId;
    }

    public int getId() { return id; }
    public long getAmount() { return amount; }
    public LocalDate getPaymentDate() { return paymentDate; }
    public String getState() { return state; }
    public int getBillId() { return billId; }
    public void setState(String state) { this.state = state; }

    @Override
    public String toString() {
        return String.format("%d. %d %s %s %d",
                id, amount, paymentDate, state, billId);
    }
}
