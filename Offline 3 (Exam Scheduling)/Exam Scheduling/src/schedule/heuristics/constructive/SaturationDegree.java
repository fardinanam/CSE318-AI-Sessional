package schedule.heuristics.constructive;

import coursedata.Course;
import logs.Log;

import java.util.*;

public class SaturationDegree implements ConstructiveHeuristic<Integer, Course> {
    private final PriorityQueue<Course> courseQueue;
    private final HashMap<Integer, HashSet<Integer>> saturationDegrees;
    private Course currentCourse;

    public SaturationDegree(Collection<Course> courses) {
        saturationDegrees = new HashMap<>();
        initializeSaturationDegrees(courses);
        courseQueue = new PriorityQueue<>(this::saturationDifference);
        initializeCourseQueue(courses);
        currentCourse = null;
    }

    /**
     * Takes two courses and returns the difference between their saturation degrees.
     * Ties are broken by comparing the number of neighbors of the courses.
     * Finally, ties are broken by comparing the course ids.
     * @return the difference between the saturation degrees of the two courses.
     *      return value is positive if course2 has a higher saturation degree than course1.
     */
    private int saturationDifference(Course course1, Course course2) {
        // Difference should be course2 - course1 because we want to
        // use it in max priority queue
        int difference = 0;

        difference = saturationDegrees.get(course2.getLabel()).size()
                - saturationDegrees.get(course1.getLabel()).size();
        if (difference != 0)
            return difference;

        // If saturation ties then use the one with more students as tie-breaker
        difference = course2.getNeighbors().size() - course1.getNeighbors().size();
        if (difference != 0)
            return difference;

        // If saturation and no. of students ties then use the one with lower label as tie-breaker
        return course1.getLabel() - course2.getLabel();
    }

    private void initializeSaturationDegrees(Collection<Course> courses) {
        for (Course course : courses) {
            saturationDegrees.put(course.getLabel(), new HashSet<>());
        }
    }

    private void updateNeighbors(Course course) {
        if (course == null || course.getTimeSlot() < 0) {
            Log.log("SaturationDegree::updateNeighbors(Course): Course is null or has no time slot assigned");
            return;
        }
        for (Course neighbor : course.getNeighbors()) {
            if (neighbor.getTimeSlot() < 0) {
                int neighborLabel = neighbor.getLabel();
                saturationDegrees.get(neighborLabel).add(course.getTimeSlot());

                // If the neighbor is in the queue then we need to update its priority
                if (courseQueue.remove(neighbor))
                    courseQueue.add(neighbor);
            }
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
            // and update the queue
            updateNeighbors(currentCourse);
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
