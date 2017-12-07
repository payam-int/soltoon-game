package ir.pint.soltoon.soltoongame.client;

import shared.communication.command.CommandAction;
import shared.communication.command.CommandFinalize;
import shared.communication.command.CommandInitialize;
import shared.communication.query.*;
import shared.communication.result.FailureResultException;
import shared.communication.result.Result;
import shared.communication.result.Status;
import shared.data.Agent;
import shared.data.Player;
import shared.data.action.Action;
import shared.data.action.AddAgent;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ClientManager {

    private Map<Long, Agent> id2ai;
    private ClientCommunicator clientCommunicator;

    public ClientManager(ClientCommunicator clientCommunicator, String className) throws Exception {
        this.clientCommunicator = clientCommunicator;
        id2ai = new HashMap<>();
        Query query = clientCommunicator.receiveQuery();
        Result result = null;
        Long id = Math.abs(new Random().nextLong());
        if (query instanceof QueryInitialize) {
            result = clientCommunicator.sendCommand(new CommandInitialize(id));
        } else throw new InvalidQueryException();
        if (result.status== Status.failure)
            throw new FailureResultException();

        Player player = null;
        try {
            player = (Player) Class.forName(className).getConstructor(Long.class).newInstance(id);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        id2ai.put(id, player);
    }


    public void run() throws IOException, ClassNotFoundException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException {

        for (Query query; (query = clientCommunicator.receiveQuery()) != null; ) {
            if (query instanceof QueryAction) {
                Action action = id2ai.get(query.id).getAction(((QueryAction) query).gameBoard);
                Result result = clientCommunicator.sendCommand(new CommandAction(query.id, action));
                if (action instanceof AddAgent) {
                    id2ai.put(((AddAgent) action).AI.id, ((AddAgent) action).AI);
                }
            } else if (query instanceof QueryFinalize){
                id2ai.get(query.id).lastThingsToDo(((QueryFinalize) query).gameBoard);
                id2ai.remove(query.id);
                clientCommunicator.sendCommand(new CommandFinalize(query.id));
            }
            //ToDo more actions
        }

    }

}
