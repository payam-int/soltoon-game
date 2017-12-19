package ir.pint.soltoon.soltoongame.server.scenarios.free;

import ir.pint.soltoon.soltoongame.server.Server;
import ir.pint.soltoon.soltoongame.server.ServerManager;
import ir.pint.soltoon.soltoongame.server.manager.ManagerGame;
import ir.pint.soltoon.soltoongame.server.manager.ManagerGameKhadang;
import ir.pint.soltoon.soltoongame.server.manager.ManagerGameSoltoon;
import ir.pint.soltoon.soltoongame.shared.GameConfiguration;
import ir.pint.soltoon.soltoongame.shared.Platform;
import ir.pint.soltoon.soltoongame.shared.communication.command.Command;
import ir.pint.soltoon.soltoongame.shared.communication.command.CommandAction;
import ir.pint.soltoon.soltoongame.shared.communication.command.CommandNothing;
import ir.pint.soltoon.soltoongame.shared.communication.query.QueryAction;
import ir.pint.soltoon.soltoongame.shared.communication.query.QueryExit;
import ir.pint.soltoon.soltoongame.shared.communication.query.QueryFinalize;
import ir.pint.soltoon.soltoongame.shared.communication.result.*;
import ir.pint.soltoon.soltoongame.shared.actions.Action;
import ir.pint.soltoon.soltoongame.shared.actions.AddKhadang;
import ir.pint.soltoon.soltoongame.shared.actions.Move;
import ir.pint.soltoon.soltoongame.shared.actions.Shoot;
import ir.pint.soltoon.soltoongame.shared.map.GameKhadang;
import ir.pint.soltoon.soltoongame.shared.map.GameSoltoon;
import ir.pint.soltoon.soltoongame.shared.result.GameEndedEvent;
import ir.pint.soltoon.soltoongame.shared.result.RoundStartEvent;
import ir.pint.soltoon.utils.shared.facades.reflection.PrivateCall;
import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;
import ir.pint.soltoon.utils.shared.facades.uuid.UUID;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by amirkasra on 9/30/2017 AD.
 */
public class FreeServerManager extends ServerManager {
    public FreeServerManager(Server server) {
        this.server = server;
        this.players = GameConfiguration.NUMBER_OF_PLAYERS;
        this.width = GameConfiguration.BOARD_WIDTH;
        this.height = GameConfiguration.BOARD_HEIGHT;
        this.rounds = GameConfiguration.ROUNDS;
        this.gameBoard = new ManagerGame(this.height, this.width);
    }

    @Override
    public void run() {
        addMapInfoToResult();

        initiateClients();

        startRounds();

        quitPlayers();

        Platform.exit(Platform.OK);
    }

    private void addMapInfoToResult() {
        ResultStorage.putMisc("rounds", this.rounds);
        ResultStorage.putMisc("mapWidth", this.width);
        ResultStorage.putMisc("mapHeight", this.height);
        ResultStorage.putMisc("players", this.players);
    }

    private void startRounds() {
        for (int round = 0; round < this.rounds; round++) {

            startRound(round);
        }

        ResultStorage.addEvent(new GameEndedEvent());
    }


    private void startRound(int round) {

        gameBoard.setRound(round);

        updateSoltoons();

        updateKhadangs();

        removeKilledKhadangs();
    }

    private void updateSoltoons() {
        Map<Long, GameSoltoon> soltoons = gameBoard.getSoltoons();

        Map<Long, Integer> currentMoneyForResult = new HashMap<>();

        for (GameSoltoon soltoon : soltoons.values()) {
            ManagerGameSoltoon engineGameSoltoon = (ManagerGameSoltoon) soltoon;
            engineGameSoltoon.passTurn();
            currentMoneyForResult.put(engineGameSoltoon.getId(), engineGameSoltoon.getMoney());
        }


        ResultStorage.addEvent(new RoundStartEvent(gameBoard.getCurrentRound(), currentMoneyForResult));


        // query soltoons
        for (GameSoltoon soltoon : soltoons.values()) {
            ManagerGameSoltoon engineGameSoltoon = (ManagerGameSoltoon) soltoon;

            boolean skipPlayer = false;
            while (!skipPlayer) {
                QueryAction queryAction = new QueryAction(soltoon.getId(), gameBoard);
                Command command = server.query(queryAction, soltoon.getId(), GameConfiguration.QUERY_WAIT_TIME);

                if (command == null)
                    command = new CommandNothing();

                boolean commandSuccessful = false;
                Result result = null;

                if (command instanceof CommandAction && ((CommandAction) command).getAction() instanceof AddKhadang) {
                    Long uuid = UUID.getLong();
                    commandSuccessful = !PrivateCall.<Boolean>call(((CommandAction) command).getAction(), "execute", gameBoard, soltoon, uuid);

                    if (commandSuccessful) {
                        result = ResultAddFighterAction.successful(soltoon.getId(), uuid);
                    } else {
                        skipPlayer = true;
                    }
                } else {
                    skipPlayer = true;
                }

                if (!commandSuccessful) {
                    result = new ResultAction(soltoon.getId(), Status.FAILURE);
                }
                server.sendResult(result, command, soltoon.getId());
            }
        }
    }


    private void updateKhadangs() {
        Long khadangOwner = null;

        for (GameKhadang khadang : gameBoard.getKhadangs().values()) {
            ((ManagerGameKhadang) khadang).turnPassed();
        }

        for (GameKhadang khadang : gameBoard.getKhadangs().values()) {
            khadangOwner = khadang.getOwner().getId();
            QueryAction queryAction = new QueryAction(khadang.getId(), gameBoard);
            Command command = server.query(queryAction, khadangOwner, GameConfiguration.QUERY_WAIT_TIME);

            if (command == null)
                command = new CommandNothing();

            boolean commandSuccessful = false;
            Result result = null;

            if (command instanceof CommandAction) {
                Action action = ((CommandAction) command).getAction();
                if (action instanceof Move) {
                    commandSuccessful = !PrivateCall.<Boolean>call(action, "execute", gameBoard, khadang);
                } else if (action instanceof Shoot) {
                    commandSuccessful = !PrivateCall.<Boolean>call(action, "execute", gameBoard, khadang);
                }
            }


            if (commandSuccessful) {
                result = new ResultAction(khadangOwner, Status.SUCCESS);
            } else {
                result = new ResultAction(khadangOwner, Status.FAILURE);
            }

            server.sendResult(result, command, khadangOwner);
        }
    }


    private void quitPlayers() {
        Set<Long> players = server.getClients().keySet();

        for (Long player : players) {
            QueryExit queryExit = new QueryExit(player, null);
            server.query(queryExit, player, GameConfiguration.EXIT_QUERY_TIME);
        }
    }


    private void removeKilledKhadangs() {
        Long khadangOwner = null;
        for (GameKhadang khadang : gameBoard.getRecentlyKilledKhadangs()) {
            khadangOwner = khadang.getOwner().getId();
            QueryFinalize queryFinalize = new QueryFinalize(khadang.getId());
            Command command = server.query(queryFinalize, khadangOwner, GameConfiguration.QUERY_WAIT_TIME);
            server.sendResult(new ResultFinalize(khadang.getId(), Status.SUCCESS), command, khadangOwner);
            gameBoard.removeKhadang(((ManagerGameKhadang) khadang));
        }
        gameBoard.emptyRecentlyKilledKhadangs();
    }
}
