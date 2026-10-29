package minibank;

public interface BankOperations {
    String openAccount(String ownerName, long initialBalance, int accountType);
    boolean deposit(String accountNumber, long amount);
    boolean withdraw(String accountNumber, long amount);
    boolean transfer(String fromAccount, String toAccount, long amount);
}