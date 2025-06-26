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

import static org.junit.Assert.*;

public class BillServiceTest {
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
    public void testSampleBillsInitialized() {
        List<Bill> bills = billService.listBills();
        assertEquals(3, bills.size());
        assertEquals(1, bills.get(0).getId());
        assertEquals("ELECTRIC", bills.get(0).getType());
        assertEquals(200000L, bills.get(0).getAmount());
        assertEquals("EVN HCMC", bills.get(0).getProvider());
        assertEquals(2, bills.get(1).getId());
        assertEquals("WATER", bills.get(1).getType());
        assertEquals(175000L, bills.get(1).getAmount());
        assertEquals("SAVACO HCMC", bills.get(1).getProvider());
        assertEquals(3, bills.get(2).getId());
        assertEquals("INTERNET", bills.get(2).getType());
        assertEquals(800000L, bills.get(2).getAmount());
        assertEquals("VNPT", bills.get(2).getProvider());
    }

    @Test
    public void testDeleteBill() {
        billService.deleteBill(1);
        assertEquals(2, billService.listBills().size());
    }

    @Test
    public void testSearchBillsByProvider() {
        List<Bill> result = billService.searchBillsByProvider("VNPT");
        assertEquals(1, result.size());
        assertEquals("VNPT", result.get(0).getProvider());
        assertEquals(3, result.get(0).getId());
    }

    @Test
    public void testGetDueBills() {
        List<Bill> dueBills = billService.getDueBills();
        assertEquals(3, dueBills.size());
        assertEquals(1, dueBills.get(0).getId());
        assertEquals(2, dueBills.get(1).getId());
        assertEquals(3, dueBills.get(2).getId());
    }

    @Test
    public void testGetBillById() {
        Bill bill = billService.getBillById(1);
        assertNotNull(bill);
        assertEquals("ELECTRIC", bill.getType());
        assertEquals(1, bill.getId());
        assertNull(billService.getBillById(999));
    }

    @Test
    public void testPayBillIntegration() {
        Customer.getInstance().addFunds(1000000L);
        assertEquals(String.valueOf(1000000L), Customer.getInstance().getBalance(), "Balance should be updated after adding funds");

        boolean result = paymentService.payBill(1);
        assertTrue("Payment should succeed with sufficient funds", result);
        assertEquals(String.valueOf(800000L), Customer.getInstance().getBalance(), "Balance should be reduced after payment");
        Bill bill = billService.getBillById(1);
        assertEquals(PAYMENT.PAID.name(), bill.getState(), "Bill should be marked as PAID");

        List<Payment> payments = paymentService.listPayments();
        assertEquals(String.valueOf(1), payments.size(), "Payment should be recorded");
        assertEquals(String.valueOf(200000L), payments.get(0).getAmount(), "Payment amount should match bill amount");
    }

    @Test
    public void testPayBillInsufficientFunds() {
        Customer.getInstance().addFunds(100000L);
        boolean result = paymentService.payBill(1);
        assertFalse("Payment should fail with insufficient funds", result);
        assertEquals(String.valueOf(100000L), Customer.getInstance().getBalance(), "Balance should remain unchanged");
        Bill bill = billService.getBillById(1);
        assertEquals(PAYMENT.NOT_PAID.name(), bill.getState(), "Bill should remain NOT_PAID");
    }
}
