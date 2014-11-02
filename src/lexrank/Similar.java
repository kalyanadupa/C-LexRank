package lexrank;



public interface Similar<T> {
    public double similarity(T other);
}