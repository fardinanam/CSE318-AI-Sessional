import examdata.CourseDependencyGraph;
import schedule.util.CourseGraphGenerator;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        CourseGraphGenerator courseGraphGenerator = new CourseGraphGenerator("dataset/car-s-91");
        CourseDependencyGraph cdg = courseGraphGenerator.generateGraph();
        System.out.println(cdg);
    }
}