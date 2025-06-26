package command;

import logging.LOG;
import model.Bill;
import service.BillService;

/**
 * Tìm kiếm hóa đơn theo nhà cung cấp
 */
public class SearchBillByProviderCommand implements Command{
    private BillService billService = BillService.getInstance();

    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            LOG.info("Provider name is required");
            return;
        }
        System.out.println("Bill No. Type Amount Due Date State PROVIDER");
        for (Bill bill : billService.searchBillsByProvider(args[0])) {
            System.out.println(bill);
        }
    }
}
