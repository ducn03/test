package command;

import logging.LOG;
import model.Bill;
import service.BillService;

public class DeleteBillCommand implements Command {
    private BillService billService = BillService.getInstance();

    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            System.out.println("DELETE_BILL requires a bill ID. Usage: DELETE_BILL <bill_id>");
            return;
        }
        try {
            int billId = Integer.parseInt(args[0]);
            Bill existingBill = billService.getBillById(billId);
            if (existingBill == null) {
                LOG.info("Sorry! Not found a bill with such id");
                return;
            }
            billService.deleteBill(billId);
            LOG.info("Bill deleted successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid bill ID format");
        }
    }
}
