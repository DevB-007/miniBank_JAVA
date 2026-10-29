package minibank;

public class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(String ownerName, long openingBalance, double interestRate) {
        super(ownerName, openingBalance);
        this.interestRate = interestRate;
    }

    @Override
    public void applyInterest() {
        long interest = (long) (balance * (interestRate / 100));
        deposit(interest);
    }
}