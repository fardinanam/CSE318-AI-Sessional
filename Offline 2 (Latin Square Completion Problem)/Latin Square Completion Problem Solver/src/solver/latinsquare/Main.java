package solver.latinsquare;

import variable.order.heuristics.MinimumMrvMdRatio;
import variables.Cell;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int n;
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();

        int[][] initialAssignment = new int[n][n];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                initialAssignment[i][j] = scanner.nextInt();
            }
        }
        LatinSquareSolver solver = new LatinSquareSolver(n, initialAssignment);
        solver.setVoh(new MinimumMrvMdRatio());
        Cell[][] solution = solver.solve(true);
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                System.out.print(solution[i][j].getValue() + " ");
            }
            System.out.println();
        }
    }
}
