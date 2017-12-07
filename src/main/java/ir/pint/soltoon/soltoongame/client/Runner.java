package ir.pint.soltoon.soltoongame.client;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Runner {
    
    public static void main(String args[]) {
        int port = 9998;
        String host = "localhost";
        String className = "ai.My";
        // ToDo: port o host ro az args begir
        // ToDo: className ro az args begir
        try {
            ClientCommunicator clientCommunicator = new ClientCommunicator(host, port);
            ClientManager ClientManager = new ClientManager(clientCommunicator, className);
            try {
                ClientManager.run();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
