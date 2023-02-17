package factories;

import coursedata.CourseDependencyGraph;
import schedule.heuristics.perturbative.KempeChainInterchange;
import schedule.heuristics.perturbative.PairSwapOperator;
import schedule.heuristics.perturbative.PerturbativeHeuristic;

public class PerturbativeHeuristicFactory implements HeuristicFactory<PerturbativeHeuristic<CourseDependencyGraph>, CourseDependencyGraph> {
    private final int heuristicNo;
    private final int noOfIterations;

    public PerturbativeHeuristicFactory(int heuristicNo, int noOfIterations) {
        this.heuristicNo = heuristicNo;
        this.noOfIterations = noOfIterations;
    }

    @Override
    public PerturbativeHeuristic<CourseDependencyGraph> createHeuristic(CourseDependencyGraph graph) {
        switch (heuristicNo) {
        case 1:
            return new KempeChainInterchange(graph, noOfIterations);
        case 2:
            return new PairSwapOperator(graph, noOfIterations);
        default:
            throw new IllegalArgumentException("No such perturbative heuristic");
        }
    }

}
