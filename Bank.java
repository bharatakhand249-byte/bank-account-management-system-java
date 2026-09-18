package bank;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bank {
    private final List<BankAccount> accounts;
    private final FileStorage storage;
    private final BankReport report;

    public Bank() {
        accounts = new ArrayList<>();
        storage = new FileStorage();
        report = new BankReport();
    }

    public void loadData() {
        try {
            accounts.clear();
            accounts.addAll(storage.loadAccounts());
            System.out.println("Saved data loaded. Accounts: " + accounts.size());
        } catch (IOException e) {
            System.out.println("Could not load data: " + e.getMessage());
        }
    }

    public void saveData() {
        try {
            storage.saveAccounts(accounts);
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Could not save data: " + e.getMessage());
        }
    }

    public SavingsAccount createSavingsAccount(String accountNumber, String holderName,
                                               double initialBalance, double interestRate) {
        ensureUniqueAccount(accountNumber);
        SavingsAccount account = new SavingsAccount(
                accountNumber, holderName, initialBalance, interestRate
        );
        accounts.add(account);
        return account;
    }

    public CurrentAccount createCurrentAccount(String accountNumber, String holderName,
                                               double initialBalance, double minimumBalance) {
        ensureUniqueAccount(accountNumber);
        CurrentAccount account = new CurrentAccount(
                accountNumber, holderName, initialBalance, minimumBalance
        );
        accounts.add(account);
        return account;
    }

    private void ensureUniqueAccount(String accountNumber) {
        if (findAccount(accountNumber) != null) {
            throw new IllegalArgumentException("Account number already exists.");
        }
    }

    public BankAccount findAccount(String accountNumber) {
        if (accountNumber == null) {
            return null;
        }

        for (BankAccount account : accounts) {
            if (account.getAccountNumber().equalsIgnoreCase(accountNumber.trim())) {
                return account;
            }
        }
        return null;
    }

    public List<BankAccount> getAccounts() {
        return new ArrayList<>(accounts);
    }

    public void deposit(String accountNumber, double amount) {
        BankAccount account = requireAccount(accountNumber);
        account.deposit(amount);
    }

    public void withdraw(String accountNumber, double amount)
            throws InsufficientBalanceException {
        BankAccount account = requireAccount(accountNumber);
        account.withdraw(amount);
    }

    public void transfer(String sourceNumber, String targetNumber, double amount)
            throws InsufficientBalanceException {
        if (sourceNumber.equalsIgnoreCase(targetNumber)) {
            throw new IllegalArgumentException("Source and target accounts must be different.");
        }

        BankAccount source = requireAccount(sourceNumber);
        BankAccount target = requireAccount(targetNumber);

        if (!source.isActive() || !target.isActive()) {
            throw new IllegalStateException("Both accounts must be active.");
        }

        // Lock in a consistent order to reduce deadlock risk.
        BankAccount first = source.getAccountNumber().compareToIgnoreCase(target.getAccountNumber()) < 0
                ? source : target;
        BankAccount second = first == source ? target : source;

        synchronized (first) {
            synchronized (second) {
                source.withdraw(amount);
                target.deposit(amount);

                source.addTransaction(new Transaction(
                        TransactionType.TRANSFER_OUT,
                        amount,
                        "Transfer to " + target.getAccountNumber()
                ));

                target.addTransaction(new Transaction(
                        TransactionType.TRANSFER_IN,
                        amount,
                        "Transfer from " + source.getAccountNumber()
                ));
            }
        }
    }

    public void closeAccount(String accountNumber) {
        requireAccount(accountNumber).closeAccount();
    }

    public void printAccount(String accountNumber) {
        BankAccount account = requireAccount(accountNumber);
        System.out.println("\n========== ACCOUNT DETAILS ==========");
        account.displayDetails();
        System.out.println("=====================================");
    }

    public void printTransactions(String accountNumber) {
        BankAccount account = requireAccount(accountNumber);

        System.out.println("\n================ TRANSACTION HISTORY ================");
        System.out.println("Account: " + account.getAccountNumber()
                + " | Holder: " + account.getHolderName());

        if (account.getTransactions().isEmpty()) {
            System.out.println("No transactions available.");
        } else {
            System.out.printf("%-9s %-14s %-10s %-20s %s%n",
                    "ID", "TYPE", "AMOUNT", "DESCRIPTION", "TIME");
            for (Transaction transaction : account.getTransactions()) {
                System.out.println(transaction);
            }
        }
        System.out.println("======================================================");
    }

    public void printReports() {
        report.printSummary(accounts);
        report.printAccountTypeReport(accounts);
        report.printAccounts(accounts);
    }

    public void applyInterestToSavingsAccounts() {
        for (BankAccount account : accounts) {
            if (account instanceof SavingsAccount && account.isActive()) {
                SavingsAccount savings = (SavingsAccount) account;
                double interest = savings.calculateInterest();
                if (interest > 0) {
                    System.out.printf("Interest credited to %s: %.2f%n",
                            savings.getAccountNumber(), interest);
                }
            }
        }
    }

    private BankAccount requireAccount(String accountNumber) {
        BankAccount account = findAccount(accountNumber);
        if (account == null) {
            throw new IllegalArgumentException("Account not found: " + accountNumber);
        }
        return account;
    }
}
