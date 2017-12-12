package payam;

import ir.pint.soltoon.soltoongame.shared.data.Fighter;
import ir.pint.soltoon.soltoongame.shared.data.action.Action;
import ir.pint.soltoon.soltoongame.shared.data.action.Move;
import ir.pint.soltoon.soltoongame.shared.data.action.Shoot;
import ir.pint.soltoon.soltoongame.shared.data.map.Direction;
import ir.pint.soltoon.soltoongame.shared.data.map.FighterType;
import ir.pint.soltoon.soltoongame.shared.data.map.GameBoard;

public class WiseAttacker extends Fighter {
    public WiseAttacker() {
        super(FighterType.MUSKETEER);
    }

    @Override
    public void init() {
        System.out.println(":)");
    }

    @Override
    public void lastThingsToDo(GameBoard gameBoard) {
        System.out.println(":(");
    }

    @Override
    public Action getAction(GameBoard gb) {
        return Math.random() < 0.5 ? new Shoot(1, 1) : new Move(Direction.up);
    }
}