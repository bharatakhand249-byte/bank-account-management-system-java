package bank;

import java.util.ArrayList;
import java.util.List;

public abstract class BankAccount implements AccountOperations {
    private final String accountNumber;
    private final String holderName;
    private double balance;
    private boolean active;
    private final List<Transaction> transactions;

    protected BankAccount(String accountNumber, String holderName, double initialBalance) {
        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException("Account number cannot be empty.");
        }
        if (holderName == null || holderName.isBlank()) {
            throw new IllegalArgumentException("Account holder name cannot be empty.");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative.");
        }

        this.accountNumber = accountNumber.trim();
        this.holderName = holderName.trim();
        this.balance = initialBalance;
        this.active = true;
        this.transactions = new ArrayList<>();

        if (initialBalance > 0) {
            addTransaction(new Transaction(
                    TransactionType.DEPOSIT,
                    initialBalance,
                    "Initial deposit"
            ));
        }
    }

    @Override
    public synchronized void deposit(double amount) {
        validateActive();
        validatePositiveAmount(amount);
        balance += amount;
        addTransaction(new Transaction(TransactionType.DEPOSIT, amount, "Cash deposit"));
    }

    @Override
    public synchronized void withdraw(double amount) throws InsufficientBalanceException {
        validateActive();
        validatePositiveAmount(amount);

        if (amount > balance) {
            throw new InsufficientBalanceException(
                    "Insufficient balance. Available balance: " + String.format("%.2f", balance)
            );
        }

        balance -= amount;
        addTransaction(new Transaction(TransactionType.WITHDRAW, amount, "Cash withdrawal"));
    }

    public synchronized void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public synchronized void closeAccount() {
        if (!active) {
            throw new IllegalStateException("Account is already closed.");
        }
        if (balance > 0.0001) {
            throw new IllegalStateException(
                    "Account cannot be closed while balance is above zero."
            );
        }
        active = false;
    }

    protected void validatePositiveAmount(double amount) {
        if (Double.isNaN(amount) || Double.isInfinite(amount) || amount <= 0) {
            throw new IllegalArgumentException("Amount must be a valid positive number.");
        }
    }

    protected void validateActive() {
        if (!active) {
            throw new IllegalStateException("This account is closed.");
        }
    }

    public abstract double calculateInterest();

    public void displayDetails() {
        System.out.println("Account Number : " + accountNumber);
        System.out.println("Holder Name    : " + holderName);
        System.out.println("Account Type   : " + getAccountType());
        System.out.printf("Balance        : %.2f%n", balance);
        System.out.println("Status         : " + (active ? "Active" : "Closed"));
    }

    public final String getAccountNumber() {
        return accountNumber;
    }

    public final String getHolderName() {
        return holderName;
    }

    public final synchronized double getBalance() {
        return balance;
    }

    public final boolean isActive() {
        return active;
    }

    public abstract String getAccountType();

    public final List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }

    public String getExtraValueForStorage() {
        return "0";
    }

    protected synchronized void addInterest(double interest) {
        if (interest > 0) {
            balance += interest;
            addTransaction(new Transaction(
                    TransactionType.INTEREST,
                    interest,
                    "Interest credited"
            ));
        }
    }

    // Inner class to demonstrate Java inner classes.
    public class AccountInfo {
        public void print() {
            System.out.println(accountNumber + " | " + holderName + " | " + getAccountType());
        }
    }

    public AccountInfo getAccountInfo() {
        return new AccountInfo();
    }
}
