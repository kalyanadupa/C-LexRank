package lexrank;


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