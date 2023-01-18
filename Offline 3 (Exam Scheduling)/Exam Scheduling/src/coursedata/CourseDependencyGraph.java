package coursedata;

import graph.Graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class CourseDependencyGraph implements Graph<Integer, Course> {
    private final HashMap<Integer, Course> courses;
    private final HashMap<Integer, LinkedList<Integer>> timeSlotsAllocation;

    public CourseDependencyGraph() {
        courses = new HashMap<>();
        timeSlotsAllocation = new HashMap<>();
    }
    @Override
    public void addNode(Integer courseId) {
        courses.put(courseId, new Course(courseId));
    }

    @Override
    public void addEdge(Integer courseId1, Integer courseId2) {
        Course course1 = courses.get(courseId1);
        Course course2 = courses.get(courseId2);

        course1.addNeighbor(course2);
        course2.addNeighbor(course1);
    }

    @Override
    public Course getNode(Integer courseId) {
        return courses.get(courseId);
    }

    public HashSet<Course> getCourses() {
        return new HashSet<>(courses.values());
    }

    public void setNoOfStudents(int courseId, int noOfStudents) {
        courses.get(courseId).setNoOfStudents(noOfStudents);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
//        for (int key : courses.keySet()) {
//            sb.append(key).append("->")
//                    .append(courses.get(key).toString()).append("\n");
//        }

        // append time slots allocation
        for (int timeSlot : timeSlotsAllocation.keySet()) {
            sb.append(timeSlot).append("->");
            for (int courseId : timeSlotsAllocation.get(timeSlot)) {
                sb.append(courseId).append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * Assigns the lowest possible time slot to the course
     * that is not already occupied by a neighbor.
     * @param courseId the course id of the course
     *                to be assigned a time slot to
     */
    public void assignCourseTimeSlot(int courseId) {
        Course course = courses.get(courseId);

        boolean[] occupiedTimeSlots = new boolean[courses.size()];
        for (Course neighbor : course.getNeighbors()) {
            if (neighbor.getTimeSlot() >= 0) {
                occupiedTimeSlots[neighbor.getTimeSlot()] = true;
            }
        }

        int timeSlot = 0;
        while (occupiedTimeSlots[timeSlot]) {
            timeSlot++;
        }

        course.setTimeSlot(timeSlot);
        timeSlotsAllocation.putIfAbsent(timeSlot, new LinkedList<>());
        timeSlotsAllocation.get(timeSlot).add(courseId);
    }
}
