package schedule.heuristics.perturbative;

import coursedata.Course;
import coursedata.CourseDependencyGraph;
import logs.Log;
import schedule.penaltycalculators.PenaltyCalculator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class KempeChainInterchange implements PerturbativeHeuristic<CourseDependencyGraph> {
    private final CourseDependencyGraph graph;
    private final int maxNoOfIterations;
    public KempeChainInterchange(CourseDependencyGraph graph, int maxNoOfIterations) {
        this.graph = graph;
        this.maxNoOfIterations = maxNoOfIterations;
    }

    /**
     * Swaps the time slots of the courses in the chain when a course is assigned one of the
     * two time slots passed.
     * @param timeSlot1 the first time slot
     * @param timeSlot2 the second time slot
     * @param chain the chain of courses of which, time slots are to be swapped
     */
    public void swapTimeSlots(int timeSlot1, int timeSlot2, List<Course> chain) {
        for (Course course : chain) {
            if (course.getTimeSlot() == timeSlot1) {
                course.setTimeSlot(timeSlot2);
            } else if (course.getTimeSlot() == timeSlot2) {
                course.setTimeSlot(timeSlot1);
            }
        }
    }

    /**
     * Fills the chain of courses with the courses in the graph that are connected to the
     * course passed and only contains courses that are assigned one of the two time slots.
     * @param course the course from which the chain is to be built
     * @param timeSlot1 the first time slot
     * @param timeSlot2 the second time slot
     * @param chain the chain of courses to be filled
     * @param visited the set of courses that have been visited while building the chain
     */
    void getKempeChain(Course course, int timeSlot1, int timeSlot2, List<Course> chain, HashSet<Integer> visited) {
        visited.add(course.getLabel());
        chain.add(course);
        for (Course neighbor : course.getNeighbors()) {
            if (!visited.contains(neighbor.getLabel())
                    && (neighbor.getTimeSlot() == timeSlot1 || neighbor.getTimeSlot() == timeSlot2)) {
                getKempeChain(neighbor, timeSlot1, timeSlot2, chain, visited);
            }
        }
    }

    /**
     * Starting from course1, this method finds a maximally connected graph
     * of courses that are connected to course1 and course2
     * where the chain only contains courses that are assigned to
     * the time slots of these two courses. It then swaps the time slots.
     * @param course1 the course from which the chain starts
     * @param course2 the second course containing the second time slot.
     */
    public void applyKempeChainInterchange(Course course1, Course course2) {
        int timeSlot1 = course1.getTimeSlot();
        int timeSlot2 = course2.getTimeSlot();

        // clear the visited map.
        HashSet<Integer> visited = new HashSet<>();

        // insert course1 to the visited map and in the chain.
        ArrayList<Course> chain = new ArrayList<>();

        // find the full chain of courses that are connected to course1 and course2
        getKempeChain(course2, timeSlot1, timeSlot2, chain, visited);

        // iterate through the chain and swap the time slots.
        swapTimeSlots(timeSlot1, timeSlot2, chain);
    }
    
    @Override
    public void reducePenalty(PenaltyCalculator<Double> penaltyCalculator) {
        double currentPenalty = penaltyCalculator.calculatePenaltyAvg();
        ArrayList<Course> courses = new ArrayList<>(graph.getCourses());

        // randomly select a course from the graph
        // and then iterate over its neighbors
        // for each neighbor, find the kempe-chain and swap the time slots
        // of the courses in the chain
        int noOfIterations = 0;
        while (noOfIterations < maxNoOfIterations) {
            int randomIndex = (int) (Math.random() * courses.size());
            Course course = courses.get(randomIndex);
            ArrayList<Course> neighbors = new ArrayList<>(course.getNeighbors());

            if (neighbors.size() == 0) {
                continue;
            }

            Course neighbor = neighbors.get((int) (Math.random() * neighbors.size()));

            // swap the time slots of the courses in the chain
            // of the two courses. If penalty is no reduced, revert the swap.
            applyKempeChainInterchange(course, neighbor);
            if (currentPenalty > penaltyCalculator.calculatePenaltyAvg()) {
                currentPenalty = penaltyCalculator.calculatePenaltyAvg();
            } else {
                applyKempeChainInterchange(course, neighbor);
            }
            noOfIterations++;
        }

        Log.log("Total kempe chain iterations: " + noOfIterations);
    }
}
