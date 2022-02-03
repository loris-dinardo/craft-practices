import java.util.ArrayList;

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
}
