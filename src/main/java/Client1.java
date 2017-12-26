import ir.pint.soltoon.soltoongame.client.ClientRunner;
import ir.pint.soltoon.soltoongame.client.implementations.RandomPlayer;
import ir.pint.soltoon.soltoongame.client.implementations.SoltoonSanjar;
import ir.pint.soltoon.utils.shared.comminucation.ComInputStream;
import ir.pint.soltoon.utils.shared.comminucation.ComOutputStream;


public class Client1 {

    public static void main(String[] args) {
//        ComInputStream.DEBUG = true;
//        ComOutputStream.DEBUG = true;
        ClientRunner.run(SoltoonSanjar.class);
    }
}
