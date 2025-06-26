package model;

import enums.PAYMENT;

import java.time.LocalDate;

/**
 * Thông tin hóa đơn
 */
public class Bill {
    private static int idCounter = 1;
    private int id;
    private String type;
    private long amount;
    private LocalDate dueDate;
    private String state;
    private String provider;

    public Bill(String type, long amount, LocalDate dueDate, String provider) {
        this.id = idCounter++;
        this.type = type;
        this.amount = amount;
        this.dueDate = dueDate;
        this.state = PAYMENT.NOT_PAID.name();
        this.provider = provider;
    }

    // Getters, setters, and toString
    public int getId() { return id; }
    public long getAmount() { return amount; }
    public LocalDate getDueDate() { return dueDate; }
    public String getState() { return state; }
    public String getProvider() { return provider; }
    public String getType() { return type; }
    public void setState(String state) { this.state = state; }

    @Override
    public String toString() {
        return String.format("%d. %s %d %s %s %s",
                id, type, amount, dueDate, state, provider);
    }
}
