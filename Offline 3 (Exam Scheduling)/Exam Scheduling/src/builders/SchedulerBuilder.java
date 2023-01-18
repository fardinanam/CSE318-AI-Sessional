package builders;

import directors.CourseGraphDirector;
import factories.ConstructiveHeuristicFactory;
import schedule.util.Scheduler;

import java.io.FileNotFoundException;

public class SchedulerBuilder implements Builder<Scheduler> {
    private final String inputFileName;
    private final String constructiveHeuristicName;
    private CourseGraphBuilder courseGraphBuilder;
    private final Scheduler scheduler;

    public SchedulerBuilder(String inputFileName, String constructiveHeuristicName) {
        this.inputFileName = "dataset/" + inputFileName;
        this.constructiveHeuristicName = constructiveHeuristicName;
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

    @Override
    public Scheduler getBuilt() {
        return scheduler;
    }
}
