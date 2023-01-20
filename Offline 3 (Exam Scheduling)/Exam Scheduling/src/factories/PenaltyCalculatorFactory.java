package factories;

import coursedata.CourseDependencyGraph;
import schedule.penaltycalculators.ExponentialPenaltyCalculator;
import schedule.penaltycalculators.LinearPenaltyCalculator;
import schedule.penaltycalculators.PenaltyCalculator;

public class PenaltyCalculatorFactory {
    private int penaltyCalculatorNo;
    public PenaltyCalculatorFactory(int penaltyCalculatorNo) {
        this.penaltyCalculatorNo = penaltyCalculatorNo;
    }

    public PenaltyCalculator<Double> createPenaltyCalculator(CourseDependencyGraph graph) {
        switch (penaltyCalculatorNo) {
            case 1:
                return new LinearPenaltyCalculator(graph);
            case 2:
                return new ExponentialPenaltyCalculator(graph);
            default:
                throw new IllegalArgumentException("Invalid penalty calculator number");
        }
    }
}
