package variable.order.heuristics;

import csps.Csp;
import variables.Variable;

public interface VariableOrderHeuristic <T, T1 extends Variable<T>, T2 extends Csp<T, T1>> {
    public T1 getNextVariable(T2 csp);
}
