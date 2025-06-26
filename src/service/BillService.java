package service;

import enums.BILL_TYPE;
import enums.PAYMENT;
import model.Bill;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BillService {
    private static BillService instance = null;
    private List<Bill> bills = new ArrayList<>();

    public BillService() {
        initializeSampleBills();
    }

    public static BillService getInstance(){
        if (instance == null){
            instance = new BillService();
            return instance;
        }
        return instance;
    }

    /**
     * Khởi tạo dữ liệu mẫu
     */
    private void initializeSampleBills() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        bills.add(new Bill(BILL_TYPE.ELECTRIC.name(), 200000L, LocalDate.parse("25/10/2025", formatter), "EVN HCMC"));
        bills.add(new Bill(BILL_TYPE.WATER.name(), 175000L, LocalDate.parse("30/10/2025", formatter), "SAVACO HCMC"));
        bills.add(new Bill(BILL_TYPE.INTERNET.name(), 800000L, LocalDate.parse("30/11/2025", formatter), "VNPT"));
    }

    public void addBill(String type, long amount, LocalDate dueDate, String provider) {
        bills.add(new Bill(type, amount, dueDate, provider));
    }

    public void deleteBill(int id) {
        for (int i = 0; i < bills.size(); i++) {
            if (bills.get(i).getId() == id) {
                bills.remove(i);
                break;
            }
        }
    }

    public void updateBill(int id, String type, long amount, LocalDate dueDate, String provider) {
        for (Bill bill : bills) {
            if (bill.getId() == id) {
                bill.setState(PAYMENT.NOT_PAID.name());
                // Thêm các field khác nếu cần
                break;
            }
        }
    }

    public List<Bill> listBills() {
        return new ArrayList<>(bills);
    }

    public List<Bill> searchBillsByProvider(String provider) {
        List<Bill> result = new ArrayList<>();
        for (Bill bill : bills) {
            if (bill.getProvider().equalsIgnoreCase(provider)) {
                result.add(bill);
            }
        }
        return result;
    }

    public List<Bill> getDueBills() {
        LocalDate today = LocalDate.now();
        List<Bill> result = new ArrayList<>();
        for (Bill bill : bills) {
            if (!bill.getDueDate().isBefore(today) && bill.getState().equals(PAYMENT.NOT_PAID.name())) {
                result.add(bill);
            }
        }
        // Sắp xếp bởi ngày đáo hạn
        result.sort(new Comparator<Bill>() {
            @Override
            public int compare(Bill b1, Bill b2) {
                return b1.getDueDate().compareTo(b2.getDueDate());
            }
        });
        return result;
    }

    public Bill getBillById(int id) {
        for (Bill bill : bills) {
            if (bill.getId() == id) {
                return bill;
            }
        }
        return null;
    }

}
