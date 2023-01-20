package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class DFS<T1 extends Node<Integer>, T2 extends Graph<Integer, T1>>{
    private final T2 graph;
    private final HashMap<Integer, Boolean> visited;

    public DFS(T2 graph) {
        this.graph = graph;
        visited = new HashMap<>();
    }

    private void dfsVisit(T1 node, HashSet<T1> treeNodes) {
        visited.put(node.getLabel(), true);
        treeNodes.add(node);

        ArrayList<T1> neighbors = node.getNeighbors();
        for (int i = 0; i < neighbors.size(); i++) {
            T1 neighbor = neighbors.get(i);
            if (!visited.get(neighbor.getLabel())) {
                dfsVisit(neighbor, treeNodes);
            }
        }
    }
    public HashSet<T1> getTreeNodes(int rootLabel) {
        HashSet<T1> treeNodes = new HashSet<>();
        dfsVisit(graph.getNode(rootLabel), treeNodes);
        return treeNodes;
    }
}
