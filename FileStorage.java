package bank;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileStorage {
    private static final String FILE_PATH = "data/accounts.csv";

    public void saveAccounts(List<BankAccount> accounts) throws IOException {
        File file = new File(FILE_PATH);
        File parent = file.getParentFile();

        if (parent != null && !parent.exists() && !parent.mkdirs()) {
            throw new IOException("Unable to create data directory.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("accountNumber,accountType,holderName,balance,active,extraValue");
            writer.newLine();

            for (BankAccount account : accounts) {
                writer.write(csv(account.getAccountNumber()) + ","
                        + csv(account.getAccountType()) + ","
                        + csv(account.getHolderName()) + ","
                        + account.getBalance() + ","
                        + account.isActive() + ","
                        + account.getExtraValueForStorage());
                writer.newLine();
            }
        }
    }

    public List<BankAccount> loadAccounts() throws IOException {
        List<BankAccount> accounts = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return accounts;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean header = true;

            while ((line = reader.readLine()) != null) {
                if (header) {
                    header = false;
                    continue;
                }

                if (line.isBlank()) {
                    continue;
                }

                String[] parts = line.split(",", -1);
                if (parts.length < 6) {
                    continue;
                }

                try {
                    String accountNumber = parts[0].trim();
                    String type = parts[1].trim();
                    String holder = parts[2].trim();
                    double balance = Double.parseDouble(parts[3].trim());
                    boolean active = Boolean.parseBoolean(parts[4].trim());
                    double extra = Double.parseDouble(parts[5].trim());

                    BankAccount account;

                    if ("Savings".equalsIgnoreCase(type)) {
                        account = new SavingsAccount(accountNumber, holder, balance, extra);
                    } else if ("Current".equalsIgnoreCase(type)) {
                        account = new CurrentAccount(accountNumber, holder, balance, extra);
                    } else {
                        continue;
                    }

                    if (!active && account.isActive()) {
                        try {
                            if (Math.abs(account.getBalance()) < 0.0001) {
                                account.closeAccount();
                            }
                        } catch (IllegalStateException ignored) {
                            // Keep the record usable if a legacy row is inconsistent.
                        }
                    }

                    accounts.add(account);
                } catch (RuntimeException ignored) {
                    // Ignore malformed rows instead of crashing the whole application.
                }
            }
        }

        return accounts;
    }

    private String csv(String value) {
        if (value.contains(",") || value.contains("\"")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}
