package ir.pint.soltoon.soltoongame.client;

import ir.pint.soltoon.soltoongame.shared.communication.command.CommandAction;
import ir.pint.soltoon.soltoongame.shared.communication.query.QueryAction;
import ir.pint.soltoon.soltoongame.shared.communication.result.Result;
import ir.pint.soltoon.soltoongame.shared.data.Agent;
import ir.pint.soltoon.soltoongame.shared.data.Player;
import ir.pint.soltoon.soltoongame.shared.data.map.GameBoard;
import ir.pint.soltoon.utils.clients.proxy.TimeAwareProxyInterface;

public interface ClientExecutorInterface extends TimeAwareProxyInterface {
    CommandAction queryAction(QueryAction query, Agent agent);

    void recieveResults(Result commandResult, Agent agent);

    void initialize(Player player);

    void lastThingsToDo(GameBoard gameBoard, Agent agent);
}
