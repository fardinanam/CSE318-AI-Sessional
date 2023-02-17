package logs;

public class Log {
    public static final boolean DEBUG = false;

    public static void log(String message) {
        if (DEBUG) {
            System.out.println(message);
        }
    }
}
