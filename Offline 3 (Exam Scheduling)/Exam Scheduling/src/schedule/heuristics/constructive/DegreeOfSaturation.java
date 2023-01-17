package schedule.heuristics.constructive;

import examdata.Course;

import java.util.Collection;
import java.util.Objects;
import java.util.PriorityQueue;

public class DegreeOfSaturation implements ConstructiveHeuristic<Integer, Course> {
    private final PriorityQueue<Course> courseQueue;

    public DegreeOfSaturation(Collection<Course> courses) {

    }

    @Override
    public boolean hasNext() {
        return !courseQueue.isEmpty();
    }

    @Override
    public Integer getNext() {
        return Objects.requireNonNull(courseQueue.poll()).getLabel();
    }
}
