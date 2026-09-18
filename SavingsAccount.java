package bank;

public class SavingsAccount extends BankAccount {
    private final double interestRate;

    public SavingsAccount(String accountNumber, String holderName,
                           double initialBalance, double interestRate) {
        super(accountNumber, holderName, initialBalance);

        if (interestRate < 0) {
            throw new IllegalArgumentException("Interest rate cannot be negative.");
        }
        this.interestRate = interestRate;
    }

    @Override
    public double calculateInterest() {
        double interest = getBalance() * interestRate / 100.0;
        addInterest(interest);
        return interest;
    }

    @Override
    public String getAccountType() {
        return "Savings";
    }

    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.printf("Interest Rate  : %.2f%%%n", interestRate);
    }

    public double getInterestRate() {
        return interestRate;
    }

    @Override
    public String getExtraValueForStorage() {
        return Double.toString(interestRate);
    }
}
