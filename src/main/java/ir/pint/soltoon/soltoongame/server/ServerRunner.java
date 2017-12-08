package ir.pint.soltoon.soltoongame.server;

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

    public static void main(String[] args) throws Exception {
        ArrayList<ComRemoteInfo> remotes = ComRemoteInfo.createFromEnv();
        run(remotes);
    }

    public static void run(ComRemoteInfo... remoteInfos) {
        run(Arrays.asList(remoteInfos));
    }

    public static void run(List<ComRemoteInfo> remoteInfos) {

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
