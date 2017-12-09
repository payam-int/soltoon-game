package ir.pint.soltoon.soltoongame.shared;

public class GameConfiguration {
    public static double CONNECTION_COEFF = 1.3;
    public static double EXTRA_TIME_COEFF = 0.2;
    public static long QUERY_TIME = 2000L;
    public static long QUERY_EXTRA_TIME = (long) (QUERY_TIME * EXTRA_TIME_COEFF);
    public static long CONNECTION_EXTRA_TIME = 500L;
    public static long QUERY_WAIT_TIME = QUERY_TIME + QUERY_EXTRA_TIME + CONNECTION_EXTRA_TIME;
    public static long QUERY_INITIALIZE_TIME = 1000L;
    public static long INITIALIZE_TIMEOUT = (long) (QUERY_INITIALIZE_TIME * CONNECTION_COEFF);
    public static long QUERY_FINALIZE_TIME = QUERY_TIME;
    public static long QUERY_FINALIZE_EXTRA_TIME = QUERY_EXTRA_TIME;
    public static long EXIT_QUERY_TIME = 1000L;

    public static int NUMBER_OF_PLAYERS = 2;
    public static int ROUNDS = 50;
    public static int BOARD_WIDTH = 10;
    public static int BOARD_HEIGHT = 10;
}
