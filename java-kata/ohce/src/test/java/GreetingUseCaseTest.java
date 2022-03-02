import domain.GreetingUseCase;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GreetingUseCaseTest {
    /**
     * Rules of Greeting :
     * 1) User is greeted only in Spanish, but differently depending on the current time
     * 2) Between 6 and 12 hours, ohce will greet you saying: ¡Buenos días < your name >!
     * 3) Between 12 and 20 hours, ohce will greet you saying: ¡Buenas tardes < your name >!
     * 4) Between 20 and 6 hours, ohce will greet you saying: ¡Buenas noches < your name >!
     */
    @ParameterizedTest
    @CsvSource({
            "'06:00:00', Pedro, ¡Buenos días Pedro!",
            "'11:59:59', Silvia, ¡Buenos días Silvia!",
            "'12:00:00', Arturo, ¡Buenas tardes Arturo!",
            "'19:59:59', Maria, ¡Buenas tardes Maria!",
            "'20:00:00', Jorge, ¡Buenas noches Jorge!",
            "'05:59:59', Julia, ¡Buenas noches Julia!"
    })
    void shouldGreetUserAccordingToTheCurrentTime(
            String currentTime,
            String userName,
            String expectedGreeting
    ) {
        assertEquals(expectedGreeting,
                new GreetingUseCase(new DeterministicTimeProvider(currentTime)).execute(userName));
    }
}
