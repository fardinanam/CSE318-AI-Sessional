package schedule.heuristics.perturbative;

import graph.Graph;
import schedule.penaltycalculators.PenaltyCalculator;

public interface PerturbativeHeuristic<T extends Graph> {
    public void reducePenalty(PenaltyCalculator<Double> penaltyCalculator);
}
