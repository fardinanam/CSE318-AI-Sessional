package csps;

import variables.Variable;

import java.util.List;

public interface Csp<T, T1 extends Variable<T>> {
    void addVariable(T1 variable);

    boolean areConstraintDomainsConsistent(T1 variable);

    void addToDomains(T value, List<T1> Variables);

    void resetVariable(T1 variable);

    List<T1> getUnassignedVariables();
    boolean isComplete();
}
