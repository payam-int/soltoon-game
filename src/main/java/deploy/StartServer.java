package deploy;

import ir.pint.soltoon.soltoongame.server.ServerRunner;
import ir.pint.soltoon.soltoongame.shared.GameConfiguration;
import ir.pint.soltoon.utils.shared.comminucation.ComRemoteInfo;

public class StartServer {
    public static void main(String[] args) {
        GameConfiguration.ROUNDS = 50;

        ServerRunner.run();
    }
}
