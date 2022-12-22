package constraints;

import variables.Variable;

public interface Constraint <T> {
    void addToScope(Variable<T> variable);
    void removeFromScope(Variable<T> variable);
    boolean holds();
}
