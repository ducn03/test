package test;

import enums.PAYMENT;
import model.Bill;
import model.Customer;
import model.Payment;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import service.BillService;
import service.PaymentService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentServiceTest {
    private BillService billService;
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        billService = new BillService();
        paymentService = PaymentService.getInstance(Customer.getInstance(), billService);
        // Reset Customer balance and payments to avoid interference
        Customer.getInstance().addFunds(-Customer.getInstance().getBalance());
        paymentService.listPayments().clear();
    }

    @Test
    public void testSingletonInstance() {
        PaymentService instance1 = PaymentService.getInstance(Customer.getInstance(), billService);
        PaymentService instance2 = PaymentService.getInstance(Customer.getInstance(), billService);
        assertSame(instance1, instance2, "getInstance() should return the same instance");
    }

    @Test
    public void testPayBillSuccess() {
        Customer.getInstance().addFunds(1000000L);
        boolean result = paymentService.payBill(1);
        assertTrue(result, "Payment should succeed with sufficient funds");
        assertEquals(800000L, Customer.getInstance().getBalance(), "Balance should be reduced after payment");
        Bill bill = billService.getBillById(1);
        assertEquals(PAYMENT.PAID.name(), bill.getState(), "Bill should be marked as PAID");
        List<Payment> payments = paymentService.listPayments();
        assertEquals(1, payments.size(), "Payment should be recorded");
        assertEquals(200000L, payments.get(0).getAmount(), "Payment amount should match bill amount");
        assertEquals(1, payments.get(0).getBillId(), "Payment should be linked to correct bill ID");
    }

    @Test
    public void testPayBillInsufficientFunds() {
        Customer.getInstance().addFunds(100000L);
        boolean result = paymentService.payBill(1);
        assertFalse(result, "Payment should fail with insufficient funds");
        assertEquals(100000L, Customer.getInstance().getBalance(), "Balance should remain unchanged");
        Bill bill = billService.getBillById(1);
        assertEquals(PAYMENT.NOT_PAID.name(), bill.getState(), "Bill should remain NOT_PAID");
        assertTrue(paymentService.listPayments().isEmpty(), "No payment should be recorded");
    }

    @Test
    public void testPayBillInvalidId() {
        Customer.getInstance().addFunds(1000000L);
        boolean result = paymentService.payBill(999);
        assertFalse(result, "Payment should fail for invalid bill ID");
        assertEquals(1000000L, Customer.getInstance().getBalance(), "Balance should remain unchanged");
        assertTrue(paymentService.listPayments().isEmpty(), "No payment should be recorded");
    }

    @Test
    public void testPayBillAlreadyPaid() {
        Customer.getInstance().addFunds(1000000L);
        paymentService.payBill(1);
        boolean result = paymentService.payBill(1);
        assertFalse(result, "Payment should fail for already paid bill");
        assertEquals(800000L, Customer.getInstance().getBalance(), "Balance should not change after failed payment");
        List<Payment> payments = paymentService.listPayments();
        assertEquals(1, payments.size(), "Only one payment should be recorded");
    }

    @Test
    public void testListPaymentsSharedInstance() {
        Customer.getInstance().addFunds(1000000L);
        PaymentService instance1 = PaymentService.getInstance(Customer.getInstance(), billService);
        instance1.payBill(1);
        PaymentService instance2 = PaymentService.getInstance(Customer.getInstance(), billService);
        List<Payment> payments = instance2.listPayments();
        assertEquals(1, payments.size(), "Payments should be shared across instances");
        assertEquals(200000L, payments.get(0).getAmount(), "Payment amount should match");
        instance2.payBill(2);
        assertEquals(2, instance1.listPayments().size(), "Payments should be updated across instances");
    }
}
