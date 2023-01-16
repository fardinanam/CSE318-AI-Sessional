package examdata;

import graph.Graph;

import java.util.HashMap;

public class CourseDependencyGraph implements Graph<Integer, Course> {
    private HashMap<Integer, Course> courses;

    public CourseDependencyGraph() {
        courses = new HashMap<>();
    }
    @Override
    public void addNode(Integer label) {
        courses.put(label, new Course(label));
    }

    @Override
    public void addEdge(Integer label1, Integer label2) {
        Course course1 = courses.get(label1);
        Course course2 = courses.get(label2);

        course1.addNeighbor(course2);
        course2.addNeighbor(course1);
    }

    @Override
    public Course getNode(Integer label) {
        return courses.get(label);
    }

    public void setNoOfStudents(int label, int noOfStudents) {
        courses.get(label).setNoOfStudents(noOfStudents);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int key : courses.keySet()) {
            sb.append(key).append("->")
                    .append(courses.get(key).toString()).append("\n");
        }

        return sb.toString();
    }
}
