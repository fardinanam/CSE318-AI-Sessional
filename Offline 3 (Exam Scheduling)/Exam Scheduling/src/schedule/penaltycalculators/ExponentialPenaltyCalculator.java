package schedule.penaltycalculators;

import coursedata.CourseDependencyGraph;
public class ExponentialPenaltyCalculator extends BasePenaltyCalculator {
    public ExponentialPenaltyCalculator(CourseDependencyGraph graph) {
        super(graph);
    }

    @Override
    protected int penalty(int timeSlot1, int timeSlot2) {
        int gap = Math.abs(timeSlot1 - timeSlot2);

        if (gap <= 5)
            return (int)Math.pow(2, 5 - gap);
        return 0;
    }
}
