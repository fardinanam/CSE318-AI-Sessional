package factories;

import coursedata.Course;
import schedule.heuristics.constructive.*;

import java.util.HashSet;

public class ConstructiveHeuristicFactory implements
        HeuristicFactory<ConstructiveHeuristic<Integer, Course>, HashSet<Course>> {
    private final int heuristicNo;
    public ConstructiveHeuristicFactory(int heuristicNo) {
        this.heuristicNo = heuristicNo;
    }
    @Override
    public ConstructiveHeuristic<Integer, Course> createHeuristic(HashSet<Course> courses) {
        switch (heuristicNo) {
        case 1:
            return new SaturationDegree(courses);
        case 2:
            return new LargestDegree(courses);
        case 3:
            return new LargestEnrollment(courses);
        case 4:
            return new RandomOrdering(courses);
        default:
            throw new IllegalArgumentException("No such constructive heuristic");
        }
    }
}
