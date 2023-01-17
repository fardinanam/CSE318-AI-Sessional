package schedule.heuristics.constructive;

import graph.Node;

public interface ConstructiveHeuristic <T, T1 extends Node<T>> {
    public boolean hasNext();
    public T getNext();
}
