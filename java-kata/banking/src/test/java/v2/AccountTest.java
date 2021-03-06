package v2;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTest {

    private Account sut;
    private OutputGateway outputGateway;

    @ParameterizedTest
    @CsvSource({
            "'', 'DATE | AMOUNT | BALANCE\n'",
            "'01-01-2022 | 1000 | 1000', 'DATE | AMOUNT | BALANCE\n01-01-2022 | 1000 | 1000'",
            "'01-01-2022 | 1000 | 1000#02-01-2022 | -100 | 900', 'DATE | AMOUNT | BALANCE\n02-01-2022 | -100 | " +
                    "900\n01-01-2022 | 1000 | 1000'"
    })
    void shouldPrintTheStatementWhenClientAskTo(
            String existingTransactions,
            String expectedDisplay
    ) {
        setupAccountWithTransactions(existingTransactions);
        assertResult(expectedDisplay);
    }

    @ParameterizedTest
    @CsvSource({
            "0, '1000%01-01-2022', " +
                    "'DATE | AMOUNT | BALANCE\n" +
                    "01-01-2022 | 1000 | 1000'",
            "0, '1000%01-01-2022#1000%02-01-2022', " +
                    "'DATE | AMOUNT | BALANCE\n" +
                    "02-01-2022 | 1000 | 2000\n" +
                    "01-01-2022 | 1000 | 1000'",
            "0, '1000%01-01-2022#1000%02-01-2022#500%03-01-2022', " +
                    "'DATE | AMOUNT | BALANCE\n" +
                    "03-01-2022 | 500 | 2500\n" +
                    "02-01-2022 | 1000 | 2000\n" +
                    "01-01-2022 | 1000 | 1000'",
    })
    void shouldHaveTheCorrectAmountWhenClientHasInitialBalanceAndMakesDeposit(
            int initialBalance,
            String amountsAndDates,
            String expectedDisplay
    ) {
        setupAccountWithInitialBalance(initialBalance);
        AmountAndDate.fromParam(amountsAndDates)
                .forEach(amountAndDate -> sut.deposit(amountAndDate.amount, amountAndDate.date));
        assertResult(expectedDisplay);
    }

    @ParameterizedTest
    @CsvSource({
            "1000, '100%01-01-2022', " +
                    "'DATE | AMOUNT | BALANCE\n" +
                    "01-01-2022 | -100 | 900'",
            "1000, '100%01-01-2022#200%02-01-2022', " +
                    "'DATE | AMOUNT | BALANCE\n" +
                    "02-01-2022 | -200 | 700\n" +
                    "01-01-2022 | -100 | 900'",
            "1000, '100%01-01-2022#200%02-01-2022#300%03-01-2022', " +
                    "'DATE | AMOUNT | BALANCE\n" +
                    "03-01-2022 | -300 | 400\n" +
                    "02-01-2022 | -200 | 700\n" +
                    "01-01-2022 | -100 | 900'",
    })
    void shouldHaveTheCorrectAmountWhenClientHasInitialBalanceAndMakesWithdraw(
            int initialBalance,
            String amountsAndDates,
            String expectedDisplay
    ) {
        setupAccountWithInitialBalance(initialBalance);
        AmountAndDate.fromParam(amountsAndDates)
                .forEach(amountAndDate -> sut.withdraw(amountAndDate.amount, amountAndDate.date));
        assertResult(expectedDisplay);
    }

    @ParameterizedTest
    @CsvSource({
            "0, '1000%01-01-2022#-100%02-01-2022#500%03-01-2022', " +
                    "'DATE | AMOUNT | BALANCE\n" +
                    "03-01-2022 | 500 | 1400\n" +
                    "02-01-2022 | -100 | 900\n" +
                    "01-01-2022 | 1000 | 1000'",
    })
    void shouldHaveTheCorrectAmountWhenClientHasInitialBalanceAndMakesMultipleDepositsAndWithdraws(
            int initialBalance,
            String amountsAndDates,
            String expectedDisplay
    ) {
        setupAccountWithInitialBalance(initialBalance);
        AmountAndDate.fromParam(amountsAndDates)
                .forEach(
                        amountAndDate -> {
                            if (amountAndDate.operationType == OperationType.DEPOSIT) {
                                sut.deposit(amountAndDate.amount, amountAndDate.date);
                            } else {
                                sut.withdraw(amountAndDate.amount, amountAndDate.date);
                            }
                        }
                );
        assertResult(expectedDisplay);
    }

    private void setupAccountWithTransactions(String existingTransactions) {
        outputGateway = new InMemoryOutputGateway();
        sut = new Account(List.of(existingTransactions.split("#")), outputGateway);
    }

    private void setupAccountWithInitialBalance(int initialBalance) {
        outputGateway = new InMemoryOutputGateway();
        sut = new Account(initialBalance, outputGateway);
    }

    private void assertResult(String expectedResult) {
        sut.printStatement();
        assertEquals(expectedResult, ((InMemoryOutputGateway) outputGateway).printed());
    }
}
