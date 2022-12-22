package variable.order.heuristics;

import assignments.Assignment;
import constraints.Constraint;
import csps.Csp;
import variables.Variable;

public interface VariableOrderHeuristic <T, T1 extends Variable<T>, T2 extends Constraint<T>, T3 extends Csp<T1, T2>, T4 extends Assignment<T1>> {
    public T1 getNextVariable(T3 csp, T4 assignment);
}
