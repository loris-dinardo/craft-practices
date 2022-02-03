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
            Account sut = new Account(0, inMemoryLogGateway);

            // act
            sut.deposit(1000);

            // assert
            assertEquals("01/04/2014 | 1000 | 1000", inMemoryLogGateway.getLastLog());
        }

        @Test
        void whenAccountBalanceIsNotEmptyAndDepositIsMadeTheAccountBalanceShouldBeEqualsToInitialBalancePlusDeposit() {
            // arrange
            InMemoryLogGateway inMemoryLogGateway = new InMemoryLogGateway("01/04/2014");
            Account sut = new Account(500, inMemoryLogGateway);

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
            Account sut = new Account(1000, inMemoryLogGateway);

            // act
            sut.withdraw(100);

            // assert
            assertEquals("02/04/2014 | -100 | 900", inMemoryLogGateway.getLastLog());
        }

        @Test
        void whenAccountBalanceIsNotLargeEnoughToAllowTheWithdrawAnAlertShouldBeRaisedToForbidWithdraw() {
            // arrange
            InMemoryLogGateway inMemoryLogGateway = new InMemoryLogGateway("02/04/2014");
            Account sut = new Account(100, inMemoryLogGateway);

            // act & assert
            assertThrows(InsufficientBalanceException.class, () -> sut.withdraw(200));
        }
    }
}
