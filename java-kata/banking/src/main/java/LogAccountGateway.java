public interface LogAccountGateway {
    void log(Integer amount, Integer balance);
    String getLastLog();
}
