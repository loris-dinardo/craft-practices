package infrastructure.adapters;

import domain.entities.PointEvent;
import domain.ports.GameEventPublisher;
import domain.ports.GameEventSubscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class InMemoryGameEventBus implements GameEventPublisher, GameEventSubscriber {
    private final Map<String, List<Consumer<PointEvent>>> subscribers = new HashMap<>();

    @Override
    public void publish(PointEvent event) {
        for (Consumer<PointEvent> subscriber : subscribers.get(event.getIdPoint())) {
            subscriber.accept(event);
        }
    }

    @Override
    public void subscribe(String idPoint, Consumer<PointEvent> subscriber) {
        subscribers.putIfAbsent(idPoint, new ArrayList<>());
        subscribers.get(idPoint).add(subscriber);
    }
}
