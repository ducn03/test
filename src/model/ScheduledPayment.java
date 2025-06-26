package model;

import java.time.LocalDate;

public class ScheduledPayment {
    private int billId;
    private LocalDate scheduledDate;

    public ScheduledPayment(int billId, LocalDate scheduledDate) {
        this.billId = billId;
        this.scheduledDate = scheduledDate;
    }

    public int getBillId() { return billId; }
    public LocalDate getScheduledDate() { return scheduledDate; }
}
