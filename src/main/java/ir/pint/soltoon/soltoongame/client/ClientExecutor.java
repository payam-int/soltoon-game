package ir.pint.soltoon.soltoongame.client;

import ir.pint.soltoon.soltoongame.shared.communication.command.CommandAction;
import ir.pint.soltoon.soltoongame.shared.communication.query.QueryAction;
import ir.pint.soltoon.soltoongame.shared.communication.result.Result;
import ir.pint.soltoon.soltoongame.shared.agents.Agent;
import ir.pint.soltoon.soltoongame.shared.agents.Soltoon;
import ir.pint.soltoon.soltoongame.shared.actions.Action;
import ir.pint.soltoon.soltoongame.shared.map.Game;
import ir.pint.soltoon.utils.clients.proxy.DefaultTimeAwareBean;

public class ClientExecutor extends DefaultTimeAwareBean implements ClientExecutorInterface {
    @Override
    public CommandAction queryAction(QueryAction query, Agent agent) {
        agent.setParentBean(this);
        agent.setId(query.getId());
        CommandAction command = null;
        Game gameBoard = ((QueryAction) query).getGameBoard();
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
    public void initialize(Soltoon player, Game gameBoard) {
        player.setParentBean(this);
        player.init(gameBoard);
    }

    @Override
    public void recieveResults(Result commandResult, Agent agent) {
        agent.setParentBean(this);
        agent.recieveResults(commandResult);
    }

    @Override
    public void lastThingsToDo(Game gameBoard, Agent agent) {
        agent.setParentBean(this);
        agent.lastThingsToDo(gameBoard);
    }
}
