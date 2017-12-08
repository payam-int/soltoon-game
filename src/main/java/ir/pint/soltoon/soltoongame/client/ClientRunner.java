package ir.pint.soltoon.soltoongame.client;

import ir.pint.soltoon.soltoongame.shared.Platform;
import ir.pint.soltoon.soltoongame.shared.data.Player;
import ir.pint.soltoon.utils.shared.comminucation.*;
import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class ClientRunner {
    public static void run(Class<? extends Player> player) {
        run(player, ComRemoteConfig.fromEnv());
    }

    public static void run(Class<? extends Player> player, ComRemoteConfig remoteConfig) {
        Comminucation connect = ComClient.connect(remoteConfig, 5000);
        ClientManager clientManager = new ClientManager(player, connect);
        clientManager.run();
        Platform.exit(Platform.OK);
    }
}
