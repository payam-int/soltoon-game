package ir.pint.soltoon.soltoongame.shared.data.action;

import ir.pint.soltoon.soltoongame.server.CoreGameBoard;
import ir.pint.soltoon.soltoongame.shared.data.map.FighterType;
import ir.pint.soltoon.soltoongame.shared.data.map.GameObject;
import ir.pint.soltoon.soltoongame.shared.result.AgentAddEvent;
import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;
import ir.pint.soltoon.utils.shared.facades.uuid.UUID;

public class AddFighterType extends Action {
    private final FighterType type;
    private int x, y;

    public AddFighterType(FighterType type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public AddFighterType(AddFighter addFighter) {
        this.type = addFighter.getAI().getType();
        this.x = addFighter.getX();
        this.y = addFighter.getY();
    }

    @Override
    public boolean execute(CoreGameBoard gb, Object... extra) {
        if (gb.getObjectByID(gb.getMyID()) != null) return true; //GameObject nabashe yevaght
        if (gb.getCellByIndex(x, y).getGameObject() != null) return true; // por nabashe yevaght
        if (gb.getMoneyByID(gb.getMyID()) - type.getCost() < 0) return true; // pool bashe
        boolean ok = true;


        // @todo what is this ?!
        /*for (Long pid : gb.getPlayerIDs())
            if (pid!=gb.getMyID())
              for (Long id : gb.idsByPlayerID(pid)) {
                    GameObject o = gb.getObjectByID(id);
                    if (gb.getCellByIndex(x,y).getDistance(o.getCell()) <= AI.type.getShootingRange())
                        ok=false;
              }*/
        if (!ok) return true;

        gb.decreaseMoneyByID(gb.getMyID(), type.getCost());

        long id = extra.length > 0 ? ((long) extra[0]) : UUID.getLong();

        GameObject o = type.getFactory(id);
        CoreGameBoard.giveCellToObject(gb.getCellByIndex(x, y), o);
        gb.addObject(o);

        ResultStorage.addEvent(new AgentAddEvent(id, x, y, type, type.getHP(), gb.getMyID()));
        return false;
    }
}
