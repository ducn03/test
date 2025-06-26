package model;

public class Customer {
    private static Customer instance = null;
    private long balance;

    private Customer() {
        this.balance = 0L;
    }

    public static Customer getInstance() {
        if (instance == null) {
            instance = new Customer();
        }
        return instance;
    }

    public long getBalance() { return balance; }
    public void addFunds(long amount) { this.balance += amount; }
    public boolean deductFunds(long amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }
}
