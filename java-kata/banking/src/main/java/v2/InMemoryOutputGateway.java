package v2;

import java.util.ArrayList;
import java.util.List;

public class InMemoryOutputGateway implements OutputGateway {
    private final List<String> printed = new ArrayList<>();

    @Override
    public void print(String value) {
        this.printed.add(value);
    }

    public String printed() {
        return String.join("\n", printed);
    }

}
