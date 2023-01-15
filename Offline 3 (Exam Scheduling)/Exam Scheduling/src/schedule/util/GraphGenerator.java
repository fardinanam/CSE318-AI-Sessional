package schedule.util;

import graph.Graph;

import java.io.FileNotFoundException;

public interface GraphGenerator <T extends Graph> {
    public void setInputFile(String inputFileName);
    public T generateGraph() throws FileNotFoundException;
}
