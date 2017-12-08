package ir.pint.soltoon.soltoongame.shared;

import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;

public class Platform {


    public static final int CONNECTION_ERROR = 1;
    public static final int PLAYERS_COUNT_ERROR = 2;
    public static final int CLIENT_INITIALIZATION_ERROR = 3;
    public static final int FATAL_ERROR = 4;
    public static final int GENERAL_ERROR = 5;
    public static final int OK = 0;

    public static void exit(int i) {
        ResultStorage.save();
        System.exit(i);
    }
}
