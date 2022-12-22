package solver.latinsquare;

import assignments.Assignment;
import assignments.LatinSquareAssignment;
import constraints.CellsConstraint;
import csps.LatinSquareCsp;
import variable.order.heuristics.VariableOrderHeuristic;
import variables.Cell;

public class LatinSquareSolver {
    private LatinSquareCsp csp;
    private LatinSquareAssignment assignment;
    private VariableOrderHeuristic<Integer, Cell, CellsConstraint, LatinSquareCsp, LatinSquareAssignment> voh;

    public LatinSquareSolver() {
        csp = null;
        assignment = null;
        voh = null;
    }

    public void setVoh(VariableOrderHeuristic<Integer, Cell, CellsConstraint, LatinSquareCsp, LatinSquareAssignment> voh) {
        this.voh = voh;
    }

    public LatinSquareAssignment solve() {
        return null;
    }
}
