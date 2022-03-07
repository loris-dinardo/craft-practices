package infrastructure.adapters;

import domain.ports.GameOutputDisplay;

public class ConsoleGameOutputDisplay implements GameOutputDisplay {
    @Override
    public void print(String dataToDisplay) {
        System.out.println(dataToDisplay);
    }
}
