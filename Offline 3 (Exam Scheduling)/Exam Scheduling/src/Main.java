import builders.SchedulerBuilder;
import coursedata.CourseDependencyGraph;
import directors.SchedulerDirector;
import logs.Log;
import schedule.util.Scheduler;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    private static Scheduler schedule(String datasetName, int constructiveHeuristic, int noOfIteration, int penaltyCalculator) {
        SchedulerBuilder schedulerBuilder = new SchedulerBuilder(datasetName, constructiveHeuristic, noOfIteration, penaltyCalculator);
        SchedulerDirector schedulerDirector = new SchedulerDirector(schedulerBuilder);

        try {
            schedulerDirector.construct();
        } catch (FileNotFoundException e) {
            Log.log("Main::schedule(String, String): " + e.getMessage());
            return null;
        }

        Scheduler scheduler = schedulerBuilder.getBuilt();
        scheduler.schedule();
        System.out.println(scheduler + "\n");
        scheduler.reducePenaltyByKempeChain();
        System.out.println("After kempe chain:");
        System.out.println(scheduler + "\n");
        scheduler.reducePenaltyByPairSwap();
        System.out.println("After pair swap:");
        System.out.println(scheduler);
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
        int constructiveHeuristic = scanner.nextInt();

        schedule(datasetName, constructiveHeuristic, 1000, 2);
    }
}