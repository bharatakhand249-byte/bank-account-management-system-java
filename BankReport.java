package bank;

import java.util.List;

public class BankReport {

    public void printSummary(List<BankAccount> accounts) {
        int active = 0;
        int closed = 0;
        double totalBalance = 0;

        for (BankAccount account : accounts) {
            if (account.isActive()) {
                active++;
                totalBalance += account.getBalance();
            } else {
                closed++;
            }
        }

        System.out.println("\n========== BANK SUMMARY ==========");
        System.out.println("Total Accounts : " + accounts.size());
        System.out.println("Active Accounts: " + active);
        System.out.println("Closed Accounts: " + closed);
        System.out.printf("Total Balance  : %.2f%n", totalBalance);
        System.out.println("==================================");
    }

    public void printAccountTypeReport(List<BankAccount> accounts) {
        int savings = 0;
        int current = 0;

        for (BankAccount account : accounts) {
            if (account instanceof SavingsAccount) {
                savings++;
            } else if (account instanceof CurrentAccount) {
                current++;
            }
        }

        System.out.println("\n======= ACCOUNT TYPE REPORT =======");
        System.out.println("Savings Accounts : " + savings);
        System.out.println("Current Accounts : " + current);
        System.out.println("===================================");
    }

    public void printAccounts(List<BankAccount> accounts) {
        System.out.println("\n================ ACCOUNTS ================");
        if (accounts.isEmpty()) {
            System.out.println("No accounts available.");
            return;
        }

        for (BankAccount account : accounts) {
            System.out.printf("%-12s %-20s %-10s %-12.2f %-8s%n",
                    account.getAccountNumber(),
                    account.getHolderName(),
                    account.getAccountType(),
                    account.getBalance(),
                    account.isActive() ? "Active" : "Closed");
        }
        System.out.println("===========================================");
    }
}
