package ir.pint.soltoon.soltoongame.shared.data.action;

import ir.pint.soltoon.soltoongame.server.CoreGameBoard;
import ir.pint.soltoon.soltoongame.shared.data.map.Cell;
import ir.pint.soltoon.soltoongame.shared.data.map.FighterType;
import ir.pint.soltoon.soltoongame.shared.data.map.GameObject;
import ir.pint.soltoon.soltoongame.shared.result.AgentAddEvent;
import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;
import ir.pint.soltoon.utils.shared.facades.uuid.UUID;

import java.util.Objects;

public class AddFighterSrv extends Action {
    private final FighterType type;
    private int x, y;

    public AddFighterSrv(FighterType type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public AddFighterSrv(AddFighter addFighter) {
        this.type = addFighter.getAI().getType();
        this.x = addFighter.getX();
        this.y = addFighter.getY();
    }

    @Override
    public boolean execute(CoreGameBoard gb, Object... extra) {
        if (gb.getObjectByID(gb.getMyId()) != null) {
            return true; //GameObject nabashe yevaght
        }
        if (gb.getCellByIndex(x, y).getGameObject() != null) {
            return true; // por nabashe yevaght
        }
        if (gb.getMoneyById(gb.getMyId()) - type.getCost() < 0) {
            return true; // pool bashe
        }

        boolean ok = true;
        for (GameObject o : gb.getGameObjectByIdMap().values()) {
            if (!Objects.equals(gb.getPlayerIdByFighter(o.getId()), gb.getMyId())) {
                if (gb.getCellByIndex(x, y).getDistance(o.getCell()) <= type.getShootingRange()) {
                    ok = false;
                }
            } else {

            }
        }

        if (!ok) return true;

        gb.decreaseMoneyByID(gb.getMyId(), type.getCost());

        gb.increasePenaltyById(gb.getMyId(), -type.getCreatePoint());

        long id = extra.length > 0 ? ((long) extra[0]) : UUID.getLong();

        GameObject gameObject = type.getFactory(id);
        Cell.giveCellToObject(gb.getCellByIndex(x, y), gameObject);
        gb.addObject(gameObject);
        ResultStorage.addEvent(new AgentAddEvent(id, x, y, type, type.getHP(), gb.getMyId(), type.getCreatePoint(), type.getCost()));

        return false;
    }
}
