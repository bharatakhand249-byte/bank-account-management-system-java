# Design Diagrams

The diagrams below are written in Mermaid notation for documentation. They are not required for running the Java program.

## 1. System Architecture

```mermaid
flowchart TD
    U[User] --> M[Main Console]
    M --> B[Bank Service]
    B --> A[Account Classes]
    B --> T[Transaction Records]
    B --> R[Bank Report]
    B --> F[CSV File Storage]
    F --> D[(data/accounts.csv)]
    B --> V[Validation & Exceptions]
    B --> TH[Background Interest Thread]
```

## 2. Workflow

```mermaid
flowchart TD
    S[Start] --> L[Load saved data]
    L --> MENU[Display Menu]
    MENU --> C[Create Account]
    MENU --> D[Deposit]
    MENU --> W[Withdraw]
    MENU --> TR[Transfer]
    MENU --> H[Transaction History]
    MENU --> R[Reports]
    MENU --> X[Close Account]
    MENU --> SV[Save Data]
    MENU --> E[Exit]
    C --> MENU
    D --> MENU
    W --> MENU
    TR --> MENU
    H --> MENU
    R --> MENU
    X --> MENU
    SV --> MENU
    E --> END[End]
```

## 3. Use Case Diagram

```mermaid
flowchart LR
    User((User))
    Create[Create Account]
    View[View/Search Accounts]
    Deposit[Deposit]
    Withdraw[Withdraw]
    Transfer[Transfer]
    History[View Transactions]
    Report[View Reports]
    Save[Save Data]
    Close[Close Account]

    User --> Create
    User --> View
    User --> Deposit
    User --> Withdraw
    User --> Transfer
    User --> History
    User --> Report
    User --> Save
    User --> Close
```

## 4. Class Diagram

```mermaid
classDiagram
    class Bank {
        -ArrayList~BankAccount~ accounts
        +createSavingsAccount()
        +createCurrentAccount()
        +deposit()
        +withdraw()
        +transfer()
        +findAccount()
        +closeAccount()
        +saveData()
        +loadData()
    }

    class BankAccount {
        <<abstract>>
        -String accountNumber
        -String holderName
        -double balance
        -boolean active
        -ArrayList~Transaction~ transactions
        +deposit(double)
        +withdraw(double)
        +addTransaction()
        +calculateInterest()
        +displayDetails()
    }

    class SavingsAccount {
        -double interestRate
        +calculateInterest()
        +displayDetails()
    }

    class CurrentAccount {
        -double minimumBalance
        +calculateInterest()
        +displayDetails()
    }

    class Transaction {
        -String transactionId
        -TransactionType type
        -double amount
        -String description
        -LocalDateTime timestamp
    }

    class TransactionType {
        <<enumeration>>
        DEPOSIT
        WITHDRAW
        TRANSFER_IN
        TRANSFER_OUT
        INTEREST
    }

    class FileStorage {
        +saveAccounts()
        +loadAccounts()
    }

    class BankReport {
        +printSummary()
        +printAccountTypeReport()
    }

    class AccountOperations {
        <<interface>>
        +deposit()
        +withdraw()
    }

    BankAccount <|-- SavingsAccount
    BankAccount <|-- CurrentAccount
    AccountOperations <|.. BankAccount
    BankAccount "1" --> "*" Transaction
    Transaction --> TransactionType
    Bank --> BankAccount
    Bank --> FileStorage
    Bank --> BankReport
```

## 5. Sequence Diagram – Transfer

```mermaid
sequenceDiagram
    actor User
    participant Main
    participant Bank
    participant Source as Source Account
    participant Target as Target Account

    User->>Main: Select Transfer
    Main->>Bank: transfer(source, target, amount)
    Bank->>Source: withdraw(amount)
    Source-->>Bank: withdrawal successful
    Bank->>Target: deposit(amount)
    Target-->>Bank: deposit successful
    Bank-->>Main: transfer completed
    Main-->>User: display result
```

## 6. Storage Design

CSV file:

```text
accountNumber,accountType,holderName,balance,active,extraValue
```

Where `extraValue` represents the savings interest rate or current-account minimum balance.

Transaction history is kept in memory during the current run. Account balances and basic account information are persisted to CSV.
