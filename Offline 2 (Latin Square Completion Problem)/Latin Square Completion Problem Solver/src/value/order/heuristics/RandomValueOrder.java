package value.order.heuristics;

import csps.LatinSquareCsp;
import variables.Cell;

import java.util.LinkedList;
import java.util.Random;

public class RandomValueOrder implements ValueOrderHeuristic<Integer, Cell, LatinSquareCsp> {
    private LatinSquareCsp csp;
    private Cell variable;
    private LinkedList<Integer> domain;
    private final Random random;

    public RandomValueOrder() {
        this.random = new Random();
        csp = null;
        variable = null;
    }

    public RandomValueOrder(LatinSquareCsp csp, Cell variable) {
        this.random = new Random();
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
        if(csp == null || variable == null) return null;
        return domain.remove(random.nextInt(domain.size()));
    }

    @Override
    public boolean hasNext() {
        if(csp == null || variable == null) return false;
        return !domain.isEmpty();
    }
}
