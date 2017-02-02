package game;

import game.exceptions.WrongCommandException;

import java.io.*;
import java.util.*;

public class GameEngine {
    private String FILENAME = "lines.txt";
    private boolean debug = false;
    // All saved previous losses
    private LoosingCombinations losses;
    // Game board
    private Board board;
    //Keeps track of the current game combination
    private Combination currentCombination;

    private Random rand = new Random();
    private BufferedWriter bw = null;

    public GameEngine(boolean debug) {
        this.debug = debug;
    }

    public GameEngine(boolean debug, String fileName) {
        this.debug = debug;
        this.FILENAME = fileName;
    }

    public GameEngine() {

    }

    public void init(){
        loadLossesFromFile();
        board = new Board();
        currentCombination = new Combination();
    }

    public void newGame(){
        board.clear();
        currentCombination = new Combination();
    }

    public String showBoard() {
        return board.toString();
    }

    public Result applyCommand(String command) throws WrongCommandException {
        int move = parse(command);
        if(!board.canApply(move))
            throw new WrongCommandException("Cell is already taken");

        // User plays with X
        board.apply(move, Board.State.X);

        //If user wins the game
        if(board.isWin(Board.State.X)){
            return Result.PLAYER_WIN;
        }
        currentCombination = currentCombination.add(move);

        //Get possible next moves for computer
        Set<Integer> nextMoves = board.getEmptyCells();

        //Board is full, draw
        if(nextMoves.isEmpty()) {
            return Result.DRAW;
        }

        //Now we need to remove cells that lead to losing
        nextMoves.removeAll(losses.getLosingCells(currentCombination));

        // There is no possible combinations for computer, player wins
        if(nextMoves.isEmpty()){
            return Result.NO_WIN;
        }

        debug("Possible next moves for AI:"+nextMoves);
        //Pick random cell for computer move
        int number = rand.nextInt(nextMoves.size());

        // Iterate through set to n-th element
        Iterator<Integer> iterator = nextMoves.iterator();
        for(int i=0;i<number;i++) iterator.next();
        int aiMove = iterator.next();

        debug("AI random pick:"+aiMove);
        // AI plays with aiMove
        board.apply(aiMove, Board.State.O);

        currentCombination = currentCombination.add(aiMove);
        //If AI wins the game
        if(board.isWin(Board.State.O)){
            return Result.COMPUTER_WIN;
        }

        // Keep playing
        return Result.NONE;
    }


    public void saveCombination() {
        FileWriter fw = null;

        try {

            File file = new File(FILENAME);
            if (!file.exists()) {
                file.createNewFile();
            }
            // true = append file
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            save(currentCombination);
            //Also save all the 90, 180 and 270 turns of this combination
            Combination turn90 = currentCombination.turn90();
            save(turn90);
            turn90 = turn90.turn90();
            save(turn90);
            turn90 = turn90.turn90();
            save(turn90);
        } catch (IOException e) {

        } finally {

            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {

            }
        }
    }


    private int parse(String command) throws WrongCommandException {
        String[] move = command.split(",");
        if(move.length!=2) {
            throw new WrongCommandException("Wrong command");
        }
        int row = 0;
        int col = 0;
        try {
            row = Integer.parseInt(move[0]);
            col = Integer.parseInt(move[1]);
        } catch (NumberFormatException e) {
            throw new WrongCommandException("Only 1-3 values allowed");
        }
        if(row<1||row>3||col<1||col>3){
            throw new WrongCommandException("Only 1-3 values allowed");
        }
        return Board.transform(row,col);
    }

    private void loadLossesFromFile() {
        losses = new LoosingCombinations();

        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] moves = line.split("-");
                List<Integer> combinationMoves = new LinkedList<>();
                for(String move : moves){
                    int row = Integer.parseInt(move.substring(1,2));
                    int col = Integer.parseInt(move.substring(3,4));
                    combinationMoves.add(Board.transform(row,col));
                }
                Combination combination = new Combination(combinationMoves);
                losses.add(combination);
            }

        } catch (IOException e) {

        }



    }

    private void save(Combination combination) throws IOException {
        losses.add(combination);
        bw.write(combination.toFileSave());
        bw.newLine();
    }

    private void debug(String s) {
        if(debug) {
            System.out.println(s);
        }
    }

}
