package ir.pint.soltoon.soltoongame.server;

import ir.pint.soltoon.soltoongame.shared.GameConfiguration;
import ir.pint.soltoon.soltoongame.shared.Platform;
import ir.pint.soltoon.soltoongame.shared.communication.command.Command;
import ir.pint.soltoon.soltoongame.shared.communication.command.CommandInitialize;
import ir.pint.soltoon.soltoongame.shared.communication.query.Query;
import ir.pint.soltoon.soltoongame.shared.communication.query.QueryInitialize;
import ir.pint.soltoon.soltoongame.shared.communication.result.Result;
import ir.pint.soltoon.soltoongame.shared.result.PlayerJoined;
import ir.pint.soltoon.utils.shared.comminucation.ComRemoteInfo;
import ir.pint.soltoon.utils.shared.comminucation.ComServer;
import ir.pint.soltoon.utils.shared.comminucation.Comminucation;
import ir.pint.soltoon.utils.shared.exceptions.NoComRemoteAvailable;
import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;
import ir.pint.soltoon.utils.shared.facades.uuid.UUID;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class Server {

    private int port;
    private ServerSocket serverSocket;
    private ComServer comServer;
    //    public HashMap<Long, ClientHandler> id2client;
    private HashMap<Long, Comminucation> clients = new HashMap<>();
    private Hashtable<ComRemoteInfo, Comminucation> comminucations = new Hashtable<>();

    Server(ComServer comServer) {
//        try {
//            serverSocket = new ServerSocket(port);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        this.comServer = comServer;
//        id2client = new HashMap<>();
    }

    public void connect() {
        if (!this.comServer.connectAll(true)) {
            Platform.exit(Platform.CONNECTION_ERROR);
        }

        this.comminucations = this.comServer.getComminucations();

        // assign id to clients
        for (ComRemoteInfo remoteInfo : this.comminucations.keySet()) {
            long client = UUID.getLong();
            clients.put(client, this.comminucations.get(remoteInfo));

            ResultStorage.addEvent(new PlayerJoined(client, remoteInfo));
        }

    }


    public Command query(Query query, long client, long timeout) {
        Comminucation comminucation = clients.get(client);
        if (comminucation != null)
            return query(query, comminucation, timeout);
        else
            return null;
    }

    public Command query(Query query, Comminucation clientCom, long timeout) {
        Object o = null;
        try {
            clientCom.getObjectOutputStream().writeObject(query);
            o = clientCom.getObjectInputStream().readObject(((int) timeout));
        } catch (IOException e) {
            ResultStorage.addException(e);
            return null;
        }

        if (!(o instanceof Command))
            return null;
        return ((Command) o);

    }

    public void sendResult(Result result, Command command, long client) {
        Comminucation comminucation = clients.get(client);
        if (comminucation != null)
            sendResult(result, command, comminucation);
    }

    public void sendResult(Result result, Command command, Comminucation clientCom) {
        try {
            clientCom.getObjectOutputStream().writeObject(result);
        } catch (IOException e) {

        }
    }

    public HashMap<Long, Comminucation> getClients() {
        return clients;
    }
//
//    public CommandInitialize accept() {
//        Socket socket = null;
//        try {
//            socket = serverSocket.accept();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        ClientHandler ch = new ClientHandler(socket);
//        ch.send(new QueryInitialize(0l));
//        CommandInitialize c = (CommandInitialize) ch.receive();
//        id2client.put(c.id, ch);
//        return c;
//    }
//
//    public Command sendQuery(Query query) {
//        ClientHandler ch = id2client.get(query.id);
//        ch.send(query);
//        return (Command) ch.receive();
//    }
//
//    public void sendResult(Result result) {
//        id2client.get(result.id).send(result);
//    }
//
//    public class ClientHandler {
//        private Socket socket;
//        private ObjectInputStream in;
//        private ObjectOutputStream out;
//
//        public ClientHandler(Socket socket) {
//            System.out.println("Socket established...");
//            this.socket = socket;
//            try {
//                out = new ObjectOutputStream(this.socket.getOutputStream());
//                out.writeObject("SERVER::Handshake");
//                out.flush();
//                in = new ObjectInputStream(this.socket.getInputStream());
//                in.readObject();
//
//            } catch (IOException | ClassNotFoundException ex) {
//                ex.printStackTrace();
//            }
//        }
//
//        public void send(Object o) {
//            try {
//                out.reset();
//                out.writeObject(o);
//                out.flush();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        public Object receive() {
//            try {
//                return in.readObject();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//    }
}
