package csps;

import variables.Variable;

import java.util.List;
import java.util.Set;

public interface Csp<T extends Variable> {
    void addVariable(T variable);
    void resetVariable(T variable);
    List<T> getUnassignedVariables();
    boolean isComplete();
}
