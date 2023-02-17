package schedule.heuristics.constructive;

import coursedata.Course;

import java.util.Collection;
import java.util.Objects;
import java.util.PriorityQueue;

public class LargestDegree implements ConstructiveHeuristic<Integer, Course> {
    private final PriorityQueue<Course> courseQueue;

    public LargestDegree(Collection<Course> courses) {
        // create a max priority queue
        this.courseQueue = new PriorityQueue<>((c1, c2) ->
                c2.getNeighbors().size() - c1.getNeighbors().size());
        courseQueue.addAll(courses);
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
