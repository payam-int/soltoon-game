package ir.pint.soltoon.soltoongame.client.implementations;

import ir.pint.soltoon.soltoongame.shared.agents.Soltoon;
import ir.pint.soltoon.soltoongame.shared.actions.Action;
import ir.pint.soltoon.soltoongame.shared.actions.AddKhadang;
import ir.pint.soltoon.soltoongame.shared.map.KhadangType;
import ir.pint.soltoon.soltoongame.shared.map.Game;

public class RandomPlayer extends Soltoon {
    private boolean created = false;

    @Override
    public void init(Game gameBoard) {

    }

    @Override
    public void lastThingsToDo(Game gameBoard) {

    }

    @Override
    public Action getAction(Game gb) {
        return new AddKhadang(new RandomMoveFighter(KhadangType.getRandomType()), ((int) (Math.random() * gb.getMapWidth())), ((int) (Math.random() * gb.getMapHeight())));
    }
}
