package schedule.util;

import coursedata.Course;
import coursedata.CourseDependencyGraph;
import factories.ConstructiveHeuristicFactory;
import schedule.heuristics.constructive.ConstructiveHeuristic;

public class Scheduler {
    private CourseDependencyGraph graph;
    private ConstructiveHeuristicFactory constructiveHeuristicFactory;

    public void setGraph(CourseDependencyGraph graph) {
        this.graph = graph;
    }

    public void setConstructiveHeuristicFactory(ConstructiveHeuristicFactory constructiveHeuristicFactory) {
        this.constructiveHeuristicFactory = constructiveHeuristicFactory;
    }

    public void schedule() {
        if (graph == null || constructiveHeuristicFactory == null)
            return;

        ConstructiveHeuristic<Integer, Course> heuristic = constructiveHeuristicFactory.createHeuristic(graph.getCourses());
        while (heuristic.hasNext()) {
            int courseId = heuristic.getNext();
            graph.assignCourseTimeSlot(courseId);
        }
    }

    public CourseDependencyGraph getGraph() {
        return graph;
    }
}
