package application;

import domain.ports.*;
import infrastructure.adapters.ConsoleGameOutputDisplay;
import infrastructure.adapters.InMemoryGameEventBus;
import infrastructure.adapters.RandomDoesPlayerHitTheBall;
import infrastructure.adapters.RealUUIDGenerator;

public class PlayerFactory {
    private final UUIDGenerator uuidGenerator;
    private final GameEventPublisher gameEventPublisher;
    private final GameEventSubscriber gameEventSubscriber;
    private final GameOutputDisplay gameOutputDisplay;
    private final DoesPlayerHitTheBall doesPlayerHitTheBall;

    public PlayerFactory(UUIDGenerator uuidGenerator,
                         GameEventPublisher gameEventPublisher,
                         GameEventSubscriber gameEventSubscriber,
                         GameOutputDisplay gameOutputDisplay,
                         DoesPlayerHitTheBall doesPlayerHitTheBall) {
        this.uuidGenerator = uuidGenerator;
        this.gameEventPublisher = gameEventPublisher;
        this.gameEventSubscriber = gameEventSubscriber;
        this.gameOutputDisplay = gameOutputDisplay;
        this.doesPlayerHitTheBall = doesPlayerHitTheBall;
    }

    public Player createPlayerNamed(String playerName) {
        return new Player(
                playerName,
                uuidGenerator,
                gameEventPublisher,
                gameEventSubscriber,
                gameOutputDisplay,
                doesPlayerHitTheBall
        );
    }

    public static PlayerFactory getDefault() {
        InMemoryGameEventBus eventBus = new InMemoryGameEventBus();
        return new PlayerFactory(
                new RealUUIDGenerator(),
                eventBus,
                eventBus,
                new ConsoleGameOutputDisplay(),
                new RandomDoesPlayerHitTheBall()
        );
    }
}
