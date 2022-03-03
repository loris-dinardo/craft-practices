package domain.ports;

import domain.entities.DomainEvent;

public interface GameEventPublisher {
    void publish(DomainEvent event);
}
