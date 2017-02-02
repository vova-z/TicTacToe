import game.*;
import game.exceptions.WrongCommandException;

import java.util.Scanner;

public class TicTacToe {



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Tic Tac Toe!");

        GameEngine engine;
        if(args.length == 1){
            engine = new GameEngine(Boolean.parseBoolean(args[0]));
        } else if(args.length == 2) {
            engine = new GameEngine(Boolean.parseBoolean(args[0]),args[1]);
        } else {
            engine = new GameEngine();
        }
        engine.init();
        System.out.println(engine.showBoard());
        while(true) {
            System.out.print(">");
            String command = scanner.nextLine();

            if("e".equals(command)){
                // 'e' is an exit command
                break;
            }

            try {
                Result result = engine.applyCommand(command);
                switch (result){
                    case NONE:
                        break;
                    case PLAYER_WIN:
                        System.out.println("Player wins!");
                        engine.saveCombination();
                        startNewGame(engine);
                        break;
                    case NO_WIN:
                        System.out.println("No winning move for computer, player wins!");
                        startNewGame(engine);
                        break;
                    case COMPUTER_WIN:
                        System.out.println("Computer wins!");
                        startNewGame(engine);
                        break;
                    case DRAW:
                        System.out.println("Draw!");
                        startNewGame(engine);
                        break;
                }
            } catch (WrongCommandException e) {
                System.out.println(e.getMessage());
            }
            System.out.println(engine.showBoard());

        }
    }

    private static void startNewGame(GameEngine engine) {
        System.out.println(engine.showBoard());
        System.out.println("=========== New game =============");
        engine.newGame();
    }

}
