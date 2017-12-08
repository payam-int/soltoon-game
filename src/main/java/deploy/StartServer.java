package deploy;

import ir.pint.soltoon.soltoongame.server.ServerRunner;
import ir.pint.soltoon.utils.shared.comminucation.ComRemoteInfo;

public class StartServer {
    public static void main(String[] args) {
        ComRemoteInfo remoteInfo1 = new ComRemoteInfo("payam", "Payam Mohammadi", "127.0.0.1", 8586, "passa");
        ServerRunner.run(remoteInfo1);
    }
}
