package minibank;

import java.io.*;
import java.util.*;

public class BankManager implements BankOperations {
    private Map<String, Account> accounts = new HashMap<>();
    private static final String DATA_FILE = "bank_data.ser";

    private transient Map<String, Integer> transactionCounts;
    private transient Map<String, TransactionStack<String>> accountHistory;

    public BankManager() {
        initTransientFields();
        loadData();
    }

    private void initTransientFields() {
        transactionCounts = new HashMap<>();
        transactionCounts.put("OPEN_ACCOUNT", 0);
        transactionCounts.put("DEPOSIT", 0);
        transactionCounts.put("WITHDRAW", 0);
        transactionCounts.put("TRANSFER", 0);
        accountHistory = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    private void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            accounts = (Map<String, Account>) ois.readObject();
            System.out.println("[System] Previous bank data loaded successfully.");
            for (String accNum : accounts.keySet()) {
                accountHistory.put(accNum, new TransactionStack<>());
                accountHistory.get(accNum).push("Session Start: Loaded from file.");
            }
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
        transactionCounts.put("OPEN_ACCOUNT", transactionCounts.get("OPEN_ACCOUNT") + 1);
        
        accountHistory.put(newAccount.getAccountNumber(), new TransactionStack<>());
        accountHistory.get(newAccount.getAccountNumber()).push("Account Opened with " + initialBalance);
        
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
        transactionCounts.put("DEPOSIT", transactionCounts.get("DEPOSIT") + 1);
        accountHistory.get(accountNumber).push("Deposited: +" + amount);
    }

    @Override
    public void withdraw(String accountNumber, long amount) throws AccountNotFoundException, InsufficientFundsException {
        Account acc = findAccount(accountNumber);
        acc.withdraw(amount);
        transactionCounts.put("WITHDRAW", transactionCounts.get("WITHDRAW") + 1);
        accountHistory.get(accountNumber).push("Withdrew: -" + amount);
    }

    @Override
    public void transfer(String fromAccount, String toAccount, long amount) throws AccountNotFoundException, InsufficientFundsException {
        Account from = findAccount(fromAccount);
        Account to = findAccount(toAccount);
        
        from.withdraw(amount);
        to.deposit(amount);
        transactionCounts.put("TRANSFER", transactionCounts.get("TRANSFER") + 1);
        
        accountHistory.get(fromAccount).push("Transferred Out: -" + amount + " to " + toAccount);
        accountHistory.get(toAccount).push("Transferred In: +" + amount + " from " + fromAccount);
    }

    @Override
    public long getBalance(String accountNumber) throws AccountNotFoundException {
        Account acc = findAccount(accountNumber);
        return acc.getBalance();
    }

    @Override
    public List<String> getHistory(String accountNumber) throws AccountNotFoundException {
        findAccount(accountNumber);
        return accountHistory.get(accountNumber).getHistory();
    }

    @Override
    public void showStatistics() {
        AccountStats<Account> stats = new AccountStats<>(accounts.values());
        System.out.println("\n--- Global Bank Statistics ---");
        System.out.println("Total Bank Assets: " + stats.getTotalAssets());
        System.out.printf("Average Account Balance: %.2f\n", stats.getAverageBalance());
        System.out.println("------------------------------");
    }

    @Override
    public void showAnalytics() {
        Set<String> uniqueCustomers = new HashSet<>();
        for (Account acc : accounts.values()) uniqueCustomers.add(acc.getOwnerName());

        List<Account> leaderboard = new ArrayList<>(accounts.values());
        leaderboard.sort((a, b) -> Long.compare(b.getBalance(), a.getBalance()));

        System.out.println("\n--- Bank Analytics ---");
        System.out.println("Total Unique Customers: " + uniqueCustomers.size());
        
        System.out.println("\nSession Transaction Frequency:");
        for (Map.Entry<String, Integer> entry : transactionCounts.entrySet()) {
            System.out.println("- " + entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("\nTop Accounts Leaderboard:");
        int limit = Math.min(3, leaderboard.size());
        for (int i = 0; i < limit; i++) {
            Account acc = leaderboard.get(i);
            System.out.println((i + 1) + ". " + acc.getOwnerName() + " (" + acc.getAccountNumber() + ") - Balance: " + acc.getBalance());
        }
    }
}