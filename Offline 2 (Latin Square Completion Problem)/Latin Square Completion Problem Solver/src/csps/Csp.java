package csps;

import constraints.Constraint;
import variables.Variable;

import java.util.List;

public interface Csp<T1 extends Variable, T2 extends Constraint> {
    void addVariable(T1 variable);
    void addConstraint(T2 constraint);
    List<T1> getVariables();
    List<T2> getConstraints();
}
