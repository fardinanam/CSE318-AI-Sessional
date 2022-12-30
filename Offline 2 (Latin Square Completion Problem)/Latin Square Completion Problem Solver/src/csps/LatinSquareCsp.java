package csps;

import variables.Cell;

import java.util.LinkedList;
import java.util.List;

public class LatinSquareCsp implements Csp<Integer, Cell> {
    private final int squareLength;
    private int[][] initialSquare;
    private final Cell[][] squareGrid;
    private final LinkedList<Cell> unassignedCells;

    public LatinSquareCsp(int squareLength) {
        this.squareLength = squareLength;
        squareGrid = new Cell[squareLength][squareLength];
        unassignedCells = new LinkedList<>();
        initialSquare = new int[squareLength][squareLength];
    }

    public LatinSquareCsp(int squareLength, int[][] initialAssignment) {
        this(squareLength);
        initialSquare = initialAssignment;
        setToInitialAssignment();
    }

    public void setToInitialAssignment() {
        // Initialize the grid
        for (int i = 0; i < squareLength; i++) {
            for (int j = 0; j < squareLength; j++) {
                squareGrid[i][j] = new Cell(i, j, squareLength);
            }
        }

        // use setValue to set the initial assignment so that
        // the related cell's domains are updated
        unassignedCells.clear();
        for (int i = 0; i < squareLength; i++) {
            for (int j = 0; j < squareLength; j++) {
                if (initialSquare[i][j] != 0) {
                    setValueAndGetAffectedCells(initialSquare[i][j], squareGrid[i][j]);
                } else {
                    unassignedCells.add(squareGrid[i][j]);
                }
            }
        }
    }

    public Cell[][] getSquareGrid() {
        return squareGrid;
    }

    /**
     * Does not do anything
     */
    @Override
    public void addVariable(Cell variable) {}

    public void addVariable(int x, int y, int value) {
        Cell cell = new Cell(x, y, squareLength);

        if(value == 0) {
            unassignedCells.add(cell);
        } else {
            cell.setValue(value);
        }

        squareGrid[x][y] = cell;
    }

    /**
     * Sets the value of the cell at the given coordinate to given value
     * and removes it from the unassigned cells. Then removes the value from
     * the domain of same row and column. Returns the list of affected cells.
     * @param value the value to set
     * @param cell the cell to set the value to
     * @return the list of affected cells
     */
    public List<Cell> setValueAndGetAffectedCells(int value, Cell cell) {
        cell.setValue(value);
        unassignedCells.remove(cell);
        LinkedList<Cell> affectedCells = new LinkedList<>();

        int x = cell.getX();
        int y = cell.getY();
        for(int i = 0; i < squareLength; i++) {
            if(i != x && squareGrid[i][y].getValue() == 0 && squareGrid[i][y].getDomain().contains(value)) {
                squareGrid[i][y].removeFromDomain(value);
                affectedCells.add(squareGrid[i][y]);
            }
            if(i != y && squareGrid[x][i].getValue() == 0 && squareGrid[x][i].getDomain().contains(value)) {
                squareGrid[x][i].removeFromDomain(value);
                affectedCells.add(squareGrid[x][i]);
            }
        }

        return affectedCells;
    }

    /**
     * Checks if any of the constraints has an empty domain
     * @param cell the cell whose associated constraint cells are to be checked
     * @return false if any of the constraint cells has an empty domain, true otherwise
     */
    @Override
    public boolean areConstraintDomainsConsistent(Cell cell) {
        int x = cell.getX();
        int y = cell.getY();
        for(int i = 0; i < squareLength; i++) {
            if(i != x && squareGrid[i][y].getValue() == 0 && squareGrid[i][y].getDomain().isEmpty()) return false;
            if(i != y && squareGrid[x][i].getValue() == 0 && squareGrid[x][i].getDomain().isEmpty()) return false;
        }
        return true;
    }

    /**
     * Adds a value to the domain of the passed list of cells.
     * @param cells The list of cells that are constrained by the variable
     * @param value The value to add to the domain of the cells
     */
    @Override
    public void addToDomains(Integer value, List<Cell> cells) {
        if(value != 0) {
            for(Cell cell : cells) {
                cell.addToDomain(value);
            }
        }
    }

    /**
     * Sets the value of the variable to 0 and adds the variable to
     * unassignedCells.
     * @param variable The variable to be reset
     */
    @Override
    public void resetVariable(Cell variable) {
        variable.removeValue();
        unassignedCells.add(variable);
    }

    @Override
    public LinkedList<Cell> getUnassignedVariables() {
        return unassignedCells;
    }

    /**
     * @return true if all the values are assigned.
     */
    @Override
    public boolean isComplete() {
        return unassignedCells.isEmpty();
    }

    /**
     * Checks if the constraints of the csp is satisfied
     * if the value of the cell is changed to the passed value.
     * @param value value to be matched
     * @param cell cell to be checked
     * @return true if constraints are satisfied
     */
    public boolean isConstraintSatisfied(int value, Cell cell) {
        int x = cell.getX();
        int y = cell.getY();

        for(int i = 0; i < squareLength; i++) {
            if (i != y && value == squareGrid[x][i].getValue()) return false;
            if (i != x && value == squareGrid[i][y].getValue()) return false;
        }

        return true;
    }

    public int getSquareLength() {
        return squareLength;
    }
}
