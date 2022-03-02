import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StopApplicationUseCaseTest {
    /**
     * Rules of stop application
     * 1) When user send "Stop!", the application should stop
     */

    @ParameterizedTest
    @CsvSource({
            "'Stop!', true",
            "'Stop', false",
            "'stop!', false",
            "'stop', false",
    })
    void shouldStopTheApplicationWhenUserSendTheCorrectWord(
            String sentCommandByUser,
            boolean expectedResult
    ) {
        assertEquals(expectedResult, new StopApplicationUseCase().execute(sentCommandByUser));
    }
}
