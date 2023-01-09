package value.order.heuristics;

import csps.LatinSquareCsp;
import variables.Cell;

import java.util.*;

public class LeastConstrainedValue implements ValueOrderHeuristic<Integer, Cell, LatinSquareCsp> {
    private PriorityQueue<Integer> values;
    private LatinSquareCsp csp;
    private Cell variable;

    public LeastConstrainedValue() {
        values = null;
        csp = null;
        variable = null;
    }

    public LeastConstrainedValue(LatinSquareCsp csp, Cell variable) {
        setCsp(csp);
        setVariable(variable);
    }

    @Override
    public void setCsp(LatinSquareCsp csp) {
        this.csp = csp;
    }

    /**
     * Sets the variable. And calculates the degree of each values of
     * the domain of the variable. Degree means, the number of unassigned variables
     * that are associated with the passed variable contains a value.
     * @param variable the variable
     */
    @Override
    public void setVariable(Cell variable) {
        this.variable = variable;
        HashMap<Integer, Integer> valueDegree = new HashMap<>();
        int x = variable.getX();
        int y = variable.getY();
        // min priority queue on the basis of degree
        values = new PriorityQueue<>(Comparator.comparing(valueDegree::get));

        // If no. of unassigned variables is less than twice the square length
        // then calculate the degree of each value of the domain of the variable
        // otherwise calculate it from the associated variables of the variable
        // this takes O(n^2) time
        if(csp.getUnassignedVariables().size() <= 2 * csp.getSquareLength()) {
            for (int value : variable.getDomain()) {
                valueDegree.put(value, 0);
                for (Cell cell : csp.getUnassignedVariables()) {
                    if ((cell.getX() == x || cell.getY() == y) && cell.getDomain().contains(value)) {
                        valueDegree.put(value, valueDegree.get(value) + 1);
                    }
                }
                values.add(value);
            }
        } else {
            for(int value : variable.getDomain()) {
                valueDegree.put(value, 0);
                for(int i = 0; i < csp.getSquareLength(); i++) {
                    Cell cell = csp.getSquareGrid()[i][y];
                    if(i != x && csp.getUnassignedVariables().contains(cell) && cell.getDomain().contains(value)) {
                        valueDegree.put(value, valueDegree.get(value) + 1);
                    }
                    cell = csp.getSquareGrid()[x][i];
                    if(i != y && csp.getUnassignedVariables().contains(cell) && cell.getDomain().contains(value)) {
                        valueDegree.put(value, valueDegree.get(value) + 1);
                    }
                }
                values.add(value);
            }
        }
    }

    /**
     * Returns the next value of the domain of the variable.
     * @return the next value of the domain of the variable. null if the domain is empty.
     */
    @Override
    public Integer getNext() {
        if(csp == null || variable == null) {
            return null;
        }
        return values.poll();
    }

    /**
     * Checks if the domain of the variable is empty.
     */
    @Override
    public boolean hasNext() {
        if(csp == null || variable == null) {
            return false;
        }
        return !values.isEmpty();
    }
}
