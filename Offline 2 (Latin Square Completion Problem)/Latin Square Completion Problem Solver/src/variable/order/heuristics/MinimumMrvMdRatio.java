package variable.order.heuristics;

import csps.LatinSquareCsp;
import variables.Cell;

public class MinimumMrvMdRatio implements VariableOrderHeuristic<Integer, Cell, LatinSquareCsp> {
    /**
     * Selects the unassigned cell with the minimum ratio of domain size to degree.
     * @param csp the Latin Square CSP
     * @return the cell with the minimum ratio of domain size to degree. null if there are no unassigned cells.
     */
    @Override
    public Cell getNextVariable(LatinSquareCsp csp) {
        // Calculate the degrees of unassigned cells
        int[] rowDegrees = new int[csp.getSquareLength()];
        int[] colDegrees = new int[csp.getSquareLength()];

        for (Cell cell : csp.getUnassignedVariables()) {
            rowDegrees[cell.getX()]++;
            colDegrees[cell.getY()]++;
        }

        // Calculate the Mrv/Md and select the cell with the lowest ratio
        Cell minRatioCell = null;
        double minRatio = Double.MAX_VALUE;
        for (Cell cell : csp.getUnassignedVariables()) {
            int degree = rowDegrees[cell.getX()] + colDegrees[cell.getY()];
            double ratio = (double) cell.getDomain().size() / degree;
            if (ratio < minRatio) {
                minRatio = ratio;
                minRatioCell = cell;
            }
        }

        return minRatioCell;
    }
}
