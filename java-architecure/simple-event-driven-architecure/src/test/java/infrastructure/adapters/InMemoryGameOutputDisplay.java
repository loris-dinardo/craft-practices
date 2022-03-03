package infrastructure.adapters;

import domain.ports.GameOutputDisplay;

public class InMemoryGameOutputDisplay implements GameOutputDisplay {
    private String lastPrinted = "";

    @Override
    public void print(String dataToDisplay) {
        lastPrinted = dataToDisplay;
    }

    public String printed() {
        return lastPrinted;
    }
}
