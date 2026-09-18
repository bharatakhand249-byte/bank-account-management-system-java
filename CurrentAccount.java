package bank;

public class CurrentAccount extends BankAccount {
    private final double minimumBalance;

    public CurrentAccount(String accountNumber, String holderName,
                          double initialBalance, double minimumBalance) {
        super(accountNumber, holderName, initialBalance);

        if (minimumBalance < 0) {
            throw new IllegalArgumentException("Minimum balance cannot be negative.");
        }
        this.minimumBalance = minimumBalance;
    }

    @Override
    public synchronized void withdraw(double amount) throws InsufficientBalanceException {
        validateActive();
        validatePositiveAmount(amount);

        if (getBalance() - amount < minimumBalance) {
            throw new InsufficientBalanceException(
                    "Withdrawal denied. Minimum required balance is "
                            + String.format("%.2f", minimumBalance)
            );
        }

        super.withdraw(amount);
    }

    @Override
    public double calculateInterest() {
        return 0.0;
    }

    @Override
    public String getAccountType() {
        return "Current";
    }

    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.printf("Minimum Balance: %.2f%n", minimumBalance);
    }

    public double getMinimumBalance() {
        return minimumBalance;
    }

    @Override
    public String getExtraValueForStorage() {
        return Double.toString(minimumBalance);
    }
}
