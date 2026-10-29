package minibank;

public class BankManager implements BankOperations {
    private Account[] accounts = new Account[100];
    private int accountCount = 0;

    @Override
    public String openAccount(String ownerName, long initialBalance, int accountType) {
        if (accountCount >= accounts.length) return null;
        Account newAccount = (accountType == 1) ? 
            new SavingsAccount(ownerName, initialBalance, 4.0) : 
            new CurrentAccount(ownerName, initialBalance, 10000);
        accounts[accountCount++] = newAccount;
        return newAccount.getAccountNumber();
    }

    private Account findAccount(String accountNumber) throws AccountNotFoundException {
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].getAccountNumber().equals(accountNumber)) {
                return accounts[i];
            }
        }
        throw new AccountNotFoundException("Error: Account " + accountNumber + " does not exist.");
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