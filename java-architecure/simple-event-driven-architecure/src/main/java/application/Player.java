package application;

import domain.entities.BallSentEvent;
import domain.entities.PointEvent;
import domain.ports.*;
import domain.usecases.PlayerReturnsBallUseCase;
import domain.usecases.PlayerServesUseCase;

public class Player {
    private final String playerName;
    private final GameEventSubscriber gameEventSubscriber;
    private final PlayerServesUseCase servesUseCase;
    private final PlayerReturnsBallUseCase returnsBallUseCase;

    public Player(String playerName,
                  UUIDGenerator uuidGenerator,
                  GameEventPublisher gameEventPublisher,
                  GameEventSubscriber gameEventSubscriber,
                  GameOutputDisplay gameOutputDisplay,
                  DoesPlayerHitTheBall doesPlayerHitTheBall) {
        this.playerName = playerName;
        this.gameEventSubscriber = gameEventSubscriber;
        this.servesUseCase = new PlayerServesUseCase(uuidGenerator, gameEventPublisher, gameOutputDisplay);
        this.returnsBallUseCase = new PlayerReturnsBallUseCase(
                gameEventPublisher,
                gameOutputDisplay,
                doesPlayerHitTheBall
        );
    }

    public String getPlayerName() {
        return playerName;
    }

    public void servesForPointAgainstOpponent(String idPoint, Player opponent) {
        this.gameEventSubscriber.subscribe(idPoint, this::tryToReturnBallForPoint);
        this.gameEventSubscriber.subscribe(idPoint, opponent::tryToReturnBallForPoint);
        this.servesUseCase.playerServesForPoint(playerName, idPoint, opponent.getPlayerName());
    }

    private void tryToReturnBallForPoint(PointEvent pointEvent) {
        if (pointEvent instanceof BallSentEvent
                && ((BallSentEvent) pointEvent).getToPlayerName().equals(playerName))
            this.returnsBallUseCase.playerTriesToReturnTheBall((BallSentEvent) pointEvent);
    }
}
