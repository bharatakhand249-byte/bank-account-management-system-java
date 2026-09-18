import bank.Bank;
import bank.BankAccount;
import bank.InsufficientBalanceException;

public class BankValidationTest {
    public static void main(String[] args) throws Exception {
        Bank bank = new Bank();

        bank.createSavingsAccount("S001", "Test User", 1000, 5);

        expectException(
                () -> bank.createCurrentAccount("S001", "Duplicate", 500, 100),
                "duplicate account"
        );

        expectException(
                () -> bank.deposit("S001", -10),
                "negative deposit"
        );

        expectException(
                () -> bank.withdraw("S001", 5000),
                "insufficient balance"
        );

        bank.createCurrentAccount("C001", "Second User", 1000, 500);

        expectException(
                () -> bank.transfer("S001", "C001", 0),
                "zero transfer"
        );

        bank.deposit("S001", 500);
        bank.transfer("S001", "C001", 200);

        BankAccount source = bank.findAccount("S001");
        BankAccount target = bank.findAccount("C001");

        assertTrue(Math.abs(source.getBalance() - 1300) < 0.001,
                "source balance after transfer");
        assertTrue(Math.abs(target.getBalance() - 1200) < 0.001,
                "target balance after transfer");

        System.out.println("ALL VALIDATION TESTS PASSED.");
    }

    private static void expectException(ThrowingAction action, String testName) throws Exception {
        try {
            action.run();
            throw new AssertionError("FAILED: " + testName + " did not throw an exception.");
        } catch (IllegalArgumentException | IllegalStateException |
                 InsufficientBalanceException expected) {
            System.out.println("PASSED: " + testName);
        }
    }

    private static void assertTrue(boolean condition, String testName) {
        if (!condition) {
            throw new AssertionError("FAILED: " + testName);
        }
        System.out.println("PASSED: " + testName);
    }

    @FunctionalInterface
    interface ThrowingAction {
        void run() throws Exception;
    }
}
