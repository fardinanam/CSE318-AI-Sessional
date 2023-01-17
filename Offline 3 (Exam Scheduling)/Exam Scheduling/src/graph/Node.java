package graph;

import java.util.Collection;

public interface Node <T> {
    public T getLabel();
    public <T2 extends Collection<? extends Node<T>>> T2 getNeighbors();
    public void addNeighbor(Node node);
}
