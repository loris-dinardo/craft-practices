package infrastructure.adapters;

import domain.entities.DomainEvent;
import domain.ports.GameEventPublisher;

import java.util.ArrayList;
import java.util.List;

public class InMemoryGameEventPublisher implements GameEventPublisher {
    private final List<DomainEvent> events = new ArrayList<>();

    @Override
    public void publish(DomainEvent event) {
        this.events.add(event);
    }

    public List<DomainEvent> events() {
        return events;
    }
}
