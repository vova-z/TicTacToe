package game;

import java.util.*;

/**
 * Created by Vova on 2/1/2017.
 */
public class LoosingCombinations {
    private SortedSet<Combination> combinations = new TreeSet<Combination>();

    public void add(Combination lossCombination) {
        combinations.add(lossCombination);
    }

    public Set<Integer> getLosingCells(Combination currentCombination) {
        Set<Integer> result = new HashSet();
        Set<Combination> losses = combinations.subSet(currentCombination.add(1),currentCombination.add(10));
        for(Combination loss : losses){
            if(loss.size()-1==currentCombination.size()) {
                //Collect only those with the next losing move
                result.add(loss.lastMove());
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "LoosingCombinations{" +
                "combinations=" + combinations +
                '}';
    }
}
