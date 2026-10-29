package minibank;

import java.io.Serializable;

public abstract class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private final String accountNumber;
    private String ownerName;
    protected long balance;
    private boolean active;
    
    private static long accountCounter = 1;

    public Account(String ownerName, long openingBalance) {
        this.ownerName = ownerName;
        this.balance = openingBalance;
        this.active = true;
        this.accountNumber = String.format("AC%04d", accountCounter++);
    }

    public Account(String ownerName) {
        this(ownerName, 0);
    }

    public abstract void applyInterest();

    public synchronized void deposit(long amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public synchronized void withdraw(long amount) throws InsufficientFundsException {
        if (amount > 0) {
            if (balance >= amount) {
                balance -= amount;
            } else {
                throw new InsufficientFundsException("Insufficient balance for withdrawal of " + amount);
            }
        }
    }

    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
    public long getBalance() { return balance; }
    public boolean isActive() { return active; }

    @Override
    public String toString() {
        return accountNumber + " | " + ownerName + " | " + balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountNumber.equals(account.accountNumber);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(accountNumber);
    }
}