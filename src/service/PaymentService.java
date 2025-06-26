package service;

import enums.PAYMENT;
import enums.STATE;
import logging.LOG;
import model.Bill;
import model.Customer;
import model.Payment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentService {
    private static PaymentService instance = null;
    private List<Payment> payments = new ArrayList<>();
    private Customer customer;
    private BillService billService;

    public PaymentService(Customer customer, BillService billService) {
        this.customer = customer;
        this.billService = billService;
    }

    public static PaymentService getInstance(Customer customer, BillService billService) {
        if (instance == null) {
            instance = new PaymentService(customer, billService);
            return instance;
        }
        return instance;
    }

    public boolean payBill(int billId) {
        Bill bill = billService.getBillById(billId);
        if (bill == null) {
            LOG.info("Sorry! Not found a bill with such id");
            return false;
        }
        if (!bill.getState().equals(PAYMENT.NOT_PAID.name())) {
            LOG.info("Bill is already paid or invalid");
            return false;
        }
        if (!customer.deductFunds(bill.getAmount())) {
            System.out.println("Sorry! Not enough fund to proceed with payment.");
            return false;
        }
        bill.setState("PAID");
        payments.add(new Payment(bill.getAmount(), LocalDate.now(), STATE.PROCESSED.name(), billId));
        LOG.info(String.format("Payment has been completed for Bill with id %d.%n", billId));
        LOG.info(String.format("Your current balance is: %d%n", customer.getBalance()));

        return true;
    }

    public List<Payment> listPayments() {
        return new ArrayList<>(payments);
    }
}
