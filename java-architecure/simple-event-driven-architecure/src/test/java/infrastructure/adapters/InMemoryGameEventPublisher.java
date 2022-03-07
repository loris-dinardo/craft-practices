package infrastructure.adapters;

import domain.entities.PointEvent;
import domain.ports.GameEventPublisher;

import java.util.ArrayList;
import java.util.List;

public class InMemoryGameEventPublisher implements GameEventPublisher {
    private final List<PointEvent> events = new ArrayList<>();

    @Override
    public void publish(PointEvent event) {
        this.events.add(event);
    }

    public List<PointEvent> events() {
        return events;
    }
}
