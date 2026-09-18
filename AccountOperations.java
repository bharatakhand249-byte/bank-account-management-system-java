package bank;

public interface AccountOperations {
    void deposit(double amount);
    void withdraw(double amount) throws InsufficientBalanceException;
}
