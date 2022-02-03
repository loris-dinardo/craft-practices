public class Account {
    private final LogAccountGateway logGateway;
    private int balance;

    public Account(int initialBalance, LogAccountGateway logGateway) {
        this.balance = initialBalance;
        this.logGateway = logGateway;
    }

    public void deposit(int amount) {
        this.balance += amount;
        this.logGateway.log(amount, this.balance);
    }

    public void withdraw(int amount) {
    }

    public void printStatement() {
    }
}
