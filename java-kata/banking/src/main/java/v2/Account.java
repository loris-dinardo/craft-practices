package v2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Account {
    private final List<String> transactions;
    private final OutputGateway outputGateway;

    public Account(OutputGateway outputGateway) {
        this(Collections.emptyList(), outputGateway);
    }

    public Account(List<String> existingTransaction, OutputGateway outputGateway) {
        this.transactions = existingTransaction;
        this.outputGateway = outputGateway;
    }

    public void deposit(int amount, String date) {
    }

    public void withdraw(int amount, String date) {
    }

    public void printStatement() {
        outputGateway.print("DATE | AMOUNT | BALANCE");
        reverseTransactions().forEach(outputGateway::print);
    }

    private List<String> reverseTransactions() {
        List<String> transactionsToBeReversed = new ArrayList<>(transactions);
        Collections.reverse(transactionsToBeReversed);
        return transactionsToBeReversed;
    }
}
