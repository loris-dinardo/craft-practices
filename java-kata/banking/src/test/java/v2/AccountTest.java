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
            "1000, 01-01-2022, 'DATE | AMOUNT | BALANCE\n01-01-2022 | 1000 | 1000'",
    })
    void shouldHaveTheCorrectAmountWhenClientMakesDeposit(
            int amount,
            String date,
            String expectedDisplay
    ) {
        setupEmptyAccount();
        sut.deposit(amount, date);
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

    private void assertResult(String expectedResult){
        sut.printStatement();
        assertEquals(expectedResult, ((InMemoryOutputGateway) outputGateway).printed());
    }
}
