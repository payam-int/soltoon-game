package ir.pint.soltoon.soltoongame.client;

import ir.pint.soltoon.soltoongame.shared.GameConfiguration;
import ir.pint.soltoon.soltoongame.shared.Platform;
import ir.pint.soltoon.soltoongame.shared.communication.command.*;
import ir.pint.soltoon.soltoongame.shared.communication.query.*;
import ir.pint.soltoon.soltoongame.shared.communication.result.Result;
import ir.pint.soltoon.soltoongame.shared.communication.result.ResultAction;
import ir.pint.soltoon.soltoongame.shared.communication.result.ResultAddFighterAction;
import ir.pint.soltoon.soltoongame.shared.communication.result.Status;
import ir.pint.soltoon.soltoongame.shared.data.Agent;
import ir.pint.soltoon.soltoongame.shared.data.Player;
import ir.pint.soltoon.soltoongame.shared.data.action.AddFighter;
import ir.pint.soltoon.soltoongame.shared.data.action.AddFighterType;
import ir.pint.soltoon.utils.clients.proxy.ProxyTimeLimit;
import ir.pint.soltoon.utils.clients.proxy.TimeAwareBeanProxy;
import ir.pint.soltoon.utils.clients.proxy.TimeoutPolicy;
import ir.pint.soltoon.utils.shared.comminucation.ComInputStream;
import ir.pint.soltoon.utils.shared.comminucation.ComOutputStream;
import ir.pint.soltoon.utils.shared.comminucation.Comminucation;
import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ClientManager {

    private Map<Long, Agent> agents = new HashMap<>();

    private Comminucation comminucation;
    private Class<? extends Player> player;

    public ClientManager(Class<? extends Player> player, Comminucation comminucation) {
        this.player = player;
        this.comminucation = comminucation;
    }

    public void run() {

        ClientExecutor clientExecutor = new ClientExecutor();
        ProxyTimeLimit proxyTimeLimit = new ProxyTimeLimit();
        ClientExecutorInterface proxifiedClientExecutor = null;
        try {
            proxifiedClientExecutor = TimeAwareBeanProxy.createBean(clientExecutor, ClientExecutorInterface.class, proxyTimeLimit);
        } catch (Exception e) {
            ResultStorage.addException(e);
            Platform.exit(Platform.FATAL_ERROR);
        }

        ComInputStream comInputStream = comminucation.getObjectInputStream();
        ComOutputStream comOutputStream = comminucation.getObjectOutputStream();

        int errors = 0;
        Query query = null;
        try {
            while (true) {
                try {
                    query = ((Query) comInputStream.readObject());
                    errors = 0;
                } catch (IOException e) {
                    ResultStorage.addException(e);
                    errors++;
                    if (errors > 15)
                        Platform.exit(Platform.CONNECTION_ERROR);
                    e.printStackTrace();
                }


                if (query == null)
                    continue;

                if (query instanceof QueryExit) {
                    Platform.exit(Platform.OK);
                }

                Long id = query.getId();


                Command command = null;
                Agent agent = null;
                Object extra = null;
                if (query instanceof QueryAction) {

                    agent = agents.get(id);
                    if (agent != null) {
                        proxyTimeLimit.setTimeLimit(GameConfiguration.QUERY_TIME);
                        proxyTimeLimit.setExtraTimeLimit(GameConfiguration.QUERY_EXTRA_TIME);
                        CommandAction commandAction = proxifiedClientExecutor.queryAction(((QueryAction) query), agent);
                        command = commandAction;
                        if (commandAction.getAction() instanceof AddFighter) {
                            AddFighter action = (AddFighter) commandAction.getAction();
                            extra = action.getAI();
                            command = new CommandAction(id, new AddFighterType(action));
                        }

                    }
                } else if (query instanceof QueryInitialize) {
                    try {
                        Player player = this.player.newInstance();
                        agent = player;
                        player.setId(query.getId());
                        addAgent(player, query.getId());

                        proxyTimeLimit.setTimeLimit(GameConfiguration.QUERY_INITIALIZE_TIME);
                        proxyTimeLimit.setExtraTimeLimit(1);

                        proxifiedClientExecutor.initialize(player);

                        command = new CommandInitialize(query.getId());
                    } catch (Exception e) {
                        ResultStorage.addException(e);
                        e.printStackTrace();
                    }
                } else if (query instanceof QueryFinalize) {
                    agent = agents.get(id);
                    proxyTimeLimit.setTimeLimit(GameConfiguration.QUERY_FINALIZE_TIME);
                    proxyTimeLimit.setExtraTimeLimit(GameConfiguration.QUERY_FINALIZE_EXTRA_TIME);
                    proxifiedClientExecutor.lastThingsToDo(((QueryFinalize) query).getGameBoard(), agent);
                    removeAgent(id);
                    command = new CommandFinalize(id);
                }
                if (command == null)
                    command = new CommandNothing();

                comOutputStream.writeObject(command);


                Result commandResult = (Result) comInputStream.readObject();
                postProcessCommand(command, commandResult, extra);

                if (agent != null)
                    proxifiedClientExecutor.recieveResults(commandResult, agent);
            }
        } catch (Exception e) {
            ResultStorage.addException(e);
            Platform.exit(Platform.GENERAL_ERROR);
        }

    }

    private void removeAgent(Long id) {
        agents.remove(id);
    }

    private void addAgent(Agent agent, Long id) {
        agents.put(id, agent);
    }

    private void postProcessCommand(Command command, Result commandResult, Object extra) {
        if (command instanceof CommandAction && ((CommandAction) command).getAction() instanceof AddFighterType) {
            if (commandResult instanceof ResultAddFighterAction && commandResult.getStatus() == Status.SUCCESS) {
                addAgent(((Agent) extra), ((ResultAddFighterAction) commandResult).getFighterId());
            }
        }

    }

}
