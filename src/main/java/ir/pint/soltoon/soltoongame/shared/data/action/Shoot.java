package ir.pint.soltoon.soltoongame.shared.data.action;

import ir.pint.soltoon.soltoongame.server.CoreGameBoard;
import ir.pint.soltoon.soltoongame.shared.data.map.*;
import ir.pint.soltoon.soltoongame.shared.result.AgentDamagedEvent;
import ir.pint.soltoon.soltoongame.shared.result.AgentDiedEvent;
import ir.pint.soltoon.soltoongame.shared.result.AgentShootEvent;
import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;

public final class Shoot extends Action {
    private final int x, y;

    public Shoot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean execute(CoreGameBoard gb, Object... extra) {
        GameObject o = gb.getObjectByID(gb.getMyID());
        FighterType type = o.getClass().getAnnotation(CorrespondingAttributes.class).value();
        Cell target = gb.getCellByIndex(x, y);
        if (target == null) return true;
        if (target.getDistance(o.getCell()) > type.getShootingRange()) return true;
        o.resetReloadingTime();
//        System.out.println(o.id + "-shoot-" + x + "," + y);

        Long player = gb.getPlayerByFighter().get(gb.getMyID());

        ResultStorage.addEvent(new AgentShootEvent(gb.getMyID(), player, o.getCell().getX(), o.getCell().getY(), x, y));


        GameObject dead = gb.ShootToCell(target, type.getShootingPower());

        if (dead != null) {
//            System.out.println(dead.id + "-killed");
            gb.increasePenaltyByID(gb.getownerByID(dead.id), dead.type.getPenalty());
//            System.out.println(gb.getMyID() + "-penalty-" + dead.type.getPenalty());

            Long killed = gb.getPlayerByFighter().get(dead.getId());

            ResultStorage.addEvent(new AgentDiedEvent(dead.getId(), dead.getCell().getX(), dead.getCell().getY(), dead.type.getPenalty(), killed, player));
        }
        return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
