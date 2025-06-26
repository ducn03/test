package command;

import logging.LOG;
import model.Bill;
import service.BillService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

public class CreateBillCommand implements Command{
    private final BillService billService = BillService.getInstance();
    @Override
    public void execute(String[] args) {
        if (args.length < 4) {
            System.out.println("CREATE_BILL requires type, amount, due_date, and provider. Usage: CREATE_BILL <type> <amount> <due_date> <provider>");
            return;
        }
        try {
            String type = args[0].toUpperCase();
            long amount = Long.parseLong(args[1]);
            if (amount <= 0) {
                System.out.println("Amount must be positive");
                return;
            }
            LocalDate dueDate = LocalDate.parse(args[2], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String provider = String.join(" ", Arrays.copyOfRange(args, 3, args.length));
            billService.addBill(type, amount, dueDate, provider);
            LOG.info("Bill created successfully.");
            System.out.println(String.format("%-8s %-10s %-10s %-10s %s", "Bill No.", "Type", "Amount", "Due Date", "PROVIDER"));
            for (Bill bill : billService.listBills()) {
                if (bill.getType().equals(type) && bill.getAmount() == amount && bill.getDueDate().equals(dueDate) && bill.getProvider().equals(provider)) {
                    System.out.println(bill);
                    break;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount format. Please enter a valid number.");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use dd/MM/yyyy.");
        }
    }
}
