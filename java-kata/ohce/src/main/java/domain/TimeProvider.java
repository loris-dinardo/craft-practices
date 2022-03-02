package domain;

import java.time.LocalTime;

public interface TimeProvider {
    LocalTime currentTime();
}
