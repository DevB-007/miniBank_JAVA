package minibank;

public interface BankOperations {
    String openAccount(String ownerName, long initialBalance, int accountType) throws IllegalArgumentException;
    void deposit(String accountNumber, long amount) throws AccountNotFoundException;
    void withdraw(String accountNumber, long amount) throws AccountNotFoundException, InsufficientFundsException;
    void transfer(String fromAccount, String toAccount, long amount) throws AccountNotFoundException, InsufficientFundsException;
    long getBalance(String accountNumber) throws AccountNotFoundException;
    void showAnalytics();
}