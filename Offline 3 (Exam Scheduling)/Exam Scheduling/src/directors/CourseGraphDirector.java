package directors;

import builders.CourseGraphBuilder;

import java.io.FileNotFoundException;

public class CourseGraphDirector implements Director {
    private final CourseGraphBuilder courseGraphBuilder;

    public CourseGraphDirector(CourseGraphBuilder courseGraphBuilder) {
        this.courseGraphBuilder = courseGraphBuilder;
    }

    @Override
    public void construct() throws FileNotFoundException {
        courseGraphBuilder.setInputFile();
    }
}
