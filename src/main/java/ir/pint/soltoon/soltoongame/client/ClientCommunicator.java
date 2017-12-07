package ir.pint.soltoon.soltoongame.client;

import shared.communication.command.Command;
import shared.communication.query.Query;
import shared.communication.result.Result;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientCommunicator {
    
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    
    public ClientCommunicator(String host, int port) {
        System.out.println("connecting to server ...");
        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("connected !");
        try {
            in = new ObjectInputStream(socket.getInputStream());
            in.readObject();
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject("CLIENT::Handshake");
            out.flush();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    public Result sendCommand(Command command) {
        Result result = null;
        try {
            out.reset();
            out.writeObject(command);
            out.flush();
            result = (Result) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Query receiveQuery() {
        Query query = null;
        try {
            query = (Query) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return query;
    }
    
}
