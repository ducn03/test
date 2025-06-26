package command;

import logging.LOG;
import model.Customer;

/**
 * Nạp tiền
 */
public class CashInCommand implements Command{
    private final Customer customer = Customer.getInstance();
    @Override
    public void execute(String[] args) {
        try {
            long amount = Long.parseLong(args[0]);
            customer.addFunds(amount);
            System.out.printf("Your available balance: %d%n", customer.getBalance());
        } catch (NumberFormatException e) {
            LOG.info("Invalid amount format");
        }
    }
}
