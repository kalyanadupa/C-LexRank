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
public class DummyItem implements Similar<DummyItem> {
    int id;
    double[][] similarityMatrix;
    public DummyItem(int id, double[][] similarityMatrix) {
        this.id = id;
        this.similarityMatrix = similarityMatrix;
    }
    public double similarity(DummyItem other) {
        return similarityMatrix[id][other.id];
    }
}