package variable.order.heuristics;

import csps.LatinSquareCsp;
import variables.Cell;

import java.util.ArrayList;

/**
 * An adapter class of MinimumRemainingValue and MaximumDegree heuristics.
 */
public class MrvMd implements VariableOrderHeuristic<Integer, Cell, LatinSquareCsp> {
    /**
     * Selects a cell with the minimum remaining values in its domain.
     * If there are more than one cell with the smallest domain then
     * the cell with the maximum degree is selected.
     * @return the variable with
     */
    @Override
    public Cell getNextVariable(LatinSquareCsp csp) {
        MinimumRemainingValue mrv = new MinimumRemainingValue();
        ArrayList<Cell> smallestDomainCells = mrv.getNextVariables(csp);

        // Now apply MaximumDegree heuristic to the smallest domain cells
        MaximumDegree md = new MaximumDegree();
        return md.getNextVariable(csp.getSquareLength(), smallestDomainCells);
    }
}
