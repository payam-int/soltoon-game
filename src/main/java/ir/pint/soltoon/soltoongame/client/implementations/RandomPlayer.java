package ir.pint.soltoon.soltoongame.client.implementations;

import ir.pint.soltoon.soltoongame.shared.data.Player;
import ir.pint.soltoon.soltoongame.shared.data.action.Action;
import ir.pint.soltoon.soltoongame.shared.data.action.AddFighter;
import ir.pint.soltoon.soltoongame.shared.data.map.FighterType;
import ir.pint.soltoon.soltoongame.shared.data.map.GameBoard;

public class RandomPlayer extends Player {
    private boolean created = false;

    @Override
    public void init(GameBoard gameBoard) {

    }

    @Override
    public void lastThingsToDo(GameBoard gameBoard) {

    }

    @Override
    public Action getAction(GameBoard gb) {
        return new AddFighter(new RandomMoveFighter(FighterType.getRandomType()), ((int) (Math.random() * gb.getWidth())), ((int) (Math.random() * gb.getHeight())));
    }
}
