public class EchoUseCase {
    public String execute(String worldSentByUser) {
        return new StringBuilder(worldSentByUser).reverse().toString();
    }
}
