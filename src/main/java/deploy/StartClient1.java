package deploy;

import ir.pint.soltoon.soltoongame.client.ClientRunner;
import ir.pint.soltoon.utils.shared.comminucation.ComRemoteConfig;
import student.payam.WisePlayer;

public class StartClient1 {
    public static void main(String[] args) {
        ComRemoteConfig remote = new ComRemoteConfig("passa", 8586);
        ClientRunner.run(WisePlayer.class, remote);
    }
}
