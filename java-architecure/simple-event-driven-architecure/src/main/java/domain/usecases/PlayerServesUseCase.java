package domain.usecases;

import domain.entities.BallSentEvent;
import domain.ports.GameEventPublisher;
import domain.ports.GameOutputDisplay;
import domain.ports.UUIDGenerator;

public class PlayerServesUseCase {
    private final GameEventPublisher gameEventPublisher;
    private final UUIDGenerator uuidGenerator;
    private final GameOutputDisplay gameOutputDisplay;

    public PlayerServesUseCase(UUIDGenerator uuidGenerator,
                               GameEventPublisher gameEventPublisher,
                               GameOutputDisplay gameOutputDisplay) {
        this.uuidGenerator = uuidGenerator;
        this.gameEventPublisher = gameEventPublisher;
        this.gameOutputDisplay = gameOutputDisplay;
    }

    public void playerServesForPoint(String playerName, String idPoint) {
        gameEventPublisher.publish(new BallSentEvent(uuidGenerator.nextUUID(), idPoint));
        gameOutputDisplay.print(playerName + " has served for point " + idPoint);
    }
}
