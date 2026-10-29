package minibank;

import java.util.HashMap;
import java.util.Map;

public class BankManager implements BankOperations {
    // Upgraded from a fixed array to a dynamic HashMap
    private Map<String, Account> accounts = new HashMap<>();

    @Override
    public String openAccount(String ownerName, long initialBalance, int accountType) {
        Account newAccount = (accountType == 1) ? 
            new SavingsAccount(ownerName, initialBalance, 4.0) : 
            new CurrentAccount(ownerName, initialBalance, 10000);
            
        // Store the account in the map using the account number as the unique key
        accounts.put(newAccount.getAccountNumber(), newAccount);
        return newAccount.getAccountNumber();
    }

    private Account findAccount(String accountNumber) throws AccountNotFoundException {
        // Instant lookup instead of looping through an array
        Account acc = accounts.get(accountNumber);
        if (acc == null) {
            throw new AccountNotFoundException("Error: Account " + accountNumber + " does not exist.");
        }
        return acc;
    }

    @Override
    public void deposit(String accountNumber, long amount) throws AccountNotFoundException {
        Account acc = findAccount(accountNumber);
        acc.deposit(amount);
    }

    @Override
    public void withdraw(String accountNumber, long amount) throws AccountNotFoundException, InsufficientFundsException {
        Account acc = findAccount(accountNumber);
        acc.withdraw(amount);
    }

    @Override
    public void transfer(String fromAccount, String toAccount, long amount) throws AccountNotFoundException, InsufficientFundsException {
        Account from = findAccount(fromAccount);
        Account to = findAccount(toAccount);
        
        from.withdraw(amount);
        to.deposit(amount);
    }
}