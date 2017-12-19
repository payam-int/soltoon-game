package ir.pint.soltoon.soltoongame.shared.actions;

import ir.pint.soltoon.soltoongame.server.manager.ManagerCell;
import ir.pint.soltoon.soltoongame.server.manager.ManagerGame;
import ir.pint.soltoon.soltoongame.server.manager.ManagerGameKhadang;
import ir.pint.soltoon.soltoongame.server.manager.ManagerGameSoltoon;
import ir.pint.soltoon.soltoongame.shared.agents.Khadang;
import ir.pint.soltoon.soltoongame.shared.map.KhadangType;
import ir.pint.soltoon.soltoongame.shared.map.GameKhadang;
import ir.pint.soltoon.soltoongame.shared.result.AgentAddEvent;
import ir.pint.soltoon.utils.shared.facades.reflection.PrivateCall;
import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;

/**
 *
 */
public final class AddKhadang extends Action {
    private transient Khadang khadang;
    private int x, y;
    private KhadangType type;

    public AddKhadang(Khadang khadang, int x, int y) {
        this.khadang = khadang;
        this.x = x;
        this.y = y;
        this.type = khadang.getType();
    }

    protected boolean execute(ManagerGame gameBoard, ManagerGameSoltoon soltoon, Long id) {
        if (type == null) {
            return true;
        }

        ManagerCell cell = (ManagerCell) gameBoard.getCell(x, y);

        if (cell == null || cell.getKhadang() != null) {
            return true; // por nabashe yevaght
        }

        if (soltoon.getMoney() - type.getCost() < 0) {
            return true; // pool bashe
        }

        boolean ok = true;
        for (GameKhadang khadang : gameBoard.getKhadangs().values()) {
            if (cell.getDistance(khadang.getCell()) <= type.getShootingRange()) {
                ok = false;
            }
        }

        if (!ok) return true;

        soltoon.changeMoney(type.getCost());
        soltoon.changeScore(type.getCreatePoint());

        GameKhadang khadang = PrivateCall.call(type, "getFactory", id, soltoon.getId());

        if (khadang == null) {
            throw new Error("Server Error!");
        }


        gameBoard.addKhadangToMap(((ManagerGameKhadang) khadang), cell);
        ResultStorage.addEvent(new AgentAddEvent(id, x, y, type, type.getHealth(), soltoon.getId(), type.getCreatePoint(), type.getCost()));
        return false;
    }

    public Khadang getKhadang() {
        return khadang;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


}
