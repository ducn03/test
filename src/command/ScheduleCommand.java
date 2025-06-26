package command;

import logging.LOG;
import model.Customer;
import service.BillService;
import service.PaymentService;
import service.ScheduledPaymentService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ScheduleCommand implements Command{
    private Customer customer = Customer.getInstance();
    private BillService billService = BillService.getInstance();
    private PaymentService paymentService = PaymentService.getInstance(customer, billService);
    private ScheduledPaymentService scheduledPaymentService = new ScheduledPaymentService(paymentService, billService);

    @Override
    public void execute(String[] args) {
        try {
            int billId = Integer.parseInt(args[0]);
            LocalDate scheduledDate = LocalDate.parse(args[1], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            scheduledPaymentService.schedulePayment(billId, scheduledDate);
        } catch (Exception e) {
            LOG.info("Invalid input format");
        }
    }
}
