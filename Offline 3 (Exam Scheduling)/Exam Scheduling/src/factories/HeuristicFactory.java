package factories;

/**
 * Factory class for creating heuristics.
 * @param <T> the type of the heuristic
 * @param <U> the type of the input to the heuristic
 */
public interface HeuristicFactory <T, U> {
    T createHeuristic(U input);
}
