import domain.TimeProvider;

import java.time.LocalTime;

public class DeterministicTimeProvider implements TimeProvider {
    private final LocalTime fixedTime;

    public DeterministicTimeProvider(String currentTime) {
        fixedTime = LocalTime.parse(currentTime);
    }

    @Override
    public LocalTime currentTime() {
        return fixedTime;
    }
}
