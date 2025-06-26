package command;

import logging.LOG;
import model.Customer;
import service.BillService;
import service.PaymentService;

/**
 * Thanh toán hóa đơn
 */
public class PayCommand implements Command{
    private Customer customer = Customer.getInstance();
    private BillService billService = BillService.getInstance();
    private PaymentService paymentService = PaymentService.getInstance(customer, billService);

    @Override
    public void execute(String[] args) {
        for (String arg : args) {
            try {
                int billId = Integer.parseInt(arg);
                paymentService.payBill(billId);
            } catch (NumberFormatException e) {
                LOG.info("Invalid bill ID format: " + arg);
            }
        }
    }
}
