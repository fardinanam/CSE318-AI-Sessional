package value.order.heuristics;

import csps.Csp;
import variables.Variable;

public interface ValueOrderHeuristic <T, T1 extends Variable<T>, T2 extends Csp<T1>> {
    void setCsp(T2 csp);
    void setVariable(T1 variable);
    T getNext();
    boolean hasNext();
}
