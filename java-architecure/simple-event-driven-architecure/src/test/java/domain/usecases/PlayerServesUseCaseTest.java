package domain.usecases;

import domain.entities.BallSentEvent;
import infrastructure.adapters.InMemoryGameEventPublisher;
import infrastructure.adapters.InMemoryGameOutputDisplay;
import infrastructure.adapters.InMemoryUUIDGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerServesUseCaseTest {
    @ParameterizedTest
    @CsvSource({
            "Player A, eventUuid, idPoint, Player B, 'Player A has served for point idPoint against Player B'",
            "Federer, 223h-bhcd-adbzc, Point_1, Nadal, 'Federer has served for point Point_1 against Nadal'"
    })
    void whenGameStartsPlayerShouldServesAndNotifyOtherPlayer(
            String playerName,
            String eventUuid,
            String idPoint,
            String opponentName,
            String expectedOutput
    ) {
        InMemoryUUIDGenerator uuidGenerator = new InMemoryUUIDGenerator(eventUuid);
        InMemoryGameEventPublisher gameEventPublisher = new InMemoryGameEventPublisher();
        InMemoryGameOutputDisplay gameOutputDisplay = new InMemoryGameOutputDisplay();

        new PlayerServesUseCase(uuidGenerator, gameEventPublisher, gameOutputDisplay).playerServesForPoint(playerName, idPoint, opponentName);

        assertTrue(gameEventPublisher.events().contains(new BallSentEvent(eventUuid, idPoint, playerName, opponentName)));
        assertEquals(expectedOutput, gameOutputDisplay.printed());
    }
}
