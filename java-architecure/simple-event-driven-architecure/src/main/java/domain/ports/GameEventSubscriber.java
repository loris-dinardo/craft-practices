package domain.ports;

import domain.entities.PointEvent;

import java.util.function.Consumer;

public interface GameEventSubscriber {
    void subscribe(String idPoint, Consumer<PointEvent> subscriber);
}
