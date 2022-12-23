package csps;

import variables.Cell;

import java.util.LinkedList;

public class LatinSquareCsp implements Csp<Cell> {
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
                    setValue(initialSquare[i][j], squareGrid[i][j]);
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
     * the domain of same row and column. Also Checks to see if the cells
     * aligned in the same row or column with the given variable has any
     * empty domain after reducing the domain. Can be used for forward checking.
     * @param value the value to set
     * @param cell the cell to set the value to
     * @return false if the domain of any of the cells associated with the given
     *          variable becomes empty, true otherwise.
     */
    public boolean setValue(int value, Cell cell) {
        cell.setValue(value);
        unassignedCells.remove(cell);

        int x = cell.getX();
        int y = cell.getY();
        boolean hasEmptyDomain = false;
        for(int i = 0; i < squareLength; i++) {
            if(i != x && squareGrid[i][y].getValue() == 0) {
                squareGrid[i][y].removeFromDomain(value);
                if(!hasEmptyDomain && squareGrid[i][y].getDomain().isEmpty()) hasEmptyDomain = true;
            }
            if(i != y && squareGrid[x][i].getValue() == 0) {
                squareGrid[x][i].removeFromDomain(value);
                if(!hasEmptyDomain && squareGrid[x][i].getDomain().isEmpty()) hasEmptyDomain = true;
            }
        }

        return !hasEmptyDomain;
    }

    /**
     * Sets the value of the variable to 0 and adds the variable to
     * unassignedCells. Then adds the value from the domain of same
     * row and column.
     * @param variable The variable to be reset
     */
    @Override
    public void resetVariable(Cell variable) {
        int value = variable.getValue();
        if(value != 0) {
            variable.removeValue();
            unassignedCells.add(variable);

            int x = variable.getX();
            int y = variable.getY();
            for(int i = 0; i < squareLength; i++) {
                if(i != y && squareGrid[x][i].getValue() == 0) {
                    squareGrid[x][i].addToDomain(value);
                }
                if(i != x && squareGrid[i][y].getValue() == 0) {
                    squareGrid[i][y].addToDomain(value);
                }
            }
        }
    }

    /**
     * Sets the value of the variable at index (x,y) to 0 and adds the variable to
     * unassignedCells.
     * @param x x index of the variable in the square grid
     * @param y y index of the variable in the square grid
     */
    public void resetVariable(int x, int y) {
        resetVariable(squareGrid[x][y]);
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
