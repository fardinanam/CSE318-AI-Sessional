package coursedata;

import java.util.ArrayList;

public class Student {
    private final ArrayList<Integer> enrolledCourseIds;

    public Student() {
        this.enrolledCourseIds = new ArrayList<>();
    }

    public void addCourse(int courseId) {
        enrolledCourseIds.add(courseId);
    }

    public ArrayList<Integer> getEnrolledCourseIds() {
        return enrolledCourseIds;
    }
}
