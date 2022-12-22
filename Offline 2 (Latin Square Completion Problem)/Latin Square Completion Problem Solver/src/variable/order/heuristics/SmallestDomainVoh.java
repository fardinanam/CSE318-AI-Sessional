package variable.order.heuristics;

import assignments.LatinSquareAssignment;
import constraints.CellsConstraint;
import csps.LatinSquareCsp;
import variables.Cell;

public class SmallestDomainVoh implements VariableOrderHeuristic<Integer, Cell, CellsConstraint, LatinSquareCsp, LatinSquareAssignment> {
    /**
     * @return the variable with the smallest domain
     */
    @Override
    public Cell getNextVariable(LatinSquareCsp csp, LatinSquareAssignment assignment) {
        return null;
    }
}
