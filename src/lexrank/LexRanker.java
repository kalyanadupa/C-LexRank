/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package lexrank;

/**
 *
 * @author Kalyan
 */
import java.util.Collections;
import java.util.List;

import java.util.ArrayList;

public class LexRanker {

    public static <T extends Similar<T>> LexRankResults<T>
                             rank(List<T> data,
                                  double similarityThreshold,
                                  boolean continuous) {
        LexRankResults<T> results = new LexRankResults<T>();
        if (data.size() == 0) {
            return results;
        }
        double[][] similarities = similarityMatrix(data);
        double[][] transitionProbabilities =
            transitionProbabilities(similarities,
                                    similarityThreshold,
                                    continuous);


        for (int i = 0; i < data.size(); ++i) {
            for (int j = 0; j < data.size(); ++j) {
                if (transitionProbabilities[i][j] > 0) {
                    List<T> neighborList = results.neighbors.get(data.get(i));
                    if (neighborList == null) {
                        neighborList = new ArrayList<T>();
                    }
                    neighborList.add(data.get(j));
                    results.neighbors.put(data.get(i), neighborList);
                }
            }
        }

        double[] rankings = powerIteration(transitionProbabilities,
                                           data.size(),
                                           0.001,
                                           100);


        List<RankPair<T>> tempList = new ArrayList<RankPair<T>>();
        for (int i = 0; i < data.size(); ++i) {
            results.scores.put(data.get(i), rankings[i]);
            tempList.add(new RankPair<T>(data.get(i), rankings[i]));
        }
        Collections.sort(tempList);
        Collections.reverse(tempList);
        for (RankPair<T> pair: tempList) {
            results.rankedResults.add(pair.data);
        }
        return results;
    }


    private static class RankPair<T> implements Comparable<RankPair<T>> {
        T data;
        double score;
        public RankPair(T d, double s) {
            data = d;
            score = s;
        }
        public int compareTo(RankPair<T> other) {
            double diff = score - other.score;
            if (diff > 0.000001) {
                return 1;
            } else if (diff < -0.000001) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    private static <T extends Similar<T>> double[][]
                              similarityMatrix(List<T> data) {
        double[][] results = new double[data.size()][data.size()];
        for (int i = 0; i < data.size(); ++i) {
            for (int j = 0; j <= i; ++j) {
                results[i][j] = results[j][i] =
                    data.get(i).similarity(data.get(j));
            }
        }
        return results;
    }

    private static double[][]
        transitionProbabilities(double[][] similarities,
                                double similarityThreshold,
                                boolean continuous) {
        double[][] probabilities =
            new double[similarities.length][similarities[0].length];
        for (int i = 0; i < similarities.length; ++i) {
            double sum = 0;
            for (int j = 0; j < similarities[i].length; ++j) {
                if (similarities[i][j] > similarityThreshold) {
                    if (continuous) {
                        probabilities[i][j] = similarities[i][j];
                        sum += similarities[i][j];
                    } else {
                        probabilities[i][j] = 1;
                        sum += 1;
                    }
                } else {
                    probabilities[i][j] = 0;
                }
            }
            for (int j = 0; j < similarities[i].length; ++j) {
                probabilities[i][j] /= sum;
            }
        }
        return probabilities;
    }

    /** Multiplies two matrices. So Exciting!*/
    private static double[][] multMatrix(double[][] first,
                                         double[][] second) {
        if (first.length == 0 || second.length == 0) {
            return null;
        }
        if (first[0].length != second.length) {
            return null;
        }
        double[][] result = new double[first.length][second[0].length];
        for (int i = 0; i < first.length; ++i) {
            for (int j = 0; j < second[0].length; ++j) {
                double sum = 0;
                for (int k = 0; k < second.length; ++k) {
                    sum += first[i][k]*second[k][j];
                }
                result[i][j] = sum;
            }
        }
        return result;
    }

    /** Transposes a matrix. This is really complicated stuff. */
    private static double[][] transposeMatrix(double[][] matrix) {
        if (matrix.length == 0) {
            return null;
        }
        double[][] result = new double[matrix[0].length][matrix.length];
        for (int i = 0; i < result.length; ++i) {
            for (int j = 0; j < result[i].length; ++j) {
                result[i][j] = matrix[j][i];
            }
        }
        return result;
    }

    private static double[] powerIteration(double[][] stochasticMatrix,
                                           int size,
                                           double epsilon,
                                           int maxIterations) {
        double[][] currentMatrix = transposeMatrix(stochasticMatrix);
        double[][] currentVector = new double[size][1];
        double[][] previousVector;
        for (int i = 0; i < size; ++i) {
            currentVector[i][0] = 1.0 / size;
        }
        for (int i = 0; i < maxIterations; ++i) {
            previousVector = currentVector;
            currentVector = multMatrix(currentMatrix, currentVector);
            double error = 0;
            for (int j = 0; j < size; ++j) {
                error += Math.pow(currentVector[j][0]-previousVector[j][0], 2);
            }
            if (error < Math.pow(epsilon, 2)) {
                break;
            }
        }
        double[] result = new double[size];
        for (int i = 0; i < size; ++i) {
            result[i] = currentVector[i][0];
        }
        return result;
    }
}