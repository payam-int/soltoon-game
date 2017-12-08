package ir.pint.soltoon.soltoongame.ai;

import ir.pint.soltoon.soltoongame.shared.data.Player;
import ir.pint.soltoon.soltoongame.shared.data.action.Action;
import ir.pint.soltoon.soltoongame.shared.data.action.AddFighter;
import ir.pint.soltoon.soltoongame.shared.data.action.Nothing;
import ir.pint.soltoon.soltoongame.shared.data.map.GameBoard;
import ir.pint.soltoon.soltoongame.shared.data.map.FighterType;

public class My extends Player {

    public My() {
    }

    @Override
    public void init() {

    }

    @Override
    public void lastThingsToDo(GameBoard gameBoard) {

    }

    @Override
    public Action getAction(GameBoard gb) {
        if (gb.getMoneyByID(gb.getMyID()) >= FighterType.MUSKETEER.getCost()) {

            if (gb.getCellByIndex(0, 0).gameObject == null)
                return new AddFighter(new SampleGhoulAgent(FighterType.MUSKETEER), 0, 0);
            else
                return new Nothing();
        } else
            return new Nothing();
    }


}
