package payam;

import ir.pint.soltoon.soltoongame.shared.data.Player;
import ir.pint.soltoon.soltoongame.shared.data.action.Action;
import ir.pint.soltoon.soltoongame.shared.data.action.AddFighter;
import ir.pint.soltoon.soltoongame.shared.data.map.FighterType;
import ir.pint.soltoon.soltoongame.shared.data.map.GameBoard;

public class WisePlayer extends Player {
    private boolean created = false;

    @Override
    public void init() {

    }

    @Override
    public void lastThingsToDo(GameBoard gameBoard) {

    }

    @Override
    public Action getAction(GameBoard gb) {

//        returnTemporary(new AddFighter(new WiseAttacker(), 0, 0));



        if (FighterType.MUSKETEER.getCost() < gb.getMyMoney()) {
            return new AddFighter(new WiseAttacker(), 0, 0);
        }
        return null;
    }
}
