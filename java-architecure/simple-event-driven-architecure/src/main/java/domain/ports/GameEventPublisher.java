package domain.ports;

import domain.entities.PointEvent;

public interface GameEventPublisher {
    void publish(PointEvent event);
}
