package command;

import logging.LOG;
import model.Bill;
import service.BillService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

public class UpdateBillCommand implements Command{
    private final BillService billService = BillService.getInstance();

    @Override
    public void execute(String[] args) {
        if (args.length < 5) {
            System.out.println("UPDATE_BILL requires bill_id, type, amount, due_date, and provider. Usage: UPDATE_BILL <bill_id> <type> <amount> <due_date> <provider>");
            return;
        }
        try {
            int billId = Integer.parseInt(args[0]);
            String type = args[1].toUpperCase();
            long amount = Long.parseLong(args[2]);
            if (amount <= 0) {
                System.out.println("Amount must be positive");
                return;
            }
            LocalDate dueDate = LocalDate.parse(args[3], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String provider = String.join(" ", Arrays.copyOfRange(args, 4, args.length));
            Bill existingBill = billService.getBillById(billId);
            if (existingBill == null) {
                LOG.info("Sorry! Not found a bill with such id");
                return;
            }
            billService.updateBill(billId, type, amount, dueDate, provider);
            LOG.info("Bill updated successfully.");
            System.out.println(String.format("%-8s %-10s %-10s %-10s %s", "Bill No.", "Type", "Amount", "Due Date", "PROVIDER"));
            System.out.println(billService.getBillById(billId));
        } catch (NumberFormatException e) {
            System.out.println("Invalid bill ID or amount format");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use dd/MM/yyyy.");
        }
    }
}
