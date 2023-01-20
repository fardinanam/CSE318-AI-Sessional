package coursedata;

import graph.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class CourseDependencyGraph implements Graph<Integer, Course> {
    private final HashMap<Integer, Course> courses;
    private final ArrayList<Student> students;

    public CourseDependencyGraph() {
        students = new ArrayList<>();
        courses = new HashMap<>();
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

    /**
     * Adds a student to the graph and then create edges between the student's enrolled courses.
     */
    public void addStudent(Student student) {
        students.add(student);

        for (int courseId1 : student.getEnrolledCourseIds()) {
            for (int courseId2 : student.getEnrolledCourseIds()) {
                if (courseId1 != courseId2)
                    addEdge(courseId1, courseId2);
            }
        }
    }

    @Override
    public Course getNode(Integer courseId) {
        return courses.get(courseId);
    }

    public HashSet<Course> getCourses() {
        return new HashSet<>(courses.values());
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setNoOfStudents(int courseId, int noOfStudents) {
        courses.get(courseId).setNoOfStudents(noOfStudents);
    }

    public int getTotalStudents() {
        return students.size();
    }

    @Override
    public int getNoOfNodes() {
        return courses.size();
    }

    public void assignCourseTimeSlot(int courseId, int timeSlot) {
        Course course = courses.get(courseId);
        course.setTimeSlot(timeSlot);
//        course.setTimeSlot(timeSlot);
//        timeSlotsAllocation.putIfAbsent(timeSlot, new LinkedList<>());
//        timeSlotsAllocation.get(timeSlot).add(courseId);
    }

    public HashMap<Integer, LinkedList<Integer>> getTimeSlots() {
        HashMap<Integer, LinkedList<Integer>> timeSlots = new HashMap<>();
        for (Course course : courses.values()) {
            int courseTimeSlot = course.getTimeSlot();
            timeSlots.putIfAbsent(courseTimeSlot, new LinkedList<>());
            timeSlots.get(courseTimeSlot).add(course.getLabel());
        }

        return timeSlots;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int timeSlotCount = 0;

        HashMap<Integer, LinkedList<Integer>> timeSlotsAllocation = getTimeSlots();

        // append time slots allocation
        for (int timeSlot : timeSlotsAllocation.keySet()) {
            timeSlotCount++;
            sb.append(timeSlot).append(" -> ");
            for (int courseId : timeSlotsAllocation.get(timeSlot)) {
                sb.append(courseId).append(" ");
            }
            sb.append("\n");
        }

        sb.append("Time slots: ").append(timeSlotCount);

        return sb.toString();
    }
}
