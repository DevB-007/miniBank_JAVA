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
        EXIT(5);

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
            System.out.print("Enter your choice (1-5): ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); 
                continue;
            }

            int choice = scanner.nextInt();
            MenuOption option = MenuOption.fromInt(choice);

            if (option == null) {
                System.out.println("Invalid choice. Please select between 1 and 5.");
                continue;
            }

            switch (option) {
                case OPEN_ACCOUNT: 
                    System.out.print("Enter owner name (no spaces): ");
                    String name = scanner.next();
                    System.out.print("Enter initial balance: ");
                    long initBal = scanner.nextLong();
                    System.out.print("Enter 1 for Savings, 2 for Current: ");
                    int type = scanner.nextInt();
                    String newAcc = manager.openAccount(name, initBal, type);
                    if (newAcc != null) {
                        System.out.println("Success! Account No: " + newAcc);
                    } else {
                        System.out.println("Failed to open account.");
                    }
                    break;
                case DEPOSIT: 
                    System.out.print("Enter account number: ");
                    String depAcc = scanner.next();
                    System.out.print("Enter deposit amount: ");
                    long depAmt = scanner.nextLong();
                    if (manager.deposit(depAcc, depAmt)) {
                        System.out.println("Deposit successful.");
                    } else {
                        System.out.println("Deposit failed. Check account number.");
                    }
                    break;
                case WITHDRAW: 
                    System.out.print("Enter account number: ");
                    String drawAcc = scanner.next();
                    System.out.print("Enter withdrawal amount: ");
                    long drawAmt = scanner.nextLong();
                    if (manager.withdraw(drawAcc, drawAmt)) {
                        System.out.println("Withdrawal successful.");
                    } else {
                        System.out.println("Withdrawal failed. Check balance or account number.");
                    }
                    break;
                case TRANSFER: 
                    System.out.print("Enter source account number: ");
                    String fromAcc = scanner.next();
                    System.out.print("Enter destination account number: ");
                    String toAcc = scanner.next();
                    System.out.print("Enter transfer amount: ");
                    long transAmt = scanner.nextLong();
                    if (manager.transfer(fromAcc, toAcc, transAmt)) {
                        System.out.println("Transfer successful.");
                    } else {
                        System.out.println("Transfer failed.");
                    }
                    break;
                case EXIT: 
                    System.out.println("Thank you for using MiniBank. Goodbye!"); 
                    running = false;
                    break;
            }
        }

        scanner.close();
        System.out.println("25CE009");
    }
}