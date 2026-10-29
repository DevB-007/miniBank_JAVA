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

        System.out.println("--- Practical 5 Tests ---");
        Account[] accounts = new Account[2];
        accounts[0] = new SavingsAccount("Riya", 5000, 4.0);
        accounts[1] = new CurrentAccount("Dev", 2000, 10000);

        accounts[0].applyInterest();
        accounts[1].withdraw(5000); 

        for (Account acc : accounts) {
            System.out.println(acc.toString() + " | Type: " + acc.getClass().getSimpleName());
        }

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

            String message = "";
            switch (option) {
                case OPEN_ACCOUNT: 
                    message = "Open Account to be implemented in a later lab."; 
                    break;
                case DEPOSIT: 
                    message = "Deposit to be implemented in a later lab."; 
                    break;
                case WITHDRAW: 
                    message = "Withdraw to be implemented in a later lab."; 
                    break;
                case TRANSFER: 
                    message = "Transfer to be implemented in a later lab."; 
                    break;
                case EXIT: 
                    message = "Thank you for using MiniBank. Goodbye!"; 
                    break;
            }

            System.out.println(message);

            if (option == MenuOption.EXIT) {
                running = false;
            }
        }

        scanner.close();
        System.out.println("25CE009");
    }
}