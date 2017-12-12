package ir.pint.soltoon.soltoongame.server.scenarios;

import ir.pint.soltoon.soltoongame.shared.data.Player;
import ir.pint.soltoon.soltoongame.shared.data.action.Action;
import ir.pint.soltoon.soltoongame.shared.data.action.AddFighterSrv;
import ir.pint.soltoon.soltoongame.shared.data.map.FighterType;
import ir.pint.soltoon.soltoongame.shared.data.map.GameBoard;

public class FirstScenario extends Player {
    private int p = 0;
    private int fighters = 0;

    private int currentCell = 0;

    @Override
    public void init(GameBoard gameBoard) {
        fighters = (int) ((0.05 + Math.random() * 0.1) * gameBoard.getWidth() * gameBoard.getHeight());


    }

    @Override
    public void lastThingsToDo(GameBoard gameBoard) {

    }

    @Override
    public Action getAction(GameBoard gb) {

        if (fighters-- > 0)
            return new AddFighterSrv(FighterType.getRandomType(), ((int) (Math.random() * gb.getWidth())), ((int) (Math.random() * gb.getHeight())));
        else
            return null;
    }
}
