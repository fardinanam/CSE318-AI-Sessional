package schedule.heuristics.perturbative;

import coursedata.Course;
import coursedata.CourseDependencyGraph;
import logs.Log;
import schedule.penaltycalculators.PenaltyCalculator;

import java.util.ArrayList;
import java.util.Collections;

public class PairSwapOperator implements PerturbativeHeuristic<CourseDependencyGraph> {
    private final CourseDependencyGraph graph;
    private final int maxNoOfIterations;
    public PairSwapOperator(CourseDependencyGraph graph, int maxNoOfIterations) {
        this.graph = graph;
        this.maxNoOfIterations = maxNoOfIterations;
    }

    private boolean isDisjointWithTimeSlot(Course course, int timeSlot) {
        for (Course neighbor : course.getNeighbors()) {
            if (neighbor.getTimeSlot() == timeSlot) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void reducePenalty(PenaltyCalculator<Double> penaltyCalculator) {
        double currentPenalty = penaltyCalculator.calculatePenaltyAvg();
        ArrayList<Course> courses = new ArrayList<>(graph.getCourses());
        int totalCourses = courses.size();
        int noOfIterations = 0;

        // randomize the order of the courses
        // apply pair swap on every possible pair of courses
        Collections.shuffle(courses);
        for (int course1Index = 0; course1Index < totalCourses; course1Index++) {
            for (int course2Index = 0; course2Index < totalCourses; course2Index++) {
                if (course1Index == course2Index) {
                    continue;
                }

                Course course1 = courses.get(course1Index);
                Course course2 = courses.get(course2Index);
                int timeSlot1 = course1.getTimeSlot();
                int timeSlot2 = course2.getTimeSlot();
                if (timeSlot1 == timeSlot2) {
                    continue;
                }

                noOfIterations++;

                // if the pair of courses are disjoint with the time slots they are assigned to
                // swap their time slots
                if (isDisjointWithTimeSlot(course1, timeSlot2) && isDisjointWithTimeSlot(course2, timeSlot1)) {
                    course1.setTimeSlot(timeSlot2);
                    course2.setTimeSlot(timeSlot1);

                    double newPenalty = penaltyCalculator.calculatePenaltyAvg();
                    if (newPenalty < currentPenalty) {
                        currentPenalty = newPenalty;
                    } else {
                        course1.setTimeSlot(timeSlot1);
                        course2.setTimeSlot(timeSlot2);
                    }
                }
            }
        }

        Log.log("Total pair swap iterations: " + noOfIterations);
    }
}
