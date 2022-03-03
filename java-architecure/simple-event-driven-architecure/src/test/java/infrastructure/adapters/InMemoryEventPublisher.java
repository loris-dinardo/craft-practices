package infrastructure.adapters;

import domain.entities.DomainEvent;
import domain.ports.EventPublisher;

import java.util.ArrayList;
import java.util.List;

public class InMemoryEventPublisher implements EventPublisher {
    private final List<DomainEvent> events = new ArrayList<>();

    @Override
    public void publish(DomainEvent event) {
        this.events.add(event);
    }

    public List<DomainEvent> events() {
        return events;
    }
}
