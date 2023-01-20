package builders;

import directors.CourseGraphDirector;
import factories.ConstructiveHeuristicFactory;
import factories.PenaltyCalculatorFactory;
import factories.PerturbativeHeuristicFactory;
import schedule.util.Scheduler;

import java.io.FileNotFoundException;

public class SchedulerBuilder implements Builder<Scheduler> {
    private final String inputFileName;
    private final int constructiveHeuristicNo;
    private final int noOfIterations;
    private CourseGraphBuilder courseGraphBuilder;
    private final Scheduler scheduler;
    private final int penaltyCalculatorNo;

    public SchedulerBuilder(String inputFileName, int constructiveHeuristicNo, int noOfIterations, int penaltyCalculatorNo) {
        this.inputFileName = "dataset/" + inputFileName;
        this.constructiveHeuristicNo = constructiveHeuristicNo;
        this.noOfIterations = noOfIterations;
        this.penaltyCalculatorNo = penaltyCalculatorNo;
        scheduler = new Scheduler();
    }

    public void setConstructiveHeuristicFactory() {
        ConstructiveHeuristicFactory heuristicFactory = new ConstructiveHeuristicFactory(constructiveHeuristicNo);
        scheduler.setConstructiveHeuristicFactory(heuristicFactory);
    }

//    public void setPerturbativeHeuristicFactory() {
//        PerturbativeHeuristicFactory heuristicFactory = new PerturbativeHeuristicFactory(perturbativeHeuristicNo, noOfIterations);
//        scheduler.setPerturbativeHeuristicFactory(heuristicFactory);
//    }
    public void setNoOfIterations() {
        scheduler.setNoOfIterations(noOfIterations);
    }

    public void setCourseGraph() throws FileNotFoundException {
        courseGraphBuilder = new CourseGraphBuilder(inputFileName);
        CourseGraphDirector courseGraphDirector = new CourseGraphDirector(courseGraphBuilder);
        courseGraphDirector.construct();
        scheduler.setGraph(courseGraphBuilder.getBuilt());
    }

    public void setPenaltyCalculatorFactory() {
        PenaltyCalculatorFactory penaltyCalculatorFactory = new PenaltyCalculatorFactory(penaltyCalculatorNo);
        scheduler.setPenaltyCalculatorFactory(penaltyCalculatorFactory);
    }

    @Override
    public Scheduler getBuilt() {
        return scheduler;
    }
}
