package solver.latinsquare;

import variable.order.heuristics.*;
import variables.Cell;

import java.util.Scanner;

public class Main {
    public static LatinSquareSolver makeSolver(int size, int[][] initialAssignment, int heuristicNumber) throws IllegalArgumentException {
        LatinSquareSolver solver = new LatinSquareSolver(size, initialAssignment);
        switch (heuristicNumber) {
            case 1:
                solver.setVohVariable(new MinimumRemainingValue());
                break;
            case 2:
                solver.setVohVariable(new MaximumDegree());
                break;
            case 3:
                solver.setVohVariable(new MrvMd());
                break;
            case 4:
                solver.setVohVariable(new MinimumMrvMdRatio());
                break;
            case 5:
                solver.setVohVariable(new RandomUnassignedVariable());
                break;
            default:
                throw new IllegalArgumentException("Invalid heuristic number");
        }

        return solver;
    }
    public static void main(String[] args) {
        // Take input from user
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the size of the Latin Square: ");
        int size = scanner.nextInt();
        System.out.println("Enter the initial assignment of the Latin Square: ");
        int[][] initialAssignment = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                initialAssignment[i][j] = scanner.nextInt();
            }
        }
        // Ask which type of backtracking to use
        System.out.println("Enter the type of backtracking to use: ");
        System.out.println("1. Backtracking");
        System.out.println("2. Forward Checking");
        // Take input from user
        int backtrackingType = scanner.nextInt();
        System.out.println("Enter the heuristic number: ");
        // Print a manual to console
        System.out.println("1. Minimum Remaining Value");
        System.out.println("2. Maximum Degree");
        System.out.println("3. Minimum Remaining Value + Maximum Degree");
        System.out.println("4. Minimum Remaining Value / Maximum Degree");
        System.out.println("5. Random Unassigned Variable");
        int heuristicNumber = scanner.nextInt();
        scanner.close();
        // Create a solver
        LatinSquareSolver solver = makeSolver(size, initialAssignment, heuristicNumber);
        // Solve the problem
        Cell[][] result;
        if (backtrackingType == 1) {
            result = solver.solve(false);
        } else if (backtrackingType == 2) {
            result = solver.solve(true);
        } else {
            throw new IllegalArgumentException("Invalid backtracking type");
        }

        // Print the result
        if (result == null) {
            System.out.println("No solution found");
        } else {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    System.out.print(result[i][j].getValue() + " ");
                }
                System.out.println();
            }
        }
    }
}
