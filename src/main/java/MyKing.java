import ir.pint.soltoon.soltoongame.shared.GameConfiguration;
import ir.pint.soltoon.soltoongame.shared.actions.AddKhadang;
import ir.pint.soltoon.soltoongame.shared.agents.Soltoon;
import ir.pint.soltoon.soltoongame.shared.actions.Action;
import ir.pint.soltoon.soltoongame.shared.actions.Nothing;
import ir.pint.soltoon.soltoongame.shared.map.Game;
import ir.pint.soltoon.soltoongame.shared.map.GameKhadang;
import ir.pint.soltoon.soltoongame.shared.map.GameSoltoon;

import java.util.Map;


public class MyKing extends Soltoon {


    @Override
    public void init(Game gameBoard) {

    }

    @Override
    public void lastThingsToDo(Game gameBoard) {

    }

    @Override
    public Action getAction(Game gameBoard) {
        System.out.println(gameBoard);
        return null;
    }
}

