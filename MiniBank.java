package minibank;

import java.util.Scanner;

public class MiniBank {

    record BankInfo(String name, String branch) {}

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

        public int getOptionNumber() {
            return optionNumber;
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
        System.out.println("Welcome to " + bankInfo.name() + " (" + bankInfo.branch() + ")");
        System.out.println("==========================================");

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

            String message = switch (option) {
                case OPEN_ACCOUNT -> "Open Account to be implemented in a later lab.";
                case DEPOSIT -> "Deposit to be implemented in a later lab.";
                case WITHDRAW -> "Withdraw to be implemented in a later lab.";
                case TRANSFER -> "Transfer to be implemented in a later lab.";
                case EXIT -> "Thank you for using MiniBank. Goodbye!";
            };

            System.out.println(message);

            if (option == MenuOption.EXIT) {
                running = false;
            }
        }

        scanner.close();
    }
}