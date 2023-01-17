package graph;

public interface Graph <T, T1 extends Node<T, T1>> {
    public void addNode(T label);
    public void addEdge(T label1, T label2);
    public T1 getNode(T label);
}
