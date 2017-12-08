package ir.pint.soltoon.soltoongame.server;

import ir.pint.soltoon.soltoongame.shared.GameConfiguration;
import ir.pint.soltoon.soltoongame.shared.Platform;
import ir.pint.soltoon.soltoongame.shared.communication.command.Command;
import ir.pint.soltoon.soltoongame.shared.communication.command.CommandAction;
import ir.pint.soltoon.soltoongame.shared.communication.command.CommandInitialize;
import ir.pint.soltoon.soltoongame.shared.communication.command.CommandNothing;
import ir.pint.soltoon.soltoongame.shared.communication.query.QueryAction;
import ir.pint.soltoon.soltoongame.shared.communication.query.QueryExit;
import ir.pint.soltoon.soltoongame.shared.communication.query.QueryFinalize;
import ir.pint.soltoon.soltoongame.shared.communication.query.QueryInitialize;
import ir.pint.soltoon.soltoongame.shared.communication.result.*;
import ir.pint.soltoon.soltoongame.shared.data.action.*;
import ir.pint.soltoon.soltoongame.shared.exceptions.ClientInitializationException;
import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;
import ir.pint.soltoon.utils.shared.facades.uuid.UUID;

import java.util.*;

/**
 * Created by amirkasra on 9/30/2017 AD.
 */
public class ServerManager extends Thread {

    public final int NUMBER_OF_PLAYERS = 1;
    public final int HEIGHT = 10, WIDTH = 10;
    public final int ROUNDS = 50;


    private Server server;

    private CoreGameBoard gameBoard;
    private LinkedList<Long> fighters = new LinkedList<>();
    private Map<Long, Long> playerByFighter = new HashMap<>();

    public ServerManager(Server server) {
        this.server = server;
        this.gameBoard = new CoreGameBoard(HEIGHT, WIDTH);
        gameBoard.setPlayerByFighter(playerByFighter);
    }

    @Override
    public void run() {
        connectToClients();
        for (int round = 0; round < ROUNDS; round++) {
            gameBoard.setRound(round);
            doRound();
        }

        sendExitSignal();

        Platform.exit(Platform.OK);
    }

    private void sendExitSignal() {
        Set<Long> players = server.getClients().keySet();

        for (Long player : players) {
            QueryExit queryExit = new QueryExit(player, null);
            server.query(queryExit, player, GameConfiguration.EXIT_QUERY_TIME);
        }
    }

    private void doRound() {

        queryPlayers();
        queryFighters();
        removeKilledFighters();
    }

    private void removeKilledFighters() {
        Long player = null;
        for (Long fighter : gameBoard.recentlyKilledIDs) {
            player = playerByFighter.get(fighter);
            QueryFinalize queryFinalize = new QueryFinalize(fighter);
            Command command = server.query(queryFinalize, player, GameConfiguration.QUERY_WAIT_TIME);
            server.sendResult(new ResultFinalize(fighter, Status.SUCCESS), command, player);
            fighters.remove(fighter);
        }
    }

    private void queryFighters() {
        Long player = null;
        for (Long fighter : fighters) {
            player = playerByFighter.get(fighter);

            if (gameBoard.getObjectByID(fighter) == null || gameBoard.recentlyKilledIDs.contains(fighter))
                continue;


            updateGameBoardForFighter(fighter, player);

            QueryAction queryAction = new QueryAction(fighter, gameBoard);
            Command command = server.query(queryAction, player, GameConfiguration.QUERY_WAIT_TIME);

            if (command == null)
                command = new CommandNothing();

            boolean commandSuccessful = false;
            Result result = null;

            if (command instanceof CommandAction) {
                Action action = ((CommandAction) command).getAction();
                if (action instanceof Move || action instanceof Shoot) {
                    commandSuccessful = !action.execute(gameBoard);
                }
            }


            if (commandSuccessful) {
                result = new ResultAction(player, Status.SUCCESS);
            } else {
                result = new ResultAction(player, Status.FAILURE);
            }

            server.sendResult(result, command, player);
        }
    }

    private void updateGameBoardForFighter(Long fighter, Long player) {
        gameBoard.setMyID(fighter);
        gameBoard.setPlayerId(player);
        gameBoard.setMyMoney(gameBoard.getMoneyByID(player));
        gameBoard.timePassedForCurrentPlayer();
    }

    private void queryPlayers() {
        Set<Long> players = server.getClients().keySet();

        for (Long player : players) {

            updateGameBoardForPlayer(player);

            QueryAction queryAction = new QueryAction(player, gameBoard);
            Command command = server.query(queryAction, player, GameConfiguration.QUERY_WAIT_TIME);

            if (command == null)
                command = new CommandNothing();

            boolean commandSuccessful = false;
            Result result = null;

            if (command instanceof CommandAction && ((CommandAction) command).getAction() instanceof AddFighterType) {
                long id = UUID.getLong();
                commandSuccessful = !((CommandAction) command).getAction().execute(gameBoard, id);

                if (commandSuccessful) {
                    HashMap<String, Long> resultMeta = new HashMap<>();
                    resultMeta.put("id", id);
                    playerByFighter.put(id, player);
                    fighters.add(id);
                    result = ResultAddFighterAction.successful(player, id);
                }


            }

            if (!commandSuccessful) {
                result = new ResultAction(player, Status.FAILURE);
            }
            server.sendResult(result, command, player);


        }
    }

    private void updateGameBoardForPlayer(Long player) {
        gameBoard.setMyID(player);
        gameBoard.setPlayerId(player);
        gameBoard.setMoneyPerTurn(gameBoard.getMyID(), 5);
        gameBoard.setMyMoney(gameBoard.getMoneyByID(player));
        gameBoard.timePassedForCurrentPlayer();
    }

    private void connectToClients() {
        server.connect();

        Set<Long> clientIds = server.getClients().keySet();

        if (clientIds.size() != NUMBER_OF_PLAYERS)
            Platform.exit(Platform.PLAYERS_COUNT_ERROR);

        Iterator<Long> clients = clientIds.iterator();
        boolean initError = false;
        long client;
        while (clients.hasNext()) {
            client = clients.next();

            QueryInitialize queryInitialize = new QueryInitialize(client);
            Command command = server.query(queryInitialize, client, GameConfiguration.INITIALIZE_TIMEOUT);

            if (command != null && command instanceof CommandInitialize) {
                ResultInitialize resultInitialize = new ResultInitialize(client, Status.SUCCESS);
                server.sendResult(resultInitialize, command, client);
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

}
