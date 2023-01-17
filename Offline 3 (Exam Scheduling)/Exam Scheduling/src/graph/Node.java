package graph;

import java.util.Collection;

public interface Node <T, T1 extends Node<T, T1>> {
    public T getLabel();
    public <T2 extends Collection<T1>> T2 getNeighbors();
    public void addNeighbor(T1 node);
}
