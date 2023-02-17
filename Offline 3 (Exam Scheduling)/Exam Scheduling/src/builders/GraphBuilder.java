package builders;

import graph.Graph;

import java.io.FileNotFoundException;

public abstract class GraphBuilder<T extends Graph> implements Builder<T> {
    public abstract void setInputFile() throws FileNotFoundException;
}
