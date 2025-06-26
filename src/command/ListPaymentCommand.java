package command;

import model.Customer;
import model.Payment;
import service.BillService;
import service.PaymentService;

/**
 * Lấy danh sách thanh toán
 */
public class ListPaymentCommand implements Command {
    private Customer customer = Customer.getInstance();
    private BillService billService = BillService.getInstance();
    private PaymentService paymentService = PaymentService.getInstance(customer, billService);

    @Override
    public void execute(String[] args) {
        System.out.printf("%-4s %-10s %-15s %-8s %s%n",
                "No.", "Amount", "Payment Date", "State", "Bill Id");

        for (Payment payment : paymentService.listPayments()) {
            System.out.printf("%-4s %-10d %-15s %-8s %s%n",
                    payment.getId() + ".",
                    payment.getAmount(),
                    payment.getPaymentDate().toString(),
                    payment.getState(),
                    payment.getBillId());
        }
    }
}
