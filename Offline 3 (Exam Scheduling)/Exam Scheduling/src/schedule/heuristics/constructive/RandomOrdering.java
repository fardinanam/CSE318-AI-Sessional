package schedule.heuristics.constructive;

import coursedata.Course;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class RandomOrdering implements ConstructiveHeuristic<Integer, Course> {
    private final Queue<Course> courseQueue;

    public RandomOrdering(Collection<Course> courses) {
        // create a random ordering of the courses
        this.courseQueue = new LinkedList<>();

        LinkedList<Course> courseList = new LinkedList<>(courses);

        while (!courseList.isEmpty()) {
            int index = (int) (Math.random() * courseList.size());
            courseQueue.add(courseList.remove(index));
        }
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
