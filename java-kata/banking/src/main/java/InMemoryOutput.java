import java.util.ArrayList;
import java.util.List;

public class InMemoryOutput implements Output {
    private final List<String> localConsole = new ArrayList<>();

    @Override
    public void print(String value) {
        this.localConsole.add(value);
    }

    public String getLocalConsoleContent() {
        return String.join("\n", this.localConsole);
    }
}
