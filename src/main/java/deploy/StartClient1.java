package deploy;

import ir.pint.soltoon.soltoongame.client.ClientRunner;
import ir.pint.soltoon.soltoongame.shared.GameConfiguration;
import ir.pint.soltoon.utils.shared.comminucation.ComRemoteConfig;
import payam.WisePlayer;

public class StartClient1 {
    public static void main(String[] args) {
        GameConfiguration.ROUNDS = 10;
        ClientRunner.run(WisePlayer.class);
    }
}
