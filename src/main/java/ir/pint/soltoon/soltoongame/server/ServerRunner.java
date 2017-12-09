package ir.pint.soltoon.soltoongame.server;

import ir.pint.soltoon.soltoongame.shared.GameConfiguration;
import ir.pint.soltoon.soltoongame.shared.Platform;
import ir.pint.soltoon.utils.shared.comminucation.ComInputStream;
import ir.pint.soltoon.utils.shared.comminucation.ComOutputStream;
import ir.pint.soltoon.utils.shared.comminucation.ComRemoteInfo;
import ir.pint.soltoon.utils.shared.comminucation.ComServer;
import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServerRunner {

    public static void runContainer() {
        ArrayList<ComRemoteInfo> remotes = ComRemoteInfo.createFromEnv();
        run(remotes, ServerMode.CONTAINER);
    }

    public static void run() {
        GameConfiguration.NUMBER_OF_PLAYERS = GameConfiguration.DEFAULT_CLIENTS_COUNT;

        JavadTypeResultHandler javadTypeResultHandler = new JavadTypeResultHandler();
        ResultStorage.addResultHandler(javadTypeResultHandler);

        run(GameConfiguration.DEFAULT_REMOTE_INFO, ServerMode.GUI);

    }

    public static void run(ComRemoteInfo comRemoteInfo, ServerMode serverMode) {
        run(Arrays.asList(comRemoteInfo), serverMode);
    }

    public static void run(List<ComRemoteInfo> remoteInfos, ServerMode serverMode) {

        // initiate communication between server and clients.
        ComServer comServer = ComServer.initiate(remoteInfos);


        // create comminucation wrapper
        Server server = new Server(comServer);

        // start judge
        ServerManager serverManager = new ServerManager(server);
        serverManager.run();

        Platform.exit(Platform.OK);
    }

}
