package csps;

import constraints.CellsConstraint;
import variables.Cell;

import java.util.ArrayList;
import java.util.List;

public class LatinSquareCsp implements Csp<Cell, CellsConstraint> {
    private ArrayList<Cell> cells;
    private ArrayList<CellsConstraint> constraints;

    @Override
    public void addVariable(Cell variable) {
        cells.add(variable);
    }

    @Override
    public void addConstraint(CellsConstraint constraint) {
        constraints.add(constraint);
    }

    @Override
    public List<Cell> getVariables() {
        return cells;
    }

    @Override
    public List<CellsConstraint> getConstraints() {
        return constraints;
    }
}
