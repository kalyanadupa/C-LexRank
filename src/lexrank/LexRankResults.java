package lexrank;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.List;
import java.util.Map;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Kalyan
 */
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