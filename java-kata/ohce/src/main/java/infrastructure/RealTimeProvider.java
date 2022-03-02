package infrastructure;

import domain.TimeProvider;

import java.time.LocalTime;

public class RealTimeProvider implements TimeProvider {
    @Override
    public LocalTime currentTime() {
        return LocalTime.now();
    }
}
