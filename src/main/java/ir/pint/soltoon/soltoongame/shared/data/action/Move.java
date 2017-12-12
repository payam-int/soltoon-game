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

        GameObject gameObject = gb.getObjectByID(gb.getMyId());

        if (gameObject == null)
            return true; //age yevaght GameObject nabud


        if (gameObject.getRemainingRestingTime() != 0)
            return true;


        Cell currentCell = gameObject.getCell();
        Cell newCell = gb.getCellByIndex(direction.dx() + currentCell.getX(), direction.dy() + currentCell.getY());


        if (newCell == null)
            return true;

        if (newCell.getGameObject() != null)
            return true;

        Cell.giveCellToObject(newCell, currentCell.getGameObject());

        gameObject.resetRestingTime();

        ResultStorage.addEvent(new AgentMoveEvent(gb.getMyId(), gb.getPlayerIdByFighter(gb.getMyId()), currentCell.getX(), currentCell.getY(), newCell.getX(), newCell.getY()));

        return false;
    }

    public Direction getDirection() {
        return direction;
    }
}
