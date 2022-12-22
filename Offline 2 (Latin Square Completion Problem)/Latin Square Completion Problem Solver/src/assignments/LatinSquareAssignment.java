package assignments;

import csps.LatinSquareCsp;
import variables.Cell;

import java.util.HashSet;
import java.util.Set;

public class LatinSquareAssignment implements Assignment<Cell> {
    private HashSet<Cell> variables;

    public LatinSquareAssignment() {
        variables = new HashSet<>();
    }
    @Override
    public Set<Cell> getVariables() {
        return variables;
    }
}
