package ir.pint.soltoon.soltoongame.ui;

import ir.pint.soltoon.soltoongame.FighterUI;
import ir.pint.soltoon.soltoongame.shared.GameConfiguration;
import ir.pint.soltoon.soltoongame.shared.data.Fighter;
import ir.pint.soltoon.soltoongame.shared.data.map.FighterType;
import ir.pint.soltoon.soltoongame.shared.result.*;
import ir.pint.soltoon.soltoongame.ui.actions.SuiActionAdd;
import ir.pint.soltoon.soltoongame.ui.actions.SuiActionMove;
import ir.pint.soltoon.soltoongame.ui.actions.SuiActionShoot;
import ir.pint.soltoon.soltoongame.ui.elements.SuiFighter;
import ir.pint.soltoon.soltoongame.ui.elements.SuiPlayer;
import ir.pint.soltoon.utils.shared.facades.result.EventLog;
import ir.pint.soltoon.utils.shared.facades.result.MetaLog;
import ir.pint.soltoon.utils.shared.facades.result.ResultHandler;

import java.util.concurrent.atomic.AtomicInteger;

public class SuiResultPipe implements ResultHandler {
    private SuiManager suiManager;

    private int playersJoined = 0;
    private AtomicInteger playerDependencies = new AtomicInteger(1);
    private AtomicInteger mapDependencies = new AtomicInteger(4);

    public SuiResultPipe(SuiManager suiManager) {
        this.suiManager = suiManager;
    }

    @Override
    public void addEvent(EventLog eventLog) {
        try {

            if (eventLog instanceof PlayerJoin) {
                PlayerJoin e = (PlayerJoin) eventLog;

                SuiPlayer suiPlayer = new SuiPlayer(e.getId(), e.getRemoteInfo());
                suiManager.addPlayer(suiPlayer);

                if (playerDependencies.decrementAndGet() < 1) {
                    suiManager.playerDependenciesResolved();
                }
            } else if (eventLog instanceof AgentAddEvent) {
                AgentAddEvent e = (AgentAddEvent) eventLog;

                SuiFighter suiFighter = new SuiFighter(FighterUI.get(e.getAgentType()), e.getX(), e.getY(), e.getAgent(), e.getPlayer());
                suiManager.addFighter(suiFighter);

                SuiActionAdd suiActionAdd = new SuiActionAdd(e.getX(), e.getY(), e.getAgent(), e.getPlayer());
                suiManager.addAction(suiActionAdd);
            } else if (eventLog instanceof AgentMoveEvent) {
                AgentMoveEvent e = (AgentMoveEvent) eventLog;

                SuiActionMove suiActionMove = new SuiActionMove(e.getFromX(), e.getFromY(), e.getAgent(), e.getPlayer(), e.getToX(), e.getToY());
                suiManager.addAction(suiActionMove);
            } else if (eventLog instanceof AgentShootEvent) {
                AgentShootEvent e = (AgentShootEvent) eventLog;

                SuiActionShoot suiActionShoot = new SuiActionShoot(e.getX(), e.getY(), e.getPlayer(), e.getTargetX(), e.getTargetY(), 100);
                suiManager.addAction(suiActionShoot);
            } else {
                System.out.println("but" + eventLog.getClass());
            }
//            else if (eventLog instanceof AgentAddEvent) {
//                SuiActionAdd suiActionAdd = new SuiActionAdd(((AgentAddEvent) eventLog).getX(), ((AgentAddEvent) eventLog).getY(), ((AgentAddEvent) eventLog).getAgent(), ((AgentAddEvent) eventLog).getPlayer());
//                SuiFighter suiFighter = new SuiFighter(FighterUI.get(((AgentAddEvent) eventLog).getAgentType()), ((AgentAddEvent) eventLog).getX(), ((AgentAddEvent) eventLog).getY(), ((AgentAddEvent) eventLog).getAgent(), ((AgentAddEvent) eventLog).getPlayer());
//
//                suiConfiguration.addFighter(suiFighter);
//                suiConfiguration.addAction(suiActionAdd);
//            } else if (eventLog instanceof AgentShootEvent) {
//                AgentShootEvent e = (AgentShootEvent) eventLog;
//                SuiActionShoot suiActionShoot = new SuiActionShoot(e.getX(), e.getY(), e.getPlayer(), e.getTargetX(), e.getTargetY(), 0);
//                suiConfiguration.addAction(suiActionShoot);
//            } else if (eventLog instanceof AgentMoveEvent) {
//                AgentMoveEvent e = (AgentMoveEvent) eventLog;
//                SuiActionMove suiActionShoot = new SuiActionMove(e.getFromX(), e.getFromY(), e.getAgent(), e.getPlayer(), e.getToX(), e.getToY());
//                suiConfiguration.addAction(suiActionShoot);
//            } else {
//                System.out.println(eventLog.getClass());
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addMeta(MetaLog metaLog) {

    }

    @Override
    public void putMisc(String s, Object o) {
        SuiConfiguration suiConfiguration = suiManager.getSuiConfiguration();
        System.out.println(s);
        if (s.equals("mapWidth")) {
            suiConfiguration.setBoardWidth(((int) o));
            mapDependencies.decrementAndGet();
        } else if (s.equals("mapHeight")) {
            suiConfiguration.setBoardHeight(((int) o));
            mapDependencies.decrementAndGet();
        } else if (s.equals("players")) {
            suiConfiguration.setPlayers(((int) o));
            mapDependencies.decrementAndGet();
        } else if (s.equals("rounds")) {
            suiConfiguration.setRounds(((int) o));
            mapDependencies.decrementAndGet();
        }

        if (mapDependencies.get() < 1)
            suiManager.mapDependenciesResloved();
    }

    @Override
    public void addException(Exception e) {

    }

    @Override
    public boolean flush() {
        return false;
    }
}
