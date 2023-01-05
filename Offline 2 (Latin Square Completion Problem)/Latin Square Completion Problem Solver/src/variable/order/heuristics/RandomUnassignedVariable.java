package variable.order.heuristics;

import csps.LatinSquareCsp;
import variables.Cell;

import java.util.Random;

public class RandomUnassignedVariable implements VariableOrderHeuristic<Integer, Cell, LatinSquareCsp> {
    private final Random random;
    public RandomUnassignedVariable() {
        random  = new Random(System.currentTimeMillis());
    }
    /**
     * @return a random variable
     */
    @Override
    public Cell getNextVariable(LatinSquareCsp csp) {
        return csp.getUnassignedVariables().get(random.nextInt(csp.getUnassignedVariables().size()));
    }
}

