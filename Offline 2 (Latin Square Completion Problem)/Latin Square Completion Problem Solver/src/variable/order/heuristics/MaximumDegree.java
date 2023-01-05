package variable.order.heuristics;

import csps.LatinSquareCsp;
import variables.Cell;

import java.util.ArrayList;
import java.util.List;

public class MaximumDegree implements VariableOrderHeuristic<Integer, Cell, LatinSquareCsp> {
    /**
     * Selects a cell with the maximum degree.
     * @param n the length of the square
     * @param cells the list of cells to choose from
     * @return the cell with the maximum degree. null if the list is empty.
     */
    public Cell getNextVariable(int n, List<Cell> cells) {
        int[] rowDegrees = new int[n];
        int[] colDegrees = new int[n];

        for (Cell cell : cells) {
            rowDegrees[cell.getX()]++;
            colDegrees[cell.getY()]++;
        }

        int maxDegree = 0;
        Cell maxDegreeCell = null;
        for (Cell cell : cells) {
            int degree = rowDegrees[cell.getX()] + colDegrees[cell.getY()];
            if (degree > maxDegree) {
                maxDegree = degree;
                maxDegreeCell = cell;
            }
        }

        return maxDegreeCell;
    }
    /**
     * Calculates the degrees of unassigned cells and returns the Cell with the highest degree.
     * @param csp the Latin Square CSP
     * @return the Cell with the maximum degree. null if there are no unassigned cells.
     */
    @Override
    public Cell getNextVariable(LatinSquareCsp csp) {
        return getNextVariable(csp.getSquareLength(), csp.getUnassignedVariables());
    }
}
