public class Account {
    private final LogAccountGateway logGateway;
    private final Output output;
    private int balance;

    public Account(int initialBalance, LogAccountGateway logGateway, Output output) {
        this.balance = initialBalance;
        this.logGateway = logGateway;
        this.output = output;
    }

    public void deposit(int amount) {
        this.balance += amount;
        this.logGateway.log(amount, this.balance);
    }

    public void withdraw(int amount) {
        if (this.balance - amount < 0) {
            throw new InsufficientBalanceException();
        }
        this.balance -= amount;
        this.logGateway.log(-amount, this.balance);
    }

    public void printStatement() {
        this.output.print("DATE | AMOUNT | BALANCE");
        this.output.print(this.logGateway.getAllLogs());
    }
}
