package variable.order.heuristics;

import csps.LatinSquareCsp;
import variables.Cell;

import java.util.ArrayList;

public class MinimumRemainingValue implements VariableOrderHeuristic<Integer, Cell, LatinSquareCsp> {
    /**
     * @return the variables with the smallest domain
     */
    public ArrayList<Cell> getNextVariables(LatinSquareCsp csp) {
        int smallestDomainSize = csp.getSquareLength() + 1;
        ArrayList<Cell> smallestDomainCells = new ArrayList<>();
        for(Cell cell : csp.getUnassignedVariables()) {
            int cellDomainSize = cell.getDomain().size();
            if(cellDomainSize < smallestDomainSize) {
                smallestDomainSize = cellDomainSize;
                smallestDomainCells.clear();
                smallestDomainCells.add(cell);
            } else if(cellDomainSize == smallestDomainSize) {
                smallestDomainCells.add(cell);
            }
        }

        return smallestDomainCells;
    }

    /**
     * @return A variable with the smallest domain
     */
    @Override
    public Cell getNextVariable(LatinSquareCsp csp) {
        return getNextVariables(csp).get(0);
    }
}
