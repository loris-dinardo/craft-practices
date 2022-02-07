package v1;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountTest {
    @Nested
    class DepositTest {
        @Test
        void whenAccountBalanceIsEmptyAndDepositIsMadeTheAccountBalanceShouldBeEqualsToDeposit() {
            // arrange
            InMemoryLogGateway inMemoryLogGateway = new InMemoryLogGateway("01/04/2014");
            Account sut = new Account(0, inMemoryLogGateway, new InMemoryOutput());

            // act
            sut.deposit(1000);

            // assert
            assertEquals("01/04/2014 | 1000 | 1000", inMemoryLogGateway.getLastLog());
        }

        @Test
        void whenAccountBalanceIsNotEmptyAndDepositIsMadeTheAccountBalanceShouldBeEqualsToInitialBalancePlusDeposit() {
            // arrange
            InMemoryLogGateway inMemoryLogGateway = new InMemoryLogGateway("01/04/2014");
            Account sut = new Account(500, inMemoryLogGateway, new InMemoryOutput());

            // act
            sut.deposit(1000);

            // assert
            assertEquals("01/04/2014 | 1000 | 1500", inMemoryLogGateway.getLastLog());
        }
    }

    @Nested
    class WithdrawTest {
        @Test
        void whenAccountBalanceIsNotEmptyAndWithdrawIsMadeTheAccountBalanceShouldBeEqualsToInitialBalanceMinusWithdraw() {
            // arrange
            InMemoryLogGateway inMemoryLogGateway = new InMemoryLogGateway("02/04/2014");
            Account sut = new Account(1000, inMemoryLogGateway, new InMemoryOutput());

            // act
            sut.withdraw(100);

            // assert
            assertEquals("02/04/2014 | -100 | 900", inMemoryLogGateway.getLastLog());
        }

        @Test
        void whenAccountBalanceIsNotLargeEnoughToAllowTheWithdrawAnAlertShouldBeRaisedToForbidWithdraw() {
            // arrange
            InMemoryLogGateway inMemoryLogGateway = new InMemoryLogGateway("02/04/2014");
            Account sut = new Account(100, inMemoryLogGateway, new InMemoryOutput());

            // act & assert
            assertThrows(InsufficientBalanceException.class, () -> sut.withdraw(200));
        }
    }

    @Nested
    class PrintStatementTest {
        @Test
        void whenNotAnyTransactionMadeOnAccountPrintingStatementShouldDisplayEmptyResult() {
            // arrange
            InMemoryLogGateway inMemoryLogGateway = new InMemoryLogGateway("03/04/2014");
            InMemoryOutput inMemoryOutput = new InMemoryOutput();
            Account sut = new Account(0, inMemoryLogGateway, inMemoryOutput);

            // act
            sut.printStatement();

            // assert
            assertEquals("DATE | AMOUNT | BALANCE" + "\n", inMemoryOutput.getLocalConsoleContent());
        }

        @Test
        void whenOneSingleTransactionWasMadeOnAccountPrintingStatementShouldDisplayIt() {
            // arrange
            InMemoryLogGateway inMemoryLogGateway = new InMemoryLogGateway("01/04/2014");
            InMemoryOutput inMemoryOutput = new InMemoryOutput();
            Account sut = new Account(0, inMemoryLogGateway, inMemoryOutput);

            // act
            sut.deposit(1000);
            sut.printStatement();

            // assert
            assertEquals(
                    "DATE | AMOUNT | BALANCE" + "\n" + "01/04/2014 | 1000 | 1000",
                    inMemoryOutput.getLocalConsoleContent());
        }

        @Test
        void whenTwoTransactionWereMadeOnAccountPrintingStatementShouldDisplayThem() {
            // arrange
            InMemoryLogGateway inMemoryLogGateway = new InMemoryLogGateway("01/04/2014");
            InMemoryOutput inMemoryOutput = new InMemoryOutput();
            Account sut = new Account(0, inMemoryLogGateway, inMemoryOutput);

            // act
            sut.deposit(1000);
            sut.withdraw(100);
            sut.printStatement();

            // assert
            assertEquals(
                    "DATE | AMOUNT | BALANCE" + "\n"
                            + "01/04/2014 | -100 | 900" + "\n"
                            + "01/04/2014 | 1000 | 1000",
                    inMemoryOutput.getLocalConsoleContent());
        }

        @Test
        void whenThreeTransactionWereMadeOnAccountPrintingStatementShouldDisplayThem() {
            // arrange
            InMemoryLogGateway inMemoryLogGateway = new InMemoryLogGateway("01/04/2014");
            InMemoryOutput inMemoryOutput = new InMemoryOutput();
            Account sut = new Account(0, inMemoryLogGateway, inMemoryOutput);

            // act
            sut.deposit(1000);
            sut.withdraw(100);
            sut.deposit(500);
            sut.printStatement();

            // assert
            assertEquals(
                    "DATE | AMOUNT | BALANCE" + "\n"
                            + "01/04/2014 | 500 | 1400" + "\n"
                            + "01/04/2014 | -100 | 900" + "\n"
                            + "01/04/2014 | 1000 | 1000",
                    inMemoryOutput.getLocalConsoleContent());
        }
    }
}
