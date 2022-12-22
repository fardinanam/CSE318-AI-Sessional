package constraints;

import variables.Variable;

import java.util.LinkedList;
import java.util.List;

public class CellsConstraint implements Constraint<Integer> {
    private List<Variable<Integer>> scope;

    public CellsConstraint() {
        scope = new LinkedList<>();
    }
    @Override
    public void addToScope(Variable<Integer> variable) {
        scope.add(variable);
    }

    @Override
    public void removeFromScope(Variable<Integer> variable) {
        scope.remove(variable);
    }

    /**
     * Checks if all the variables in the scope holds the constraint of inequality.
     * @return true, if it holds the constraint. false, otherwise.
     */
    @Override
    public boolean holds() {
        for (int i = 0; i < scope.size(); i++) {
            int iVal = scope.get(i).getValue();
            if(iVal == 0)
                continue;
            for (int j = i + 1; j < scope.size(); j++) {
                int jVal = scope.get(j).getValue();
                if(jVal != 0 && iVal == jVal) {
                    return false;
                }
            }
        }

        return true;
    }
}
