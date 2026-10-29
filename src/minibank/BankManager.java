package minibank;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class BankManager implements BankOperations {
    private Map<String, Account> accounts = new HashMap<>();
    private static final String DATA_FILE = "bank_data.ser";

    public BankManager() {
        loadData();
    }

    @SuppressWarnings("unchecked")
    private void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            accounts = (Map<String, Account>) ois.readObject();
            System.out.println("[System] Previous bank data loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("[System] No previous data found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("[System] Error loading data: " + e.getMessage());
        }
    }

    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(accounts);
            System.out.println("[System] Bank data saved successfully.");
        } catch (IOException e) {
            System.out.println("[System] Error saving data: " + e.getMessage());
        }
    }

    @Override
    public String openAccount(String ownerName, long initialBalance, int accountType) throws IllegalArgumentException {
        Account newAccount = (accountType == 1) ? 
            new SavingsAccount(ownerName, initialBalance, 4.0) : 
            new CurrentAccount(ownerName, initialBalance, 10000);
            
        ReflectionValidator.validate(newAccount);

        accounts.put(newAccount.getAccountNumber(), newAccount);
        return newAccount.getAccountNumber();
    }

    private Account findAccount(String accountNumber) throws AccountNotFoundException {
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

    @Override
    public long getBalance(String accountNumber) throws AccountNotFoundException {
        Account acc = findAccount(accountNumber);
        return acc.getBalance();
    }
}