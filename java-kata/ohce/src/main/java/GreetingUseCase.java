import java.time.LocalTime;

public class GreetingUseCase {
    private final DateTimeProvider dateTimeProvider;

    public GreetingUseCase(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    public String execute(String userName) {
        LocalTime currentTime = dateTimeProvider.currentTime();
        if (isMorning(currentTime))
            return "¡Buenos días " + userName + "!";
        if (isAfternoon(currentTime))
            return "¡Buenas tardes " + userName + "!";

        return "¡Buenas noches " + userName + "!";
    }

    private boolean isAfternoon(LocalTime currentTime) {
        return currentTime.isAfter(LocalTime.of(11, 59, 59))
                && currentTime.isBefore(LocalTime.of(20, 0, 0));
    }

    private boolean isMorning(LocalTime currentTime) {
        return currentTime.isAfter(LocalTime.of(5, 59, 59))
                && currentTime.isBefore(LocalTime.of(12, 0, 0));
    }
}
