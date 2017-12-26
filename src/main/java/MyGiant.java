import ir.pint.soltoon.soltoongame.shared.actions.Move;
import ir.pint.soltoon.soltoongame.shared.actions.Shoot;
import ir.pint.soltoon.soltoongame.shared.agents.Khadang;
import ir.pint.soltoon.soltoongame.shared.actions.Action;
import ir.pint.soltoon.soltoongame.shared.actions.Nothing;
import ir.pint.soltoon.soltoongame.shared.map.*;

import java.util.Collection;

public class MyGiant extends Khadang {

    public static Long target = null;

    public MyGiant() {
        super(KhadangType.GIANT);
    }

    @Override
    public void init(Game gameBoard) {
        System.out.println("Hello sir");
    }

    @Override
    public void lastThingsToDo(Game gameBoard) {
        System.out.println("Bye Sir");
    }

    @Override
    public Action getAction(Game gameBoard) {
        Collection<GameKhadang> values = gameBoard.getKhadangs().values();


        GameKhadang me = gameBoard.getKhadang(getId());
        Cell mecell = me.getCell();

        if (target == null || gameBoard.getKhadang(target) == null) {
            target = null;

            for (GameKhadang v : values) {
//                System.out.println(v.getOwner());
                if (!v.getOwner().equals(me.getOwner())) {

                    target = v.getId();
//                    System.out.println("Target found");
                    break;
                }
            }
        }

        if (target == null)
            return null;

        GameKhadang khadang = gameBoard.getKhadang(target);
//            System.out.println("going to target");
            Cell cell = khadang.getCell();
            if (cell.getDistance(mecell) > 1) {
                Direction d = Direction.LEFT;
                if (cell.getX() > mecell.getX())
                    d = Direction.RIGHT;
                else if (cell.getY() > mecell.getY())
                    d = Direction.DOWN;
                else if (cell.getY() < mecell.getY())
                    d = Direction.UP;
                return new Move(d);
            } else {
//                System.out.println("Shooting target");
                return new Shoot(cell.getX(), cell.getY());
            }



    }
}
