package builders;

import coursedata.CourseDependencyGraph;
import coursedata.Student;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CourseGraphBuilder extends GraphBuilder<CourseDependencyGraph> {
    private final String inputFileName;
    private File stuFile;
    private File crsFile;
    private final CourseDependencyGraph courseDependencyGraph;

    public CourseGraphBuilder(String inputFileName) {
        this.inputFileName = inputFileName;
        courseDependencyGraph = new CourseDependencyGraph();
    }

    @Override
    public void setInputFile() throws FileNotFoundException {
        stuFile = new File(inputFileName + ".stu");
        crsFile = new File(inputFileName + ".crs");

        if(!stuFile.exists())
            throw new FileNotFoundException(inputFileName + ".stu not found.");
        if(!crsFile.exists())
            throw new FileNotFoundException(inputFileName + ".crs not found.");
    }

    @Override
    public CourseDependencyGraph getBuilt() {
        addCourses();
        addStudents();

        return courseDependencyGraph;
    }

    private Scanner setScanner(File file)  {
        Scanner scanner = null;

        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return scanner;
    }

    private void addCourses() {
        if (crsFile == null)
            throw new IllegalStateException("Input file not set.");

        Scanner scanner = setScanner(crsFile);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] courseInfo = line.split(" ");
            int courseLabel = Integer.parseInt(courseInfo[0]);
            int noOfStudents = Integer.parseInt(courseInfo[1]);
            courseDependencyGraph.addNode(courseLabel);
            courseDependencyGraph.setNoOfStudents(courseLabel, noOfStudents);
        }
    }

    private void addStudents() {
        Scanner scanner = setScanner(stuFile);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (line.isEmpty())
                continue;

            String[] studentCourses = line.split(" ");

            Student student = new Student();
            for (String course : studentCourses) {
                int courseLabel = Integer.parseInt(course);
                student.addCourse(courseLabel);
            }

            courseDependencyGraph.addStudent(student);
        }
    }
}
