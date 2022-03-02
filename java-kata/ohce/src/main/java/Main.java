import domain.EchoUseCase;
import domain.GreetingUseCase;
import domain.StopApplicationUseCase;
import domain.WonderfulWordDetectorUseCase;
import infrastructure.RealTimeProvider;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String userName = args[0];
        System.out.println(new GreetingUseCase(new RealTimeProvider()).execute(userName));

        EchoUseCase echoUseCase = new EchoUseCase();
        WonderfulWordDetectorUseCase wonderfulWordDetectorUseCase = new WonderfulWordDetectorUseCase();
        StopApplicationUseCase stopApplicationUseCase = new StopApplicationUseCase();

        Scanner in = new Scanner(System.in);
        while (true) {
            String command = in.nextLine();

            if (stopApplicationUseCase.execute(command))
                break;

            System.out.println(echoUseCase.execute(command));

            String congratulations = wonderfulWordDetectorUseCase.execute(command);
            if (!congratulations.isEmpty())
                System.out.println(congratulations);
        }
        in.close();
        System.out.println("Adios " + userName);
    }
}
