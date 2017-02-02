package game;

import java.util.Arrays;
import java.util.List;

/**
 * Representation of a specific game moves on a board with numbered cells
 *
 * 1 2 3
 * 4 5 6
 * 7 8 9
 *
 * For example [5,6,1,9,7,8]
 *
 * X
 *   X O
 * X O O
 *
 */
public class Combination implements Comparable<Combination> {
    private int size = 0;
    private int[] moves = {0,0,0,0,0,0,0,0,0};

    public Combination() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Combination that = (Combination) o;

        return Arrays.equals(moves, that.moves);

    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(moves);
    }


    public int compareTo(Combination that) {
        for(int i=0;i<9;i++){
            if(moves[i]!=that.moves[i]) {
                return moves[i]-that.moves[i];
            }
        }
        return 0;
    }

    public Combination(List<Integer> moves) {
        int i=0;
        for(int move : moves){
            this.moves[i] = move;
            i++;
        }
        size = i;
    }

    public Combination add(int move) {
        Combination result = new Combination();
        //Copy
        for(int i=0;i<size;i++){
            result.moves[i] = this.moves[i];
        }
        //Add the last one
        result.moves[size] = move;
        result.size = this.size+1;
        return result;
    }

    public int size() {
        return size;
    }

    public Integer lastMove() {
        return moves[size-1];
    }

    /**
     * Turns move coordinate 90 degrees counterclockwise:
     *
     * 1 2 3      3 6 9
     * 4 5 6  ->  2 5 8
     * 7 8 9      1 4 7
     *
     *
     * @return
     */
    public Combination turn90() {
        Combination result = new Combination();
        for(int i=0;i<size;i++){
            result.moves[i] = turnMove(this.moves[i]);
        }
        result.size = this.size;
        return result;

    }


    private int turnMove(int move){
        if(move==1) return 3;
        if(move==2) return 6;
        if(move==3) return 9;
        if(move==4) return 2;
        if(move==5) return 5;
        if(move==6) return 8;
        if(move==7) return 1;
        if(move==8) return 4;
        if(move==9) return 7;
        return 0;
    }

    @Override
    public String toString() {
        return "Combination{" +
                "moves=" + Arrays.toString(moves) +
                '}';
    }

    public String toFileSave() {
        StringBuilder result = new StringBuilder();
        for(int i=0;i<size;i++){
            int row = (moves[i]+2)/3;
            int col = (moves[i]+2)%3+1;
            result.append('(').append(row).append(',').append(col).append(')');
            if(i<(size-1)) result.append('-');
        }

        return result.toString();
    }

    public static void main(String[] args) {
        int[] moves = {1,2,3,4,5,6,7,8,9};
        for(int i=0;i<moves.length;i++) {
            int row = (moves[i] + 2) / 3;
            int col = (moves[i] +2 ) % 3 +1;
            System.out.println(" move: "+moves[i]+" ("+row+","+col+")");
        }
    }
}

