package ir.pint.soltoon.soltoongame.server.scenarios.helloWorld;

import ir.pint.soltoon.soltoongame.shared.agents.Soltoon;
import ir.pint.soltoon.soltoongame.shared.actions.Action;
import ir.pint.soltoon.soltoongame.shared.map.Game;

public class FirstScenario extends Soltoon {
    private int p = 0;
    private int fighters = 0;

    private int currentCell = 0;

    @Override
    public void init(Game gameBoard) {
        fighters = (int) ((0.05 + Math.random() * 0.1) * gameBoard.getMapWidth() * gameBoard.getMapHeight());


    }

    @Override
    public void lastThingsToDo(Game gameBoard) {

    }

    @Override
    public Action getAction(Game gb) {
        return null;
    }
}
