package solver.latinsquare;

import csps.LatinSquareCsp;
import variable.order.heuristics.MinimumRemainingValue;
import variable.order.heuristics.VariableOrderHeuristic;
import variables.Cell;

public class LatinSquareSolver {
    private final LatinSquareCsp csp;
    private VariableOrderHeuristic<Integer, Cell, LatinSquareCsp> voh;
    private long backtracks;
    private long assignments;

    /**
     * Creates csp and initial assignment configuration from a two-dimensional array.
     * And the default VariableOrderHeuristic is MinimumRemainingValue.
     * @param n length of the square
     * @param initialAssignment initial values of the Latin Square
     */
    public LatinSquareSolver(int n, int[][] initialAssignment) {
        csp = new LatinSquareCsp(n, initialAssignment);
        voh = new MinimumRemainingValue();
    }

    public void setVoh(VariableOrderHeuristic<Integer, Cell, LatinSquareCsp> voh) {
        this.voh = voh;
    }

    private Cell[][] backtrack() {
        if(csp.isComplete()) return csp.getSquareGrid();

        Cell cell = voh.getNextVariable(csp);
        if(cell == null) return null;
        for (int value : cell.getDomain()) {
            if (csp.isConstraintSatisfied(value, cell)) {
                csp.setValue(value, cell);
                assignments++;
                Cell[][] result = backtrack();
                if (result != null) return result;
                else backtracks++;
                csp.resetVariable(cell);
            }
        }

        return null;
    }

    private Cell[][] forwardTracing() {
        if(csp.isComplete()) return csp.getSquareGrid();

        Cell cell = voh.getNextVariable(csp);
        if(cell == null) return null;
        for (int value : cell.getDomain()) {
            if (csp.isConstraintSatisfied(value, cell)) {
                // Forward tracing
                boolean isConsistent = csp.setValue(value, cell);
                assignments++;
                if (isConsistent) {
                    Cell[][] result = forwardTracing();
                    if (result != null) return result;
                    else backtracks++;
                }

                csp.resetVariable(cell);
            }
        }

        return null;
    }

    /**
     * @return null if no solution exists
     */
    public Cell[][] solve(boolean isForwardTracing) {
        if(voh == null) {
            System.out.println("Variable order heuristic is not yet selected");
            return null;
        }
        backtracks = 0;
        assignments = 0;
        long runtime = System.currentTimeMillis();
        Cell[][] solution = isForwardTracing ? forwardTracing() : backtrack();
        runtime = System.currentTimeMillis() - runtime;
        if(solution == null) System.out.println("No solution exists");
        else System.out.println("Runtime: " + runtime + "ms, Backtracks: " + backtracks + ", Assignments: " + assignments);
        return solution;
    }
}
