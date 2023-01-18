package schedule.heuristics.constructive;

import coursedata.Course;

import java.util.Collection;
import java.util.Objects;
import java.util.PriorityQueue;

public class LargestEnrollment implements ConstructiveHeuristic<Integer, Course> {
    private final PriorityQueue<Course> courseQueue;

    public LargestEnrollment(Collection<Course> courses) {
        // create a max priority queue based on number of students enrolled
        // in the courses
        this.courseQueue = new PriorityQueue<>((c1, c2) ->
                c2.getNoOfStudents() - c1.getNoOfStudents());
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
