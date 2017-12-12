package ir.pint.soltoon.soltoongame.server.scenarios;

import ir.pint.soltoon.soltoongame.server.CoreGameBoard;
import ir.pint.soltoon.soltoongame.server.Server;
import ir.pint.soltoon.soltoongame.server.ServerManager;
import ir.pint.soltoon.soltoongame.shared.GameConfiguration;
import ir.pint.soltoon.soltoongame.shared.Platform;
import ir.pint.soltoon.soltoongame.shared.communication.command.Command;
import ir.pint.soltoon.soltoongame.shared.communication.query.QueryAction;
import ir.pint.soltoon.soltoongame.shared.communication.query.QueryExit;
import ir.pint.soltoon.soltoongame.shared.communication.result.*;
import ir.pint.soltoon.soltoongame.shared.data.Player;
import ir.pint.soltoon.soltoongame.shared.data.action.Action;
import ir.pint.soltoon.soltoongame.shared.result.GameEndedEvent;
import ir.pint.soltoon.soltoongame.shared.result.RoundStartEvent;
import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;

import java.util.*;

public class FirstScenarioServerManager extends ServerManager {

    private Player scenario;
    private Long scenarioId = -1L;

    private LinkedList<Long> fighters = new LinkedList<>();
    private Map<Long, Long> playerByFighter = new HashMap<>();

    public FirstScenarioServerManager(Server server, int width, int height) {
        this.server = server;
        this.players = 1;
        this.width = width;
        this.height = height;
        this.rounds = 1;

        this.gameBoard = new CoreGameBoard(this.height, this.width);
        gameBoard.setPlayerByFighter(playerByFighter);
    }

    @Override
    public void run() {
        connectToClients();
        connectToScenario();

        ResultStorage.putMisc("rounds", this.rounds);
        ResultStorage.putMisc("mapWidth", this.width);
        ResultStorage.putMisc("mapHeight", this.height);
        ResultStorage.putMisc("players", this.players);
        ResultStorage.putMisc("finalSceneOnly", true);

        gameBoard.setRound(0);
        ResultStorage.addEvent(new RoundStartEvent(0, gameBoard.getMoneyByPlayer()));

        queryScenario();
        queryPlayer();

        ResultStorage.addEvent(new GameEndedEvent());

        sendExitSignal();

        Platform.exit(Platform.OK);
    }

    private void queryScenario() {
        updateGameBoardForPlayer(scenarioId);
        FirstScenario firstScenario = new FirstScenario();
        firstScenario.init(gameBoard);

        Action action = null;
        while ((action = firstScenario.getAction(gameBoard)) != null) {
            action.execute(gameBoard);
            updateGameBoardForPlayer(scenarioId);
        }
    }

    private void connectToScenario() {
        gameBoard.addPlayer(scenarioId);
        gameBoard.setPlayerMoney(scenarioId, 10000000);
    }

    private void sendExitSignal() {
        Set<Long> players = server.getClients().keySet();

        for (Long player : players) {
            QueryExit queryExit = new QueryExit(player, null);
            server.query(queryExit, player, GameConfiguration.EXIT_QUERY_TIME);
        }
    }


    private void queryPlayer() {
        Set<Long> players = server.getClients().keySet();

        for (Long player : players) {
            boolean skipPlayer = false;
            updateGameBoardForPlayer(player);
            QueryAction queryAction = new QueryAction(player, gameBoard);
            Command command = server.query(queryAction, player, GameConfiguration.QUERY_WAIT_TIME);
            Result result = new ResultAction(player, Status.SUCCESS);
            server.sendResult(result, command, player);
        }
    }

    private void updateGameBoardForPlayer(Long player) {
        gameBoard.setMyId(player);
        gameBoard.setMoneyPerTurn(gameBoard.getMyId(), GameConfiguration.PLAYERS_TURN_MONERY);
    }
}
