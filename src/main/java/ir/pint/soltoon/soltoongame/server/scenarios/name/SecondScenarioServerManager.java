package ir.pint.soltoon.soltoongame.server.scenarios.name;

import ir.pint.soltoon.soltoongame.server.manager.ManagerGame;
import ir.pint.soltoon.soltoongame.server.Server;
import ir.pint.soltoon.soltoongame.server.ServerManager;
import ir.pint.soltoon.soltoongame.shared.agents.Soltoon;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class SecondScenarioServerManager extends ServerManager {

    private Soltoon scenario;
    private Long scenarioId = -1L;

    private LinkedList<Long> fighters = new LinkedList<>();
    private Map<Long, Long> playerByFighter = new HashMap<>();

    public SecondScenarioServerManager(Server server, int width, int height) {
        this.server = server;
        this.players = 1;
        this.width = width;
        this.height = height;
        this.rounds = 1;

        this.gameBoard = new ManagerGame(this.height, this.width);
//        gameBoard.setPlayerByFighter(playerByFighter);
    }

    @Override
    public void run() {

    }
    //
//    @Override
//    public void run() {
//        initiateClients();
//
//        ResultStorage.putMisc("rounds", this.rounds);
//        ResultStorage.putMisc("mapWidth", this.width);
//        ResultStorage.putMisc("mapHeight", this.height);
//        ResultStorage.putMisc("players", this.players);
//
//        gameBoard.setRound(0);
//        ResultStorage.addEvent(new RoundStartEvent(0, gameBoard.getMoneyByPlayer()));
//
//        queryPlayers();
//
//        ResultStorage.addEvent(new GameEndedEvent());
//
//        sendExitSignal();
//
//        Platform.exit(Platform.OK);
//    }
//
//
//    private void sendExitSignal() {
//        Set<Long> players = server.getClients().keySet();
//
//        for (Long player : players) {
//            QueryExit queryExit = new QueryExit(player, null);
//            server.query(queryExit, player, GameConfiguration.EXIT_QUERY_TIME);
//        }
//    }
//
//
//    private void queryPlayers() {
//        Set<Long> players = server.getClients().keySet();
//
//        for (Long player : players) {
//            boolean skipPlayer = false;
//            updateGameBoardForPlayer(player);
//
//            gameBoard.setPlayerMoney(player, 100000000);
//
//            while (!skipPlayer) {
//
//                QueryAction queryAction = new QueryAction(player, gameBoard);
//                Command command = server.query(queryAction, player, GameConfiguration.QUERY_WAIT_TIME);
//
//                if (command == null)
//                    command = new CommandNothing();
//
//                boolean commandSuccessful = false;
//                Result result = null;
//
//                if (command instanceof CommandAction && ((CommandAction) command).getAction() instanceof AddKhadang) {
//                    long id = UUID.getLong();
//                    commandSuccessful = !PrivateCall.<Boolean>call(((CommandAction) command).getAction(), "execute", gameBoard);;
//
//                    if (commandSuccessful) {
//                        HashMap<String, Long> resultMeta = new HashMap<>();
//                        resultMeta.put("id", id);
//                        playerByFighter.put(id, player);
//                        fighters.add(id);
//                        result = ResultAddFighterAction.successful(player, id);
//                    } else {
//                        skipPlayer = true;
//                    }
//                } else {
//                    skipPlayer = true;
//                }
//
//                if (!commandSuccessful) {
//                    result = new ResultAction(player, Status.FAILURE);
//                }
//                server.sendResult(result, command, player);
//            }
//        }
//    }
//
//    private void updateGameBoardForPlayer(Long player) {
//        gameBoard.setMyId(player);
//        gameBoard.setMoneyPerTurn(gameBoard.getMyId(), GameConfiguration.PLAYERS_TURN_MONERY);
//        gameBoard.timePassedForCurrentPlayer();
//    }
}
