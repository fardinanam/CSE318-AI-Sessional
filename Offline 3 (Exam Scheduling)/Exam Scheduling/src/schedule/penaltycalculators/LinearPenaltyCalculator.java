package schedule.penaltycalculators;

import coursedata.CourseDependencyGraph;

public class LinearPenaltyCalculator extends BasePenaltyCalculator {

    public LinearPenaltyCalculator(CourseDependencyGraph graph) {
        super(graph);
    }

    @Override
    protected int penalty(int timeSlot1, int timeSlot2) {
        int gap = Math.abs(timeSlot1 - timeSlot2);

        if (gap <= 5)
            return 2 * (5 - gap);
        return 0;
    }
}
