import java.time.LocalTime;

public class DeterministicDateTimeProvider implements DateTimeProvider {
    private final LocalTime fixedTime;

    public DeterministicDateTimeProvider(String currentTime) {
        fixedTime = LocalTime.parse(currentTime);
    }

    @Override
    public LocalTime currentTime() {
        return fixedTime;
    }
}
