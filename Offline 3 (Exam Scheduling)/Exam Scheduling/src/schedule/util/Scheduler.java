package schedule.util;

import coursedata.Course;
import coursedata.CourseDependencyGraph;
import factories.ConstructiveHeuristicFactory;
import factories.PenaltyCalculatorFactory;
import logs.Log;
import schedule.heuristics.constructive.ConstructiveHeuristic;

public class Scheduler {
    private CourseDependencyGraph graph;
    private ConstructiveHeuristicFactory constructiveHeuristicFactory;
    private PenaltyCalculatorFactory penaltyCalculatorFactory;
    public void setGraph(CourseDependencyGraph graph) {
        this.graph = graph;
    }

    public void setConstructiveHeuristicFactory(ConstructiveHeuristicFactory constructiveHeuristicFactory) {
        this.constructiveHeuristicFactory = constructiveHeuristicFactory;
    }

    public void setPenaltyCalculatorFactory(PenaltyCalculatorFactory penaltyCalculatorFactory) {
        this.penaltyCalculatorFactory = penaltyCalculatorFactory;
    }

    /**
     * Assigns the lowest possible time slot to the course
     * that is not already occupied by a neighbor.
     * @param courseId the course id of the course
     *                to be assigned a time slot to
     */
    public void assignCourseTimeSlot(int courseId) {
        Course course = graph.getNode(courseId);

        boolean[] occupiedTimeSlots = new boolean[graph.getNoOfCourses()];
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

    public double getPenaltyAvg() {
        if (penaltyCalculatorFactory == null) {
            Log.log("Penalty calculator not set");
            return 0;
        }
        return penaltyCalculatorFactory.getPenaltyCalculator(graph).calculatePenaltyAvg();
    }

    public CourseDependencyGraph getGraph() {
        return graph;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(graph.toString()).append("\n");
        sb.append("Average penalty: ").append(getPenaltyAvg());

        return sb.toString();
    }
}
