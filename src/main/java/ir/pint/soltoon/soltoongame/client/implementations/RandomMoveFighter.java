package ir.pint.soltoon.soltoongame.client.implementations;

import ir.pint.soltoon.soltoongame.shared.agents.Khadang;
import ir.pint.soltoon.soltoongame.shared.actions.Action;
import ir.pint.soltoon.soltoongame.shared.actions.Move;
import ir.pint.soltoon.soltoongame.shared.map.Direction;
import ir.pint.soltoon.soltoongame.shared.map.KhadangType;
import ir.pint.soltoon.soltoongame.shared.map.Game;

public class RandomMoveFighter extends Khadang {

    public RandomMoveFighter(KhadangType type) {
        super(type);
    }

    @Override
    public void init(Game gameBoard) {

    }

    @Override
    public void lastThingsToDo(Game gameBoard) {
        System.out.println(":(");
    }

    @Override
    public Action getAction(Game gb) {
        Direction[] values = Direction.values();
        return new Move(values[((int) (values.length * Math.random()))]);
    }
}
