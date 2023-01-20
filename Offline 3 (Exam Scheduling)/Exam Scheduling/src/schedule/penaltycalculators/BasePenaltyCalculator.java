package schedule.penaltycalculators;

import coursedata.CourseDependencyGraph;
import coursedata.Student;
import logs.Log;

public abstract class BasePenaltyCalculator implements PenaltyCalculator<Double> {
    private final CourseDependencyGraph graph;

    public BasePenaltyCalculator(CourseDependencyGraph graph) {
        this.graph = graph;
    }
    protected abstract int penalty(int timeSlot1, int timeSlot2);
    private int calculateStudentPenalty(Student student) {
        int penalty = 0;

        int coursesEnrolled = student.getEnrolledCourseIds().size();

        for (int i = 0; i < coursesEnrolled; i++) {
            int courseId1 = student.getEnrolledCourseIds().get(i);

            for (int j = i + 1; j < coursesEnrolled; j++) {
                int courseId2 = student.getEnrolledCourseIds().get(j);

                if (courseId1 != courseId2) {
                    int timeSlot1 = graph.getNode(courseId1).getTimeSlot();
                    int timeSlot2 = graph.getNode(courseId2).getTimeSlot();

                    penalty += penalty(timeSlot1, timeSlot2);
                }
            }
        }

        return penalty;
    }

    @Override
    public Double calculatePenaltyAvg() {
        int noOfStudents = graph.getTotalStudents();
//        Log.log("Total students: " + noOfStudents);
        int totalPenalty = 0;

        for (Student student : graph.getStudents()) {
            totalPenalty += calculateStudentPenalty(student);
        }

//        Log.log("Total penalty: " + totalPenalty);
//        Log.log("Number of Students: " + noOfStudents);
        return ((double) totalPenalty) / noOfStudents;
    }
}
