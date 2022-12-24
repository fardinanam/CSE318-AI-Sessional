package value.order.heuristics;

import csps.LatinSquareCsp;
import variables.Cell;

import java.util.LinkedList;

public class NoOrderValue implements ValueOrderHeuristic<Integer, Cell, LatinSquareCsp> {
    private LatinSquareCsp csp;
    private Cell variable;
    private LinkedList<Integer> domain;
    public NoOrderValue() {
        csp = null;
        variable = null;
        domain = null;
    }

    public NoOrderValue(LatinSquareCsp csp, Cell variable) {
        setCsp(csp);
        setVariable(variable);
    }
    @Override
    public void setCsp(LatinSquareCsp csp) {
        this.csp = csp;
    }

    @Override
    public void setVariable(Cell variable) {
        this.variable = variable;
        domain = new LinkedList<>(variable.getDomain());
    }

    @Override
    public Integer getNext() {
        if(csp == null || variable == null) {
            System.out.println("CSP or variable is null");
            return null;
        }

        return domain.removeFirst();
    }

    @Override
    public boolean hasNext() {
        if(csp == null || variable == null) {
            System.out.println("CSP or variable is null");
            return false;
        }
        return !domain.isEmpty();
    }
}
