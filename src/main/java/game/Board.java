package game;


import java.util.Set;
import java.util.TreeSet;

/**
 * Data structure that represents the state of the game
 *
 * Internal array represents each cell with a following index
 *      0 1 2
 *      3 4 5
 *      6 7 8
 *
 * So in order to convert given row (1-3) and cell (1-3)
 * we need to do the following transformation:
 *
 * index = 3 x row + column - 4
 *
 */
public class Board {


    public Set<Integer> getEmptyCells() {
        Set<Integer> result = new TreeSet();
        for(int i=0;i<9;i++){
            if(cells[i]==State.EMPTY) result.add(i+1);
        }
        return result;
    }

    public enum State {EMPTY, X, O};

    private State[] cells = new State[9];

    public static int transform(int row, int col){
        return 3*row+col-3;
    }

    public Board() {
        clear();
    }

    public void clear() {
        for(int i=0;i<9;i++){
            cells[i] = State.EMPTY;
        }
    }

    public boolean canApply(int move){
        return cells[move-1] == State.EMPTY;
    }

    public void apply(int move, State state){
        cells[move-1] = state;
    }

    public boolean isWin(State type) {
        //Rows
        if(cells[0]==type && cells[1]==type && cells[2]==type) return true;
        if(cells[3]==type && cells[4]==type && cells[5]==type) return true;
        if(cells[6]==type && cells[7]==type && cells[8]==type) return true;
        //Columns
        if(cells[0]==type && cells[3]==type && cells[6]==type) return true;
        if(cells[1]==type && cells[4]==type && cells[7]==type) return true;
        if(cells[2]==type && cells[5]==type && cells[8]==type) return true;
        //Diagonals
        if(cells[0]==type && cells[4]==type && cells[8]==type) return true;
        if(cells[2]==type && cells[4]==type && cells[6]==type) return true;

        return false;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("-------------\n");
        for(int i=1;i<4;i++){
            result.append("|");
                for(int j=1;j<4;j++){
                    switch ( cells[transform(i,j)-1]){

                        case EMPTY:
                        result.append("   |");
                            break;
                        case X:
                            result.append(" X |");
                            break;
                        case O:
                            result.append(" O |");
                            break;
                    }
                }
            result.append("\n-------------\n");
        }

        return result.toString();
    }
}
