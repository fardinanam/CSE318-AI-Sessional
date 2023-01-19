package builders;

import directors.CourseGraphDirector;
import factories.ConstructiveHeuristicFactory;
import factories.PenaltyCalculatorFactory;
import schedule.util.Scheduler;

import java.io.FileNotFoundException;

public class SchedulerBuilder implements Builder<Scheduler> {
    private final String inputFileName;
    private final String constructiveHeuristicName;
    private CourseGraphBuilder courseGraphBuilder;
    private final Scheduler scheduler;
    private final String penaltyCalculatorName;

    public SchedulerBuilder(String inputFileName, String constructiveHeuristicName, String penaltyCalculatorName) {
        this.inputFileName = "dataset/" + inputFileName;
        this.constructiveHeuristicName = constructiveHeuristicName;
        this.penaltyCalculatorName = penaltyCalculatorName;
        scheduler = new Scheduler();
    }

    public void setConstructiveHeuristicFactory() {
        ConstructiveHeuristicFactory heuristicFactory = new ConstructiveHeuristicFactory(constructiveHeuristicName);
        scheduler.setConstructiveHeuristicFactory(heuristicFactory);
    }

    public void setCourseGraph() throws FileNotFoundException {
        courseGraphBuilder = new CourseGraphBuilder(inputFileName);
        CourseGraphDirector courseGraphDirector = new CourseGraphDirector(courseGraphBuilder);
        courseGraphDirector.construct();
        scheduler.setGraph(courseGraphBuilder.getBuilt());
    }

    public void setPenaltyCalculatorFactory() {
        PenaltyCalculatorFactory penaltyCalculatorFactory = new PenaltyCalculatorFactory(penaltyCalculatorName);
        scheduler.setPenaltyCalculatorFactory(penaltyCalculatorFactory);
    }

    @Override
    public Scheduler getBuilt() {
        return scheduler;
    }
}
