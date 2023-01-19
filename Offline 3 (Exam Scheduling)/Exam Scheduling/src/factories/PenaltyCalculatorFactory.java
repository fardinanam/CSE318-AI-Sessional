package factories;

import coursedata.CourseDependencyGraph;
import schedule.penaltycalculators.ExponentialPenaltyCalculator;
import schedule.penaltycalculators.LinearPenaltyCalculator;
import schedule.penaltycalculators.PenaltyCalculator;

public class PenaltyCalculatorFactory {
    private String penaltyCalculatorName;
    public PenaltyCalculatorFactory(String penaltyCalculatorName) {
        this.penaltyCalculatorName = penaltyCalculatorName;
    }

    public PenaltyCalculator<Double> getPenaltyCalculator(CourseDependencyGraph graph) {
        if (penaltyCalculatorName == null) {
            return null;
        }

        switch (penaltyCalculatorName) {
            case "linear":
                return new LinearPenaltyCalculator(graph);
            case "exponential":
                return new ExponentialPenaltyCalculator(graph);
            default:
                throw new IllegalArgumentException("Invalid penalty calculator name");
        }
    }
}
