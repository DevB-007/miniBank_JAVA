package minibank;

public class TransactionJob implements Runnable {
    private BankManager manager;
    private String accountNumber;
    private boolean isDeposit;
    private long amount;

    public TransactionJob(BankManager manager, String accountNumber, boolean isDeposit, long amount) {
        this.manager = manager;
        this.accountNumber = accountNumber;
        this.isDeposit = isDeposit;
        this.amount = amount;
    }

    @Override
    public void run() {
        try {
            if (isDeposit) {
                manager.deposit(accountNumber, amount);
                System.out.println(Thread.currentThread().getName() + " successfully deposited " + amount);
            } else {
                manager.withdraw(accountNumber, amount);
                System.out.println(Thread.currentThread().getName() + " successfully withdrew " + amount);
            }
        } catch (Exception e) {
            System.out.println(Thread.currentThread().getName() + " failed: " + e.getMessage());
        }
    }
}