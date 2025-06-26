package service;

import logging.LOG;
import model.ScheduledPayment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ScheduledPaymentService {
    private List<ScheduledPayment> scheduledPayments = new ArrayList<>();
    private final PaymentService paymentService;
    private final BillService billService;

    public ScheduledPaymentService(PaymentService paymentService, BillService billService) {
        this.paymentService = paymentService;
        this.billService = billService;
    }

    public void schedulePayment(int billId, LocalDate scheduledDate) {
        if (billService.getBillById(billId) == null) {
            LOG.info("Sorry! Not found a bill with such id");
        }
        scheduledPayments.add(new ScheduledPayment(billId, scheduledDate));
        LOG.info(String.format("Payment for bill id %d is scheduled on %s%n", billId, scheduledDate));
    }

    public void processScheduledPayments() {
        LocalDate today = LocalDate.now();
        for (ScheduledPayment sp : scheduledPayments) {
            if (sp.getScheduledDate().isAfter(today)) continue;
            paymentService.payBill(sp.getBillId());
        }
        scheduledPayments.removeIf(sp -> !sp.getScheduledDate().isAfter(today));
    }
}
