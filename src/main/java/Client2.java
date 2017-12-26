import ir.pint.soltoon.soltoongame.client.ClientRunner;
import ir.pint.soltoon.soltoongame.client.implementations.SoltoonSakht;
import ir.pint.soltoon.soltoongame.client.implementations.SoltoonSibl;

public class Client2 {

    public static void main(String[] args) {
//        ComInputStream.DEBUG = true;
//        ComOutputStream.DEBUG = true;
        ClientRunner.runPlayerTwo(SoltoonSibl.class);
    }

}
