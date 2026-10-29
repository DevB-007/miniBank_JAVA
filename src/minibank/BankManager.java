package minibank;

public class BankManager implements BankOperations {
    private Account[] accounts = new Account[100];
    private int accountCount = 0;

    @Override
    public String openAccount(String ownerName, long initialBalance, int accountType) {
        if (accountCount >= accounts.length) {
            return null;
        }
        Account newAccount;
        if (accountType == 1) {
            newAccount = new SavingsAccount(ownerName, initialBalance, 4.0);
        } else {
            newAccount = new CurrentAccount(ownerName, initialBalance, 10000);
        }
        accounts[accountCount++] = newAccount;
        return newAccount.getAccountNumber();
    }

    private Account findAccount(String accountNumber) {
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].getAccountNumber().equals(accountNumber)) {
                return accounts[i];
            }
        }
        return null;
    }

    @Override
    public boolean deposit(String accountNumber, long amount) {
        Account acc = findAccount(accountNumber);
        if (acc != null) {
            acc.deposit(amount);
            return true;
        }
        return false;
    }

    @Override
    public boolean withdraw(String accountNumber, long amount) {
        Account acc = findAccount(accountNumber);
        if (acc != null) {
            return acc.withdraw(amount);
        }
        return false;
    }

    @Override
    public boolean transfer(String fromAccount, String toAccount, long amount) {
        Account from = findAccount(fromAccount);
        Account to = findAccount(toAccount);
        if (from != null && to != null && from.withdraw(amount)) {
            to.deposit(amount);
            return true;
        }
        return false;
    }
}