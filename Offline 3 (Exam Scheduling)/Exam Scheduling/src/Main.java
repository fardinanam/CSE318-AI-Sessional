import builders.SchedulerBuilder;
import coursedata.CourseDependencyGraph;
import directors.SchedulerDirector;
import logs.Log;
import schedule.util.Scheduler;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    private static String getConstructiveHeuristicName(int heuristicNumber) {
        switch (heuristicNumber) {
            case 1:
                return "SaturationDegree";
            case 2:
                return "LargestDegree";
            case 3:
                return "LargestEnrollment";
            case 4:
                return "RandomOrdering";
            default:
                throw new IllegalArgumentException("Invalid heuristic number.");
        }
    }

    private static String getPenaltyCalculatorName(int penaltyCalculatorNumber) {
        switch (penaltyCalculatorNumber) {
            case 1:
                return "linear";
            case 2:
                return "exponential";
            default:
                throw new IllegalArgumentException("Invalid penalty calculator number.");
        }
    }

    private static Scheduler schedule(String datasetName, String constructiveHeuristic, String penaltyCalculator) {
        SchedulerBuilder schedulerBuilder = new SchedulerBuilder(datasetName, constructiveHeuristic, penaltyCalculator);
        SchedulerDirector schedulerDirector = new SchedulerDirector(schedulerBuilder);

        try {
            schedulerDirector.construct();
        } catch (FileNotFoundException e) {
            Log.log("Main::schedule(String, String): " + e.getMessage());
            return null;
        }

        Scheduler scheduler = schedulerBuilder.getBuilt();
        scheduler.schedule();
        return scheduler;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the dataset: ");
        String datasetName = scanner.nextLine();

        System.out.println("Enter Constructive Heuristic (1-4): ");
        System.out.println("1. Saturation Degree");
        System.out.println("2. Largest Degree");
        System.out.println("3. Largest Enrollment");
        System.out.println("4. Random Ordering");
        String constructiveHeuristic = getConstructiveHeuristicName(scanner.nextInt());

        System.out.println("Enter Penalty Calculator (1-2): ");
        System.out.println("1. Linear");
        System.out.println("2. Exponential");
        String penaltyCalculator = getPenaltyCalculatorName(scanner.nextInt());

        System.out.println(schedule(datasetName, constructiveHeuristic, penaltyCalculator));

    }
}