package minibank;

import java.util.Scanner;

public class MiniBank {

    static class BankInfo {
        private String name;
        private String branch;
        public BankInfo(String name, String branch) {
            this.name = name;
            this.branch = branch;
        }
        public String getName() { return name; }
        public String getBranch() { return branch; }
    }

    enum MenuOption {
        OPEN_ACCOUNT(1),
        DEPOSIT(2),
        WITHDRAW(3),
        TRANSFER(4),
        EXIT(5),
        CHECK_BALANCE(6),
        ANALYTICS(7);

        private final int optionNumber;

        MenuOption(int optionNumber) {
            this.optionNumber = optionNumber;
        }

        public static MenuOption fromInt(int choice) {
            for (MenuOption option : values()) {
                if (option.optionNumber == choice) {
                    return option;
                }
            }
            return null;
        }
    }

    public static void main(String[] args) {
        BankInfo bankInfo = new BankInfo("CHARUSAT National Bank", "Changa Branch");
        System.out.println("==========================================");
        System.out.println("Welcome to " + bankInfo.getName() + " (" + bankInfo.getBranch() + ")");
        System.out.println("==========================================\n");

        BankManager manager = new BankManager();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Exit");
            System.out.println("6. Check Balance");
            System.out.println("7. Bank Analytics (Leaderboard & Frequencies)");
            System.out.print("Enter your choice (1-7): ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); 
                continue;
            }

            int choice = scanner.nextInt();
            MenuOption option = MenuOption.fromInt(choice);

            if (option == null) {
                System.out.println("Invalid choice. Please select between 1 and 7.");
                continue;
            }

            try {
                switch (option) {
                    case OPEN_ACCOUNT: 
                        System.out.print("Enter owner name: ");
                        String name = scanner.next();
                        System.out.print("Enter initial balance: ");
                        long initBal = scanner.nextLong();
                        System.out.print("Enter 1 for Savings, 2 for Current: ");
                        int type = scanner.nextInt();
                        try {
                            String newAcc = manager.openAccount(name, initBal, type);
                            System.out.println("Success! Account No: " + newAcc);
                        } catch (IllegalArgumentException e) {
                            System.out.println("VALIDATION FAILED: " + e.getMessage());
                        }
                        break;
                    case DEPOSIT: 
                        System.out.print("Enter account number: ");
                        String depAcc = scanner.next();
                        System.out.print("Enter deposit amount: ");
                        long depAmt = scanner.nextLong();
                        manager.deposit(depAcc, depAmt);
                        System.out.println("Deposit successful.");
                        break;
                    case WITHDRAW: 
                        System.out.print("Enter account number: ");
                        String drawAcc = scanner.next();
                        System.out.print("Enter withdrawal amount: ");
                        long drawAmt = scanner.nextLong();
                        manager.withdraw(drawAcc, drawAmt);
                        System.out.println("Withdrawal successful.");
                        break;
                    case TRANSFER: 
                        System.out.print("Enter source account number: ");
                        String fromAcc = scanner.next();
                        System.out.print("Enter destination account number: ");
                        String toAcc = scanner.next();
                        System.out.print("Enter transfer amount: ");
                        long transAmt = scanner.nextLong();
                        manager.transfer(fromAcc, toAcc, transAmt);
                        System.out.println("Transfer successful.");
                        break;
                    case EXIT: 
                        manager.saveData(); 
                        System.out.println("Thank you for using MiniBank. Goodbye!"); 
                        running = false;
                        break;
                    case CHECK_BALANCE:
                        System.out.print("Enter account number: ");
                        String balAcc = scanner.next();
                        long balance = manager.getBalance(balAcc);
                        System.out.println("Current Balance for " + balAcc + ": " + balance);
                        break;
                    case ANALYTICS:
                        manager.showAnalytics();
                        break;
                }
            } catch (AccountNotFoundException | InsufficientFundsException e) {
                System.out.println("TRANSACTION FAILED: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
                scanner.nextLine(); 
            }
        }

        scanner.close();
        System.out.println("25CE009");
    }
}