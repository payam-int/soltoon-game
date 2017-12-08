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

    public final int NUMBER_OF_PLAYERS = 2;
    public final int HEIGHT = 10, WIDTH = 10;
    public final int ROUNDS = 20;


    private Server server;

    private CoreGameBoard gameBoard;
    private LinkedList<Long> fighters = new LinkedList<>();
    private Map<Long, Long> playerByFighter = new HashMap<>();

    public ServerManager(Server server) {
        this.server = server;
        this.gameBoard = new CoreGameBoard(HEIGHT, WIDTH);
    }

    @Override
    public void run() {
        connectToClients();
        for (int round = 0; round < ROUNDS; round++) {
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
            System.out.println(fighter);
            System.out.println(fighters);
            fighters.remove(fighter);
            System.out.println(fighters);
        }
    }

    private void queryFighters() {
        Long player = null;
        for (Long fighter : fighters) {
            player = playerByFighter.get(fighter);

            if (gameBoard.getObjectByID(fighter) == null || gameBoard.recentlyKilledIDs.contains(fighter))
                continue;

            updateGameBoardForFighter(fighter);

            QueryAction queryAction = new QueryAction(player, gameBoard);
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

    private void updateGameBoardForFighter(Long fighter) {
        gameBoard.setMyID(fighter);
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
        gameBoard.setMoneyPerTurn(gameBoard.getMyID(), 5);
        gameBoard.timePassedForCurrentPlayer();
    }

//    private void doShit() {
//        if (true)
//            return;
//
//        while (true) {
//            for (Long playerID : playerIDs) {
//                while (true) {
//                    gameBoard.setMyID(playerID);
//                    gameBoard.setMoneyPerTurn(gameBoard.getMyID(), 5);
//                    gameBoard.timePassedForCurrentPlayer();
//                    Command command = server.sendQuery(new QueryAction(playerID, gameBoard));
//
//                    try {
//
//                        if (command instanceof CommandAction) {
//
//                            Action action = ((CommandAction) command).action;
//
//                            if (action instanceof AddFighter || action instanceof Nothing) {
//                                if (action.execute(gameBoard))
//                                    throw new InvalidCommandException();
//                                else {
//                                    server.sendResult(new ResultAction(((CommandAction) command).id, Status.SUCCESS, new HashMap()));
//                                    if (action instanceof AddFighter) {
//                                        // Registering GameObject
//                                        gameObjectIDs.add(((AddFighter) action).AI.id);
//                                        server.id2client.put(((AddFighter) action).AI.id, server.id2client.get(((CommandAction) command).id));
//                                    } else if (action instanceof Nothing) break;
//                                }
//                            } else throw new InvalidCommandException();
//
//                        } else throw new InvalidCommandException();
//                    } catch (InvalidCommandException e) {
//                        e.printStackTrace();
//                        server.sendResult(new ResultAction(((CommandAction) command).id, Status.FAILURE, new HashMap()));
//                        break;
//                    }
//                }
//
//                gameBoard.print();
//            }
//
//            for (Long id : gameObjectIDs)
//                queryGameObjectByID(id, new QueryAction(id, gameBoard));
//
//            for (Long dead : gameBoard.recentlyKilledIDs)
//                gameObjectIDs.remove(dead);
//        }
//    }

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

            System.out.println(command);
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

//
//
//        for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
//            Command ci = server.accept();
//            playerIDs.add(ci.id);
//            if (ci instanceof CommandInitialize) {
//                server.sendResult(new ResultInitialize(ci.id, Status.SUCCESS, new HashMap()));
//            } else try {
//                throw new InvalidCommandException();
//            } catch (InvalidCommandException e) {
//                e.printStackTrace();
//                server.sendResult(new ResultInitialize(ci.id, Status.FAILURE, new HashMap()));
//            }
//            System.out.println(i + " th player joined with id: " + ci.id);
//    }

    }
//
//    private void queryGameObjectByID(Long id, Query query) {
//        if (gameBoard.getObjectByID(id) == null) return; //hamzaman dast nazanim !
//
//        gameBoard.setMyID(id);
//        gameBoard.timePassedForCurrentPlayer();
//        Command command = server.sendQuery(query);
//        if (query instanceof QueryFinalize) {
//            server.sendResult(new ResultAction(((CommandAction) command).id, Status.SUCCESS, new HashMap()));
//            return;
//        }
//
//        try {
//            if (command instanceof CommandAction) {
//                Action action = ((CommandAction) command).action;
//                if (action.execute(gameBoard))
//                    throw new InvalidCommandException();
//                else {
//                    server.sendResult(new ResultAction(((CommandAction) command).id, Status.SUCCESS, new HashMap()));
//                    if (action instanceof Shoot) {
//                        for (Long dead : gameBoard.recentlyKilledIDs)
//                            queryGameObjectByID(dead, new QueryFinalize(dead, gameBoard));
//                    }
//                }
//            } else
//                throw new InvalidCommandException();
//        } catch (InvalidCommandException e) {
//            e.printStackTrace();
//            server.sendResult(new ResultAction(((CommandAction) command).id, Status.FAILURE, new HashMap()));
//        }
//    }
}
