package ir.pint.soltoon.soltoongame.shared;

import ir.pint.soltoon.utils.shared.comminucation.ComRemoteConfig;
import ir.pint.soltoon.utils.shared.comminucation.ComRemoteInfo;

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


    public static int DEFAULT_CLIENTS_COUNT = 1;
    public static ComRemoteInfo DEFAULT_REMOTE_INFO = new ComRemoteInfo("127.0.0.1", 5566, "password1");
    public static ComRemoteInfo DEFAULT_REMOTE_INFO2 = new ComRemoteInfo("127.0.0.1", 5567, "password2");
    public static ComRemoteConfig DEFAULT_REMOTE_CONFIG = new ComRemoteConfig("password1", 5566);
    public static ComRemoteConfig DEFAULT_REMOTE_CONFIG2 = new ComRemoteConfig("password2", 5567);
}
