import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTest {
    @Nested
    class DepositTest {
        @Test
        void whenAccountIsEmptyAndDepositIsMadeTheAccountBalanceShouldBeEqualsToDeposit() {
            // arrange
            InMemoryLogGateway inMemoryLogGateway = new InMemoryLogGateway("01/04/2014");
            Account sut = new Account(0, inMemoryLogGateway);

            // act
            sut.deposit(1000);

            // assert
            assertEquals("01/04/2014 | 1000 | 1000", inMemoryLogGateway.getLastLog());
        }

        @Test
        void whenAccountIsNotEmptyAndDepositIsMadeTheAccountBalanceShouldBeEqualsToInitialBalancePlusDeposit() {
            // arrange
            InMemoryLogGateway inMemoryLogGateway = new InMemoryLogGateway("01/04/2014");
            Account sut = new Account(500, inMemoryLogGateway);

            // act
            sut.deposit(1000);

            // assert
            assertEquals("01/04/2014 | 1000 | 1500", inMemoryLogGateway.getLastLog());
        }
    }

}
