package ir.pint.soltoon.soltoongame.server;

import ir.pint.soltoon.soltoongame.server.manager.ManagerGame;
import ir.pint.soltoon.soltoongame.server.manager.ManagerGameSoltoon;
import ir.pint.soltoon.soltoongame.shared.GameConfiguration;
import ir.pint.soltoon.soltoongame.shared.Platform;
import ir.pint.soltoon.soltoongame.shared.communication.command.Command;
import ir.pint.soltoon.soltoongame.shared.communication.command.CommandInitialize;
import ir.pint.soltoon.soltoongame.shared.communication.query.QueryInitialize;
import ir.pint.soltoon.soltoongame.shared.communication.result.ResultInitialize;
import ir.pint.soltoon.soltoongame.shared.communication.result.Status;
import ir.pint.soltoon.soltoongame.shared.exceptions.ClientInitializationException;
import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;

import java.util.Iterator;
import java.util.Set;

public abstract class ServerManager {
    protected int rounds;
    protected int width, height;
    protected int players;

    protected ManagerGame gameBoard;
    protected Server server;

    public abstract void run();


    protected void initiateClients() {
        server.connect();

        Set<Long> clientIds = server.getClients().keySet();

        if (clientIds.size() != this.players) {
            System.err.printf("You must have at least %d connected clients.\n" +
                    "* Check your configurations.\n", this.players);
            Platform.exit(Platform.PLAYERS_COUNT_ERROR);
        }

        Iterator<Long> clients = clientIds.iterator();
        boolean initError = false;
        long client;
        while (clients.hasNext()) {
            client = clients.next();

            QueryInitialize queryInitialize = new QueryInitialize(client, gameBoard);
            Command command = server.query(queryInitialize, client, GameConfiguration.INITIALIZE_TIMEOUT);

            if (command != null && command instanceof CommandInitialize) {
                ResultInitialize resultInitialize = new ResultInitialize(client, Status.SUCCESS);
                server.sendResult(resultInitialize, command, client);
                addClient(client);
            } else {
                ResultInitialize resultInitialize = new ResultInitialize(client, Status.FAILURE);
                server.sendResult(resultInitialize, command, client);

                ResultStorage.addException(new ClientInitializationException(client));
                initError = true;
            }
        }


        if (initError)
            Platform.exit(Platform.CLIENT_INITIALIZATION_ERROR);
    }

    protected void addClient(Long client) {
        ManagerGameSoltoon engineGameSoltoon = new ManagerGameSoltoon(client, GameConfiguration.PLAYERS_INITIAL_SCORE, GameConfiguration.PLAYERS_TURN_MONERY, GameConfiguration.PLAYERS_INITIAL_MONEY);
        gameBoard.addSoltoon(engineGameSoltoon);
    }
}
