import application.Player;
import application.PlayerFactory;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PlayerFactory playerFactory = PlayerFactory.getDefault();
        System.out.println("--------------------------");
        System.out.println("Welcome to Ping Pong game");
        System.out.println("--------------------------");
        Scanner in = new Scanner(System.in);
        System.out.println("Enter Player One name");
        Player playerOne = playerFactory.createPlayerNamed(in.nextLine());
        System.out.println("Enter Player Two name");
        Player playerTwo = playerFactory.createPlayerNamed(in.nextLine());
        System.out.println("--------------------------");
        System.out.println("The game is about to start");
        System.out.println("--------------------------");
        playerOne.servesForPointAgainstOpponent("Point_1", playerTwo);
        System.out.println("--------------------------");
        System.out.println("The game is finished! Thanks for watching");
    }
}
