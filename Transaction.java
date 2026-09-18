package bank;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private static int counter = 1000;

    private final String transactionId;
    private final TransactionType type;
    private final double amount;
    private final String description;
    private final LocalDateTime timestamp;

    public Transaction(TransactionType type, double amount, String description) {
        this.transactionId = "TXN" + (++counter);
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.timestamp = LocalDateTime.now();
    }

    public Transaction(String transactionId, TransactionType type, double amount,
                       String description, LocalDateTime timestamp) {
        this.transactionId = transactionId;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.timestamp = timestamp;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return String.format("%-9s %-14s %-10.2f %-20s %s",
                transactionId, type, amount, description, timestamp.format(format));
    }
}
