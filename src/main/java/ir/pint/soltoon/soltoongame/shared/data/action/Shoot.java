package ir.pint.soltoon.soltoongame.shared.data.action;

import ir.pint.soltoon.soltoongame.server.CoreGameBoard;
import ir.pint.soltoon.soltoongame.shared.data.map.*;
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

        GameObject gameObject = gb.getObjectByID(gb.getMyId());

        if (gameObject == null)
            return true;

        FighterType type = gameObject.getType();

        Cell target = gb.getCellByIndex(x, y);

        if (target == null)
            return true;

        if (target.getDistance(gameObject.getCell()) > type.getShootingRange())
            return true;


        gameObject.resetReloadingTime();

        Long player = gb.getPlayerIdByFighter(gb.getMyId());
        int fromX = gameObject.getCell().getX();
        int fromY = gameObject.getCell().getY();

        ResultStorage.addEvent(new AgentShootEvent(gb.getMyId(), player, fromX, fromY, this.x, this.y));


        GameObject dead = gb.ShootToCell(target, type.getShootingPower());

        if (dead != null) {
            gb.increasePenaltyById(gb.getPlayerIdByFighter(dead.id), dead.type.getPenalty());

            Long deadFighterOwner = gb.getPlayerIdByFighter(dead.getId());
            Long killer = player;

            ResultStorage.addEvent(new AgentDiedEvent(dead.getId(), x, y, dead.type.getPenalty(), deadFighterOwner, killer));
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
