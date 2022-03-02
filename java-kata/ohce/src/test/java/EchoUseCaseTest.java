import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EchoUseCaseTest {
    @ParameterizedTest
    @CsvSource({
            "echo, ohce",
            "Hola!, !aloH"
    })
    void shouldReturnTheWorldSentByUserInReverse(
            String worldSentByUser,
            String expectedWorldRevere
    ){
        assertEquals(expectedWorldRevere, new EchoUseCase().execute(worldSentByUser));
    }
}
