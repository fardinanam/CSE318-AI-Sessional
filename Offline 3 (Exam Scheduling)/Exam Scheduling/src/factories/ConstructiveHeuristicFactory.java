package factories;

import coursedata.Course;
import schedule.heuristics.constructive.*;

import java.util.HashSet;

public class ConstructiveHeuristicFactory implements
        HeuristicFactory<ConstructiveHeuristic<Integer, Course>, HashSet<Course>> {
    private final String heuristicName;
    public ConstructiveHeuristicFactory(String heuristicName) {
        this.heuristicName = heuristicName;
    }
    @Override
    public ConstructiveHeuristic<Integer, Course> createHeuristic(HashSet<Course> courses) {
        switch (heuristicName) {
        case "SaturationDegree":
            return new SaturationDegree(courses);
        case "LargestDegree":
            return new LargestDegree(courses);
        case "RandomOrdering":
            return new RandomOrdering(courses);
        case "LargestEnrollment":
            return new LargestEnrollment(courses);
        default:
            throw new IllegalArgumentException("No such heuristic");
        }
    }
}
