package ir.pint.soltoon.soltoongame.server;

import ir.pint.soltoon.utils.shared.comminucation.ComInputStream;
import ir.pint.soltoon.utils.shared.comminucation.ComOutputStream;
import ir.pint.soltoon.utils.shared.comminucation.ComRemoteInfo;
import ir.pint.soltoon.utils.shared.comminucation.ComServer;
import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;

import java.util.Arrays;

public class ServerRunner {

    public static void main(String[] args) throws Exception {
        ComOutputStream.DEBUG = true;
        ComInputStream.DEBUG = true;
        ResultStorage.setOutputStream(System.out);
        int port = 9998;


        ComRemoteInfo remoteInfo1 = new ComRemoteInfo("127.0.0.1", 8585, "password1");
        ComRemoteInfo remoteInfo2 = new ComRemoteInfo("127.0.0.1", 8586, "password2");
        ComServer comServer = ComServer.initiate(Arrays.asList(remoteInfo1, remoteInfo2));
        Server server = new Server(comServer);


        ServerManager serverManager = new ServerManager(server);

        serverManager.run();
    }

}
