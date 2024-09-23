// Subsystem: Account Management
class Account {
    private String accountNumber;
    private double balance;

    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
        this.balance = 0.0;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited: $" + amount + " | New Balance: $" + balance);
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.println("Withdrew: $" + amount + " | New Balance: $" + balance);
        } else {
            System.out.println("Insufficient funds for withdrawal.");
        }
    }

    public double getBalance() {
        return balance;
    }
}

// Subsystem: Transaction Processing
class Transaction {
    public void processTransaction(String type, double amount, Account account) {
        if (type.equalsIgnoreCase("deposit")) {
            account.deposit(amount);
        } else if (type.equalsIgnoreCase("withdraw")) {
            account.withdraw(amount);
        }
    }
}

// Subsystem: Loan Application
class Loan {
    public void applyForLoan(double amount) {
        System.out.println("Loan application for: $" + amount + " has been submitted.");
    }
}

// Facade Class
class BankingFacade {
    private Account account;
    private Transaction transaction;
    private Loan loan;

    public BankingFacade(String accountNumber) {
        this.account = new Account(accountNumber);
        this.transaction = new Transaction();
        this.loan = new Loan();
    }

    public void deposit(double amount) {
        transaction.processTransaction("deposit", amount, account);
    }

    public void withdraw(double amount) {
        transaction.processTransaction("withdraw", amount, account);
    }

    public void checkBalance() {
        System.out.println("Current Balance: $" + account.getBalance());
    }

    public void applyForLoan(double amount) {
        loan.applyForLoan(amount);
    }
}

// Client code
public class BankingSystemExample {
    public static void main(String[] args) {
        BankingFacade bankingFacade = new BankingFacade("123456");

        bankingFacade.deposit(500);
        bankingFacade.checkBalance();
        bankingFacade.withdraw(200);
        bankingFacade.checkBalance();
        bankingFacade.applyForLoan(15000);
    }
}
