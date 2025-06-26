package command;

import model.Bill;
import service.BillService;

/**
 * Lấy danh sách bill
 */
public class ListBillCommand implements Command{
    private BillService billService = BillService.getInstance();

    @Override
    public void execute(String[] args) {
        System.out.printf("%-8s %-10s %-10s %-12s %-10s %s%n",
                "Bill No.", "Type", "Amount", "Due Date", "State", "PROVIDER");

        for (Bill bill : billService.listBills()) {
            System.out.printf("%-8s %-10s %-10d %-12s %-10s %s%n",
                    bill.getId() + ".",
                    bill.getType(),
                    bill.getAmount(),
                    bill.getDueDate().toString(),
                    bill.getState(),
                    bill.getProvider());
        }
    }
}
