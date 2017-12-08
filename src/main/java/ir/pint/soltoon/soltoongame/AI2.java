package ir.pint.soltoon.soltoongame;

import ir.pint.soltoon.soltoongame.ai.My;
import ir.pint.soltoon.soltoongame.client.ClientRunner;
import ir.pint.soltoon.utils.shared.comminucation.ComRemoteConfig;
import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;

public class AI2 {
    public static void main(String[] args) {
        ComRemoteConfig remoteConfig = new ComRemoteConfig("password2", 8586);
        ResultStorage.setOutputStream(System.out);
        ClientRunner.run(My.class, remoteConfig);
    }
}
