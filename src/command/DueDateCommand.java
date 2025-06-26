package command;

import model.Bill;
import service.BillService;

/**
 * In bill đáo hạn
 */
public class DueDateCommand implements Command{
    private BillService billService = BillService.getInstance();

    @Override
    public void execute(String[] args) {
        System.out.println("Bill No. Type Amount Due Date State PROVIDER");
        for (Bill bill : billService.getDueBills()) {
            System.out.println(bill);
        }
    }
}
