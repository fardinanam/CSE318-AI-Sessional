package directors;

import builders.SchedulerBuilder;

import java.io.FileNotFoundException;

public class SchedulerDirector implements Director {
    private final SchedulerBuilder schedulerBuilder;

    public SchedulerDirector(SchedulerBuilder schedulerBuilder) {
        this.schedulerBuilder = schedulerBuilder;
    }

    @Override
    public void construct() throws FileNotFoundException {
        schedulerBuilder.setCourseGraph();
        schedulerBuilder.setConstructiveHeuristicFactory();
        schedulerBuilder.setPenaltyCalculatorFactory();
    }
}
