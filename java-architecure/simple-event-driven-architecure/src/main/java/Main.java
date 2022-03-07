import application.Player;
import application.PlayerFactory;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Ping Pong game : Player One vs Player Two");
        PlayerFactory playerFactory = PlayerFactory.getDefault();
        Player playerOne = playerFactory.createPlayerNamed("Player One");
        Player playerTwo = playerFactory.createPlayerNamed("Player Two");

        playerOne.servesForPointAgainstOpponent("Point_1", playerTwo);

        /*
        Scanner in = new Scanner(System.in);
        while (true) {
            String command = in.nextLine();
            if (command.equals("Stop!"))
                break;
        }
        in.close();
         */
        System.out.println("The game is finished! Thanks for watching");
    }
}
