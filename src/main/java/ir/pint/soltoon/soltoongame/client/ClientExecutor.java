package ir.pint.soltoon.soltoongame.client;

import ir.pint.soltoon.soltoongame.shared.communication.command.CommandAction;
import ir.pint.soltoon.soltoongame.shared.communication.query.QueryAction;
import ir.pint.soltoon.soltoongame.shared.communication.result.Result;
import ir.pint.soltoon.soltoongame.shared.data.Agent;
import ir.pint.soltoon.soltoongame.shared.data.Player;
import ir.pint.soltoon.soltoongame.shared.data.action.Action;
import ir.pint.soltoon.soltoongame.shared.data.map.GameBoard;
import ir.pint.soltoon.utils.clients.proxy.DefaultTimeAwareBean;

public class ClientExecutor extends DefaultTimeAwareBean implements ClientExecutorInterface {
    @Override
    public CommandAction queryAction(QueryAction query, Agent agent) {
        agent.setParentBean(this);
        CommandAction command = null;
        GameBoard gameBoard = ((QueryAction) query).getGameBoard();
        Action action = null;
        try {
            action = agent.getAction(gameBoard);
        } catch (Exception e) {
            e.printStackTrace();
        }
        command = new CommandAction(query.getId(), action);
        return command;
    }

    @Override
    public void initialize(Player player) {
        player.setParentBean(this);
        player.init();
    }

    @Override
    public void recieveResults(Result commandResult, Agent agent) {
        agent.setParentBean(this);
        agent.recieveResults(commandResult);
    }

    @Override
    public void lastThingsToDo(GameBoard gameBoard, Agent agent) {
        agent.setParentBean(this);
        agent.lastThingsToDo(gameBoard);
    }
}
