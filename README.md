# Bank Account Management System

A console-based Java project developed for the **VITyarthi – Build Your Own Project** requirement and aligned with the CSE2006 Programming in Java syllabus.

> **Academic simulation only:** This project is not connected to any real bank and must not be used with real financial or personal information.

## 1. Project Overview

The Bank Account Management System simulates common banking operations through a menu-driven console application. It supports customer/account management, deposits, withdrawals, transfers, transaction history, and account reports.

The implementation intentionally uses only the **Java Standard Library**. There are no external Java libraries, Maven/Gradle dependencies, database servers, JDBC drivers, or GUI frameworks.

## 2. Major Functional Modules

1. **Account Management**
   - Create savings/current accounts
   - View all accounts
   - Search an account
   - Close an account

2. **Transaction Management**
   - Deposit
   - Withdraw
   - Transfer between accounts
   - View transaction history

3. **Reports & Data Persistence**
   - Account summary
   - Total bank balance
   - Account-type report
   - Save/load account data using CSV files

4. **Validation & Error Handling**
   - Invalid menu choices
   - Invalid amounts
   - Duplicate account numbers
   - Insufficient balance
   - Closed/non-existent accounts

## 3. Java Concepts Demonstrated

The project demonstrates concepts from the CSE2006 syllabus:

- Variables, data types, operators and flow control
- Classes and objects
- Constructors and methods
- Encapsulation
- `this`, `final`, `instanceof`
- Inheritance
- Method overriding
- Method overloading
- `super`
- Abstract class and methods
- Interface
- Runtime polymorphism
- Enum
- Nested/inner class
- Exception handling: `try-catch`, `throw`, `throws`, custom exception
- Multithreading
- `ArrayList` and collections
- Arrays and strings
- Character-oriented file I/O
- CSV persistence
- Modular package structure

## 4. Project Structure

```text
BankAccountManagementSystem/
├── src/
│   └── bank/
│       ├── Main.java
│       ├── Bank.java
│       ├── BankAccount.java
│       ├── SavingsAccount.java
│       ├── CurrentAccount.java
│       ├── Transaction.java
│       ├── TransactionType.java
│       ├── InsufficientBalanceException.java
│       ├── FileStorage.java
│       └── BankReport.java
├── data/
│   └── accounts.csv
├── tests/
│   └── BankValidationTest.java
├── docs/
│   └── diagrams.md
├── statement.md
└── README.md
```

## 5. Requirements

- A Java Development Kit (JDK) installed on the computer.
- VS Code can be used as the editor and terminal.
- No external Java library or database application is required.

Check Java:

```bash
java -version
javac -version
```

## 6. How to Run in VS Code

Open the project folder in VS Code.

### Windows PowerShell / Command Prompt

Compile:

```bash
javac -d out src\bank\*.java
```

Run:

```bash
java -cp out bank.Main
```

### macOS / Linux

Compile:

```bash
javac -d out src/bank/*.java
```

Run:

```bash
java -cp out bank.Main
```

If your system does not have Java installed, install a JDK first. The project itself does not require any external Java dependency.

## 7. Testing

Compile the main project:

```bash
javac -d out src/bank/*.java
```

Compile the validation test:

```bash
javac -cp out -d out tests/BankValidationTest.java
```

Run:

```bash
java -cp out BankValidationTest
```

The test checks duplicate account validation, invalid transaction amounts, insufficient balance handling, and transfer validation.

## 8. Sample Workflow

1. Start the application.
2. Select **Create Savings Account**.
3. Enter account holder name and initial deposit.
4. Create another account.
5. Deposit money into an account.
6. Withdraw money.
7. Transfer money to another account.
8. View transaction history.
9. Open Reports.
10. Save data before exiting.

## 9. Storage

Account data is saved in:

```text
data/accounts.csv
```

The application automatically creates/updates this file when the user chooses **Save Data**.

No database is used, which keeps the project portable and dependency-free.

## 10. Non-Functional Requirements

- **Usability:** Menu-driven console interface with clear prompts.
- **Reliability:** Validation and custom exception handling prevent invalid transactions.
- **Security:** The application is an academic simulation and does not collect real banking credentials or connect to financial systems.
- **Maintainability:** Separate classes and package structure divide responsibilities.
- **Resource efficiency:** Uses Java collections and simple CSV persistence rather than a database server.
- **Error handling:** Invalid input, missing accounts, duplicate accounts, closed accounts and insufficient funds are handled.

## 11. Design Approach

The central `BankAccount` class is abstract. `SavingsAccount` and `CurrentAccount` inherit from it. Transaction behavior is exposed through the `AccountOperations` interface.

This allows the project to demonstrate inheritance, abstraction, interfaces and runtime polymorphism in one realistic scenario.

## 12. Limitations

- Console interface only.
- CSV storage instead of a production database.
- No real authentication.
- No real payment gateway.
- Not intended for actual banking use.

## 13. Future Enhancements

- JDBC/MySQL persistence
- JPA/ORM
- Secure authentication
- GUI/web interface
- Unit testing framework
- Role-based access
- Advanced analytics
- Transaction export to PDF

## 14. Academic Alignment

The project is aligned with the CSE2006 Programming in Java topics covering Java fundamentals, OOP, inheritance/polymorphism, exception handling, multithreading, collections, I/O streams and database-related concepts. JDBC/JPA are listed as possible future enhancements rather than runtime dependencies.

## 15. Author

Student project for VITyarthi – Build Your Own Project.
