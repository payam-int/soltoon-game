package ir.pint.soltoon.soltoongame.server;

import ir.pint.soltoon.soltoongame.shared.GameConfiguration;
import ir.pint.soltoon.soltoongame.shared.Platform;
import ir.pint.soltoon.soltoongame.shared.communication.command.Command;
import ir.pint.soltoon.soltoongame.shared.communication.command.CommandAction;
import ir.pint.soltoon.soltoongame.shared.communication.command.CommandNothing;
import ir.pint.soltoon.soltoongame.shared.communication.query.QueryAction;
import ir.pint.soltoon.soltoongame.shared.communication.query.QueryExit;
import ir.pint.soltoon.soltoongame.shared.communication.query.QueryFinalize;
import ir.pint.soltoon.soltoongame.shared.communication.result.*;
import ir.pint.soltoon.soltoongame.shared.data.action.*;
import ir.pint.soltoon.soltoongame.shared.result.GameEndedEvent;
import ir.pint.soltoon.soltoongame.shared.result.RoundStartEvent;
import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;
import ir.pint.soltoon.utils.shared.facades.uuid.UUID;

import java.util.*;

/**
 * Created by amirkasra on 9/30/2017 AD.
 */
public class DefaultServerManager extends ServerManager {

    private Server server;
    private CoreGameBoard gameBoard;

    private LinkedList<Long> fighters = new LinkedList<>();
    private Map<Long, Long> playerByFighter = new HashMap<>();

    public DefaultServerManager(Server server) {
        this.server = server;
        this.players = GameConfiguration.NUMBER_OF_PLAYERS;
        this.width = GameConfiguration.BOARD_WIDTH;
        this.height = GameConfiguration.BOARD_HEIGHT;
        this.rounds = GameConfiguration.ROUNDS;

        this.gameBoard = new CoreGameBoard(this.height, this.width);
        gameBoard.setPlayerByFighter(playerByFighter);
    }

    @Override
    public void run() {
        connectToClients();

        ResultStorage.putMisc("rounds", this.rounds);
        ResultStorage.putMisc("mapWidth", this.width);
        ResultStorage.putMisc("mapHeight", this.height);
        ResultStorage.putMisc("players", this.players);

        for (int round = 0; round < this.rounds; round++) {

            gameBoard.setRound(round);

            ResultStorage.addEvent(new RoundStartEvent(round, gameBoard.getMoneyByPlayer()));

            doRound();
        }

        ResultStorage.addEvent(new GameEndedEvent());

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
        gameBoard.setMyId(fighter);
        gameBoard.timePassedForCurrentPlayer();
    }

    private void queryPlayers() {
        Set<Long> players = server.getClients().keySet();

        for (Long player : players) {
            boolean skipPlayer = false;
            updateGameBoardForPlayer(player);

            while (!skipPlayer) {
                QueryAction queryAction = new QueryAction(player, gameBoard);
                Command command = server.query(queryAction, player, GameConfiguration.QUERY_WAIT_TIME);

                if (command == null)
                    command = new CommandNothing();

                boolean commandSuccessful = false;
                Result result = null;

                if (command instanceof CommandAction && ((CommandAction) command).getAction() instanceof AddFighterSrv) {
                    long id = UUID.getLong();
                    commandSuccessful = !((CommandAction) command).getAction().execute(gameBoard, id);

                    if (commandSuccessful) {
                        HashMap<String, Long> resultMeta = new HashMap<>();
                        resultMeta.put("id", id);
                        playerByFighter.put(id, player);
                        fighters.add(id);
                        result = ResultAddFighterAction.successful(player, id);
                    } else {
                        skipPlayer = true;
                    }
                } else {
                    skipPlayer = true;
                }

                if (!commandSuccessful) {
                    result = new ResultAction(player, Status.FAILURE);
                }
                server.sendResult(result, command, player);
            }
        }
    }

    private void updateGameBoardForPlayer(Long player) {
        gameBoard.setMyId(player);
        gameBoard.setMoneyPerTurn(gameBoard.getMyId(), GameConfiguration.PLAYERS_TURN_MONERY);
        gameBoard.timePassedForCurrentPlayer();
    }


}
