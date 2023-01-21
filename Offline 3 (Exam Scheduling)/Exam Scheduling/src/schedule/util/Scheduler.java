package schedule.util;

import coursedata.Course;
import coursedata.CourseDependencyGraph;
import factories.ConstructiveHeuristicFactory;
import factories.PenaltyCalculatorFactory;
import logs.Log;
import schedule.heuristics.constructive.ConstructiveHeuristic;
import schedule.heuristics.perturbative.KempeChainInterchange;
import schedule.heuristics.perturbative.PairSwapOperator;
import schedule.heuristics.perturbative.PerturbativeHeuristic;
import schedule.penaltycalculators.PenaltyCalculator;

import java.text.DecimalFormat;

public class Scheduler {
    private CourseDependencyGraph graph;
    private ConstructiveHeuristicFactory constructiveHeuristicFactory;
    private PenaltyCalculatorFactory penaltyCalculatorFactory;
    private int noOfIterations; // Number of iterations to run the perturbative heuristics
    public void setGraph(CourseDependencyGraph graph) {
        this.graph = graph;
    }

    public void setConstructiveHeuristicFactory(ConstructiveHeuristicFactory constructiveHeuristicFactory) {
        this.constructiveHeuristicFactory = constructiveHeuristicFactory;
    }

    public void setPenaltyCalculatorFactory(PenaltyCalculatorFactory penaltyCalculatorFactory) {
        this.penaltyCalculatorFactory = penaltyCalculatorFactory;
    }

    public void setNoOfIterations(int noOfIterations) {
        this.noOfIterations = noOfIterations;
    }

    /**
     * Assigns the lowest possible time slot to the course
     * that is not already occupied by a neighbor.
     * @param courseId the course id of the course
     *                to be assigned a time slot to
     */
    public void assignCourseTimeSlot(int courseId) {
        Course course = graph.getNode(courseId);

        boolean[] occupiedTimeSlots = new boolean[graph.getNoOfNodes()];
        for (Course neighbor : course.getNeighbors()) {
            if (neighbor.getTimeSlot() > 0) {
                occupiedTimeSlots[neighbor.getTimeSlot() - 1] = true;
            }
        }

        int timeSlot = 1;
        while (occupiedTimeSlots[timeSlot - 1]) {
            timeSlot++;
        }

        graph.assignCourseTimeSlot(courseId, timeSlot);
    }

    public void schedule() {
        if (graph == null || constructiveHeuristicFactory == null)
            return;

        ConstructiveHeuristic<Integer, Course> heuristic = constructiveHeuristicFactory.createHeuristic(graph.getCourses());
        while (heuristic.hasNext()) {
            int courseId = heuristic.getNext();
            assignCourseTimeSlot(courseId);
        }
    }

    public void reducePenaltyByKempeChain() {
        if (graph == null || penaltyCalculatorFactory == null)
            return;

        PenaltyCalculator<Double> penaltyCalculator = penaltyCalculatorFactory.createPenaltyCalculator(graph);
        PerturbativeHeuristic<CourseDependencyGraph> perturbativeHeuristic = new KempeChainInterchange(graph, noOfIterations);

        // apply kempechain interchange 4 times
        for (int i = 0; i < 4; i++) {
            perturbativeHeuristic.reducePenalty(penaltyCalculator);
        }
    }

    public void reducePenaltyByPairSwap() {
        if (graph == null || penaltyCalculatorFactory == null)
            return;

        PenaltyCalculator<Double> penaltyCalculator = penaltyCalculatorFactory.createPenaltyCalculator(graph);
        PerturbativeHeuristic<CourseDependencyGraph> perturbativeHeuristic = new PairSwapOperator(graph, noOfIterations);
        perturbativeHeuristic.reducePenalty(penaltyCalculator);
    }
    public double getPenaltyAvg() {
        if (penaltyCalculatorFactory == null) {
            Log.log("Penalty calculator not set");
            return 0;
        }
        return penaltyCalculatorFactory.createPenaltyCalculator(graph).calculatePenaltyAvg();
    }

    public boolean isConflictFree() {
        for (Course course : graph.getCourses()) {
            for (Course neighbor : course.getNeighbors()) {
                if (course.getTimeSlot() == neighbor.getTimeSlot()) {
                    return false;
                }
            }
        }

        return true;
    }

    public CourseDependencyGraph getGraph() {
        return graph;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.##");
        StringBuilder sb = new StringBuilder();
        sb.append(graph.toString()).append("\n");
        sb.append("Average penalty: ").append(df.format(getPenaltyAvg()));

        return sb.toString();
    }
}
