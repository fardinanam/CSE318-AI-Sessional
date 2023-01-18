package logs;

public class Log {
    public static final boolean DEBUG = true;

    public static void log(String message) {
        if (DEBUG) {
            System.out.println(message);
        }
    }
}
