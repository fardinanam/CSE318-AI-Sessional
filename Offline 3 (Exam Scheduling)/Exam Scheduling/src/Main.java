import builders.SchedulerBuilder;
import directors.SchedulerDirector;
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

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the dataset: ");
        String datasetName = scanner.nextLine();
        System.out.println("Enter Constructive Heuristic (1-4): ");
        System.out.println("1. Saturation Degree");
        System.out.println("2. Largest Degree");
        System.out.println("3. Largest Enrollment");
        System.out.println("4. Random Ordering");
        String constructiveHeuristic = getConstructiveHeuristicName(scanner.nextInt());

        SchedulerBuilder schedulerBuilder = new SchedulerBuilder(datasetName, constructiveHeuristic);
        SchedulerDirector schedulerDirector = new SchedulerDirector(schedulerBuilder);
        schedulerDirector.construct();
        Scheduler scheduler = schedulerBuilder.getBuilt();
        scheduler.schedule();

        System.out.println(scheduler.getGraph());
    }
}