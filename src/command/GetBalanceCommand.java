package command;

import logging.LOG;
import model.Customer;

public class GetBalanceCommand implements Command {
    private final Customer customer = Customer.getInstance();
    @Override
    public void execute(String[] args) {
        try {
            System.out.printf("Your available balance: %d%n", customer.getBalance());
        } catch (NumberFormatException e) {
            LOG.info("Invalid amount format");
        }
    }
}
