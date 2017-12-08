package ir.pint.soltoon.soltoongame.shared.data.action;

import ir.pint.soltoon.soltoongame.server.CoreGameBoard;
import ir.pint.soltoon.soltoongame.shared.data.map.Cell;
import ir.pint.soltoon.soltoongame.shared.data.map.Direction;
import ir.pint.soltoon.soltoongame.shared.data.map.GameObject;
import ir.pint.soltoon.soltoongame.shared.result.AgentMoveEvent;
import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;

public final class Move extends Action {
    private final Direction direction;

    public Move(Direction direction) {
        this.direction = direction;
    }

    @Override
    public boolean execute(CoreGameBoard gb, Object... extra) {
        GameObject o = gb.getObjectByID(gb.getMyID());
        if (o == null) return true; //age yevaght GameObject nabud
        if (o.getRemainingRestingTime() != 0) return true;
        Cell currentCell = o.getCell();
        Cell newCell = gb.getCellByIndex(direction.dx() + currentCell.getX(), direction.dy() + currentCell.getY());
        if (newCell == null) return true;
        if (newCell.getGameObject() != null)
            return true;
        else CoreGameBoard.giveCellToObject(newCell, currentCell.getGameObject());
        o.resetRestingTime();

        ResultStorage.addEvent(new AgentMoveEvent(gb.getMyID(), gb.getPlayerByFighter().get(gb.getMyID()), currentCell.getX(), currentCell.getY(), newCell.getX(), newCell.getY()));
        return false;
    }

    public Direction getDirection() {
        return direction;
    }
}
