package schedule.heuristics.constructive;

import examdata.Course;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.PriorityQueue;

public class SaturationDegree implements ConstructiveHeuristic<Integer, Course> {
    private final PriorityQueue<Course> courseQueue;
    private final HashMap<Integer, Integer> saturationDegrees;
    private Course currentCourse;

    public SaturationDegree(Collection<Course> courses) {
        saturationDegrees = new HashMap<>();
        initializeSaturationDegrees(courses);
        courseQueue = new PriorityQueue<>(this::getDifference);
        initializeCourseQueue(courses);
        currentCourse = null;
    }

    private int getDifference(Course course1, Course course2) {
        // Difference should be course2 - course1 because we want to
        // use it in max priority queue
        int difference = 0;

        difference = saturationDegrees.get(course2.getLabel())
                - saturationDegrees.get(course1.getLabel());
        if (difference != 0)
            return difference;

        // If saturation ties then use the one with more students as tie-breaker
        difference = course2.getNoOfStudents() - course1.getNoOfStudents();
        if (difference != 0)
            return difference;

        // If saturation and no. of students ties then use the one with lower label as tie-breaker
        return course1.getLabel() - course2.getLabel();
    }

    private void initializeSaturationDegrees(Collection<Course> courses) {
        for (Course course : courses) {
            saturationDegrees.put(course.getLabel(), 0);
        }
    }

    private void updateSaturationDegrees(Course course) {
        if (course == null || course.getTimeSlot() < 0)
            return;
        for (Course neighbor : course.getNeighbors()) {
            saturationDegrees.put(neighbor.getLabel(),
                    saturationDegrees.get(neighbor.getLabel()) + 1);
        }
    }

    private void initializeCourseQueue(Collection<Course> courses) {
        courseQueue.addAll(courses);
    }

    /**
     * @return true if there is a next course to be returned
     */
    @Override
    public boolean hasNext() {
        // Current course will only be null when hasNext is first called
        // or the queue is empty.
        if (currentCourse != null) {
            // If current course has not yet been assigned a time slot
            // return true without changing the queue
            if (currentCourse.getTimeSlot() < 0) {
                return true;
            }

            // Update saturation degrees of neighbors of current course
            updateSaturationDegrees(currentCourse);

            // Update the priority queue
            courseQueue.remove(currentCourse);
            courseQueue.add(currentCourse);
        }

        // Get the next course
        currentCourse = courseQueue.poll();

        return currentCourse != null;
    }

    @Override
    public Integer getNext() {
        return Objects.requireNonNull(currentCourse).getLabel();
    }
}
