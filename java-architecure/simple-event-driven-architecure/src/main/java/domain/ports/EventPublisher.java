package domain.ports;

import domain.entities.DomainEvent;

public interface EventPublisher {
    void publish(DomainEvent event);
}
