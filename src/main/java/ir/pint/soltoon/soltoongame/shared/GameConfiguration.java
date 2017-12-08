package ir.pint.soltoon.soltoongame.shared;

public class GameConfiguration {

    public static final double CONNECTION_COEFF = 1.3;
    public static final double EXTRA_TIME_COEFF = 0.2;
    public static final long QUERY_TIME = 2000L;
    public static final long QUERY_EXTRA_TIME = (long) (QUERY_TIME * EXTRA_TIME_COEFF);
    public static final long CONNECTION_EXTRA_TIME = 500L;
    public static final long QUERY_WAIT_TIME = QUERY_TIME + QUERY_EXTRA_TIME + CONNECTION_EXTRA_TIME;
    public static final long QUERY_INITIALIZE_TIME = 1000L;
    public static final long INITIALIZE_TIMEOUT = (long) (QUERY_INITIALIZE_TIME * CONNECTION_COEFF);
    public static final long QUERY_FINALIZE_TIME = QUERY_TIME;
    public static final long QUERY_FINALIZE_EXTRA_TIME = QUERY_EXTRA_TIME;
    public static final long EXIT_QUERY_TIME = 1000L;
}
