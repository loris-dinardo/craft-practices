package v1;

import java.util.ArrayList;
import java.util.Collections;

public class InMemoryLogGateway implements LogAccountGateway {
    private final ArrayList<String> logDb;
    private final String date;

    public InMemoryLogGateway(String date) {
        this.logDb = new ArrayList<>();
        this.date = date;
    }

    @Override
    public void log(Integer amount, Integer balance) {
        this.logDb.add(date + " | " + amount + " | " + balance);
    }

    @Override
    public String getLastLog() {
        return this.logDb.isEmpty() ?
                ""
                : this.logDb.get(this.logDb.size() - 1);
    }

    @Override
    public String getAllLogs() {
        Collections.reverse(this.logDb);
        return String.join("\n", logDb);
    }
}
