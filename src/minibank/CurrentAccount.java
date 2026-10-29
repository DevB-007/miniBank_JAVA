package minibank;

public class CurrentAccount extends Account {
    private long overdraftLimit;

    public CurrentAccount(String ownerName, long openingBalance, long overdraftLimit) {
        super(ownerName, openingBalance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void applyInterest() {
    }

    @Override
    public void withdraw(long amount) throws InsufficientFundsException {
        if (amount > 0) {
            if ((balance + overdraftLimit) >= amount) {
                balance -= amount;
            } else {
                throw new InsufficientFundsException("Overdraft limit exceeded for withdrawal of " + amount);
            }
        }
    }
}