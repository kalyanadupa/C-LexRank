package lexrank;


import java.util.List;
import java.util.Map;

import java.util.ArrayList;
import java.util.HashMap;

/** A dumb container class that holds results from the LexRank algorithm. */
public class LexRankResults<T> {
    
    public List<T> rankedResults;
    public Map<T, Double> scores;
    public Map<T, List<T>> neighbors;

    public LexRankResults() {
        rankedResults = new ArrayList<T>();
        scores = new HashMap<T, Double>();
        neighbors = new HashMap<T, List<T>>();
    }

}