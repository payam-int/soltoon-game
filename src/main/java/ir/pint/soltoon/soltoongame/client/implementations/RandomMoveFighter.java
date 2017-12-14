package ir.pint.soltoon.soltoongame.client.implementations;

import ir.pint.soltoon.soltoongame.shared.data.Fighter;
import ir.pint.soltoon.soltoongame.shared.data.action.Action;
import ir.pint.soltoon.soltoongame.shared.data.action.Move;
import ir.pint.soltoon.soltoongame.shared.data.map.Direction;
import ir.pint.soltoon.soltoongame.shared.data.map.FighterType;
import ir.pint.soltoon.soltoongame.shared.data.map.GameBoard;

public class RandomMoveFighter extends Fighter {

    public RandomMoveFighter(FighterType type) {
        super(type);
    }

    @Override
    public void init(GameBoard gameBoard) {

    }

    @Override
    public void lastThingsToDo(GameBoard gameBoard) {
        System.out.println(":(");
    }

    @Override
    public Action getAction(GameBoard gb) {
        Direction[] values = Direction.values();
        return new Move(values[((int) (values.length * Math.random()))]);
    }
}
