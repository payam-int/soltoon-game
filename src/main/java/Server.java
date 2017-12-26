import ir.pint.soltoon.soltoongame.server.ServerRunner;
import ir.pint.soltoon.soltoongame.shared.GameConfiguration;
import ir.pint.soltoon.utils.shared.comminucation.ComInputStream;
import ir.pint.soltoon.utils.shared.comminucation.ComOutputStream;

public class Server {

    public static void main(String[] args) {
        GameConfiguration.BOARD_WIDTH = 10;
        GameConfiguration.BOARD_HEIGHT = 20;
        ServerRunner.runHelloWorld();
    }

}
