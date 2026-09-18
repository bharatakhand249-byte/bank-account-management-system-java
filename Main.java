package bank;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Bank bank = new Bank();

    public static void main(String[] args) {
        bank.loadData();

        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Enter your choice: ");

            try {
                switch (choice) {
                    case 1 -> createSavings();
                    case 2 -> createCurrent();
                    case 3 -> bank.printReports();
                    case 4 -> searchAccount();
                    case 5 -> deposit();
                    case 6 -> withdraw();
                    case 7 -> transfer();
                    case 8 -> showTransactions();
                    case 9 -> closeAccount();
                    case 10 -> bank.saveData();
                    case 11 -> applyInterestInBackground();
                    case 0 -> {
                        bank.saveData();
                        System.out.println("Thank you for using Bank Account Management System.");
                        running = false;
                    }
                    default -> System.out.println("Invalid choice. Please select a menu option.");
                }
            } catch (InsufficientBalanceException | IllegalArgumentException |
                     IllegalStateException e) {
                System.out.println("Operation failed: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n==============================================");
        System.out.println("       BANK ACCOUNT MANAGEMENT SYSTEM");
        System.out.println("==============================================");
        System.out.println("1.  Create Savings Account");
        System.out.println("2.  Create Current Account");
        System.out.println("3.  Display Account Report");
        System.out.println("4.  Search Account");
        System.out.println("5.  Deposit");
        System.out.println("6.  Withdraw");
        System.out.println("7.  Transfer Money");
        System.out.println("8.  View Transaction History");
        System.out.println("9.  Close Account");
        System.out.println("10. Save Data");
        System.out.println("11. Apply Savings Interest (Multithreading Demo)");
        System.out.println("0.  Exit");
        System.out.println("==============================================");
    }

    private static void createSavings() {
        String number = readText("Enter account number: ");
        String name = readText("Enter holder name: ");
        double balance = readDouble("Enter initial deposit: ");
        double rate = readDouble("Enter annual interest rate (%): ");

        bank.createSavingsAccount(number, name, balance, rate);
        System.out.println("Savings account created successfully.");
    }

    private static void createCurrent() {
        String number = readText("Enter account number: ");
        String name = readText("Enter holder name: ");
        double balance = readDouble("Enter initial deposit: ");
        double minimum = readDouble("Enter minimum balance requirement: ");

        bank.createCurrentAccount(number, name, balance, minimum);
        System.out.println("Current account created successfully.");
    }

    private static void searchAccount() {
        String number = readText("Enter account number: ");
        BankAccount account = bank.findAccount(number);

        if (account == null) {
            System.out.println("Account not found.");
        } else {
            account.displayDetails();
        }
    }

    private static void deposit() {
        String number = readText("Enter account number: ");
        double amount = readDouble("Enter deposit amount: ");

        bank.deposit(number, amount);
        System.out.println("Deposit successful.");
    }

    private static void withdraw() throws InsufficientBalanceException {
        String number = readText("Enter account number: ");
        double amount = readDouble("Enter withdrawal amount: ");

        bank.withdraw(number, amount);
        System.out.println("Withdrawal successful.");
    }

    private static void transfer() throws InsufficientBalanceException {
        String source = readText("Enter source account number: ");
        String target = readText("Enter target account number: ");
        double amount = readDouble("Enter transfer amount: ");

        bank.transfer(source, target, amount);
        System.out.println("Transfer successful.");
    }

    private static void showTransactions() {
        String number = readText("Enter account number: ");
        bank.printTransactions(number);
    }

    private static void closeAccount() {
        String number = readText("Enter account number: ");
        bank.closeAccount(number);
        System.out.println("Account closed successfully.");
    }

    private static void applyInterestInBackground() {
        Thread interestThread = new Thread(() -> {
            System.out.println("\nInterest calculation thread started...");
            bank.applyInterestToSavingsAccounts();
            System.out.println("Interest calculation thread completed.");
        }, "InterestThread");

        interestThread.start();

        try {
            interestThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Interest thread was interrupted.");
        }
    }

    private static String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("Input cannot be empty.");
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();

            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();

            try {
                double number = Double.parseDouble(value);

                if (Double.isNaN(number) || Double.isInfinite(number)) {
                    throw new NumberFormatException();
                }

                return number;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
