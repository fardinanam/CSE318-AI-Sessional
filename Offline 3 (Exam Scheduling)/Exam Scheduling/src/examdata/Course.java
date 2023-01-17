package examdata;

import graph.Node;

import java.util.HashSet;

public class Course implements Node<Integer, Course> {
    private int label;
    private int timeSlot;
    private int noOfStudents;
    private HashSet<Course> neighbors;

    public Course(int label) {
        this.label = label;
        this.timeSlot = -1;
        this.noOfStudents = 0;
        neighbors = new HashSet<>();
    }

    public int getTimeSlot() {
        return timeSlot;
    }

    public int getNoOfStudents() {
        return noOfStudents;
    }

    public void setNoOfStudents(int noOfStudents) {
        this.noOfStudents = noOfStudents;
    }

    public void setTimeSlot(int timeSlot) {
        this.timeSlot = timeSlot;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(label);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Course)) {
            return false;
        }

        Course other = (Course) obj;
        return this.label == other.label;
    }

    @Override
    public Integer getLabel() {
        return label;
    }

    @Override
    public HashSet<Course> getNeighbors() {
        return neighbors;
    }

    @Override
    public void addNeighbor(Course course) {
        neighbors.add(course);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Course: ").append(label).append("\n");
        sb.append("neighbors: ");
        for (Course neighbor : neighbors) {
            sb.append(" ").append(neighbor.label);
        }

        return sb.toString();
    }
}
