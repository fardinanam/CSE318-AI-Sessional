package schedule.util;

import examdata.CourseDependencyGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CourseGraphGenerator implements GraphGenerator<CourseDependencyGraph> {
    private String inputFileName;
    private CourseDependencyGraph courseDependencyGraph;

    public CourseGraphGenerator(String inputFileName) {
        setInputFile(inputFileName);
    }

    /**
     * Sets the input file name and creates a new empty CourseDependencyGraph.
     * generateGraph() must be called to fill up the graph.
     * @param inputFileName the name of the input files without the extension
     */
    @Override
    public void setInputFile(String inputFileName) {
        this.inputFileName = inputFileName;
        courseDependencyGraph = new CourseDependencyGraph();
    }

    @Override
    public CourseDependencyGraph generateGraph() throws FileNotFoundException {
        addCourses();
        addStudents();

        return courseDependencyGraph;
    }

    private File setFile(String extension) throws FileNotFoundException {
        File file = new File(inputFileName + "." + extension);

        if (!file.exists()) {
            throw new FileNotFoundException("File " + file.getName() + " does not exist.");
        }

        return file;
    }
    private void addCourses() throws FileNotFoundException {
        File crsFile = setFile("crs");

        Scanner scanner = new Scanner(crsFile);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] courseInfo = line.split(" ");
            int courseLabel = Integer.parseInt(courseInfo[0]);
            int noOfStudents = Integer.parseInt(courseInfo[1]);
            courseDependencyGraph.addNode(courseLabel);
            courseDependencyGraph.setNoOfStudents(courseLabel, noOfStudents);
        }
    }

    private void addStudents() throws FileNotFoundException {
        File stuFile = setFile("stu");

        Scanner scanner = new Scanner(stuFile);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] studentCourses = line.split(" ");

            for (int i = 0; i < studentCourses.length; i++) {
                for (int j = i + 1; j < studentCourses.length; j++) {
                    int courseLabel1 = Integer.parseInt(studentCourses[i]);
                    int courseLabel2 = Integer.parseInt(studentCourses[j]);
                    courseDependencyGraph.addEdge(courseLabel1, courseLabel2);
                }
            }
        }
    }
}
