public class StopApplicationUseCase {
    public boolean execute(String commandSent) {
        return commandSent.equals("Stop!");
    }
}
