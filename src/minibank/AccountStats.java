package minibank;

import java.util.Collection;

public class AccountStats<T extends Account> {
    private Collection<T> accounts;

    public AccountStats(Collection<T> accounts) {
        this.accounts = accounts;
    }

    public double getAverageBalance() {
        if (accounts.isEmpty()) return 0.0;
        long total = getTotalAssets();
        return (double) total / accounts.size();
    }

    public long getTotalAssets() {
        long total = 0;
        for (T acc : accounts) {
            total += acc.getBalance();
        }
        return total;
    }
}