package v2;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
            "'1000%01-01-2022', " +
                    "'DATE | AMOUNT | BALANCE\n" +
                    "01-01-2022 | 1000 | 1000'",
            "'1000%01-01-2022#1000%02-01-2022', " +
                    "'DATE | AMOUNT | BALANCE\n" +
                    "02-01-2022 | 1000 | 2000\n" +
                    "01-01-2022 | 1000 | 1000'",
            "'1000%01-01-2022#1000%02-01-2022#500%03-01-2022', " +
                    "'DATE | AMOUNT | BALANCE\n" +
                    "03-01-2022 | 500 | 2500\n" +
                    "02-01-2022 | 1000 | 2000\n" +
                    "01-01-2022 | 1000 | 1000'",
    })
    void shouldHaveTheCorrectAmountWhenClientMakesDeposit(
            String amountsAndDates,
            String expectedDisplay
    ) {
        setupEmptyAccount();
        getAmountsAndDatesFromParam(amountsAndDates)
                .forEach(amountAndDate -> sut.deposit(amountAndDate.amount, amountAndDate.date));
        assertResult(expectedDisplay);
    }

    private void setupEmptyAccount() {
        outputGateway = new InMemoryOutputGateway();
        sut = new Account(outputGateway);
    }

    private void setupAccountWithTransactions(String existingTransactions) {
        outputGateway = new InMemoryOutputGateway();
        sut = new Account(List.of(existingTransactions.split("#")), outputGateway);
    }

    static class AmountAndDate {
        public int amount;
        public String date;

        public AmountAndDate(int amount, String date) {
            this.amount = amount;
            this.date = date;
        }
    }

    private List<AmountAndDate> getAmountsAndDatesFromParam(String param) {
        String[] data = param.split("#");
        return Arrays.stream(data).map(amountAndDate -> {
            String[] values = amountAndDate.split("%");
            return new AmountAndDate(Integer.parseInt(values[0]), values[1]);
        }).collect(Collectors.toList());
    }

    private void assertResult(String expectedResult) {
        sut.printStatement();
        assertEquals(expectedResult, ((InMemoryOutputGateway) outputGateway).printed());
    }
}
