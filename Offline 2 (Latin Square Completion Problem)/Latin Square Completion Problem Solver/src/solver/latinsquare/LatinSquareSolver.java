package solver.latinsquare;

import csps.LatinSquareCsp;
import value.order.heuristics.LeastConstrainedValue;
import value.order.heuristics.NoOrderValue;
import value.order.heuristics.ValueOrderHeuristic;
import variable.order.heuristics.MinimumRemainingValue;
import variable.order.heuristics.VariableOrderHeuristic;
import variables.Cell;

import java.util.List;

public class LatinSquareSolver {
    private final LatinSquareCsp csp;
    private VariableOrderHeuristic<Integer, Cell, LatinSquareCsp> vohVariable;
    private long noOfBacktracks;
    private long noOfAssignments;

    /**
     * Creates csp and initial assignment configuration from a two-dimensional array.
     * And the default VariableOrderHeuristic is MinimumRemainingValue.
     * @param n length of the square
     * @param initialAssignment initial values of the Latin Square
     */
    public LatinSquareSolver(int n, int[][] initialAssignment) {
        csp = new LatinSquareCsp(n, initialAssignment);
        vohVariable = new MinimumRemainingValue();
    }

    public void setVohVariable(VariableOrderHeuristic<Integer, Cell, LatinSquareCsp> vohVariable) {
        this.vohVariable = vohVariable;
    }

    private Cell[][] backtrack() {
        if(csp.isComplete()) return csp.getSquareGrid();

        Cell cell = vohVariable.getNextVariable(csp);
        if(cell == null) return null;

        ValueOrderHeuristic<Integer, Cell, LatinSquareCsp> vohValue = new LeastConstrainedValue(csp, cell);
        boolean isLeafNode = true;
        while (vohValue.hasNext()) {
            int value = vohValue.getNext();
            if (csp.isConstraintSatisfied(value, cell)) {
                List<Cell> affectedCells = csp.setValueAndGetAffectedCells(value, cell);
                noOfAssignments++;
                Cell[][] result = backtrack();

                // as backtrack is called so this is not a leaf node
                isLeafNode = false;
                if (result != null) {
                    return result;
                }

                // reset the value of the cell and fix the domains of the affected cells
                csp.resetVariable(cell);
                csp.addToDomains(value, affectedCells);
            }
        }

        if(isLeafNode) noOfBacktracks++;
        return null;
    }

    private Cell[][] forwardCheck() {
        if(csp.isComplete()) return csp.getSquareGrid();

        Cell cell = vohVariable.getNextVariable(csp);
        if(cell == null) {
            noOfBacktracks++;
            return null;
        }

        ValueOrderHeuristic<Integer, Cell, LatinSquareCsp> vohValue = new LeastConstrainedValue(csp, cell);
        boolean isLeafNode = true;
        while (vohValue.hasNext()) {
            int value = vohValue.getNext();
            if (csp.isConstraintSatisfied(value, cell)) {
                List<Cell> affectedCells = csp.setValueAndGetAffectedCells(value, cell);
                noOfAssignments++;
                // Forward checking
                if (csp.areConstraintDomainsConsistent(cell)) {
                    Cell[][] result = forwardCheck();

                    // as forwardCheck is called so this is not a leaf node
                    isLeafNode = false;
                    if (result != null) return result;
                }

                // reset the value of the cell and fix the domains of the affected cells
                csp.resetVariable(cell);
                csp.addToDomains(value, affectedCells);
            }
        }

        if (isLeafNode) noOfBacktracks++;
        return null;
    }

    /**
     * @return null if no solution exists
     */
    public Cell[][] solve(boolean isForwardChecking) {
        if(vohVariable == null) {
            System.out.println("Variable order heuristic is not yet selected");
            return null;
        }
        noOfBacktracks = 0;
        noOfAssignments = 0;
        long runtime = System.currentTimeMillis();
        Cell[][] solution = isForwardChecking ? forwardCheck() : backtrack();
        runtime = System.currentTimeMillis() - runtime;
        if(solution == null) System.out.println("No solution exists");
        else System.out.println("Runtime: " + runtime + "ms, Backtracks: " + noOfBacktracks + ", Nodes: " + noOfAssignments);
        return solution;
    }
}
