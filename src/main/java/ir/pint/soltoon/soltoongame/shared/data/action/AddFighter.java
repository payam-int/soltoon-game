package ir.pint.soltoon.soltoongame.shared.data.action;

import ir.pint.soltoon.soltoongame.server.CoreGameBoard;
import ir.pint.soltoon.soltoongame.shared.data.Fighter;
import ir.pint.soltoon.soltoongame.shared.data.map.GameObject;

/**
 *
 */
public final class AddFighter extends Action {
    private final Fighter AI;
    private final int x, y;

    public AddFighter(Fighter AI, int x, int y) {
        this.AI = AI;
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean execute(CoreGameBoard gb, Object... extra) {
        return false;
    }

    public Fighter getAI() {
        return AI;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
