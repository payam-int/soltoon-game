package ir.pint.soltoon.soltoongame.shared.data.map;

import ir.pint.soltoon.utils.shared.facades.json.Secure;
import ir.pint.soltoon.utils.shared.facades.json.SecureMap;

import java.io.Serializable;
import java.util.*;

@Secure
public class GameBoard implements Serializable {
    private Cell[][] cells;
    protected int height, width;
    protected Map<Long, GameObject> id2object;
    protected Long myID;

    @SecureMap(keys = Long.class, values = Integer.class)
    protected Map<Long, Integer> playerID2money;

    @SecureMap(keys = Long.class, values = Integer.class)
    protected Map<Long, Integer> playerID2moneyPerTurn;

    @SecureMap(keys = Long.class, values = Long.class)
    protected Map<Long, Long> id2owner;

    @SecureMap(keys = Long.class, values = Integer.class)
    protected Map<Long, Integer> playerID2penalty;


    protected Map<Long, List<Long>> playerID2ids;
    protected Set<Long> playerIDs;

    protected GameBoard(int h, int w) {

        height = h;
        width = w;
        cells = new Cell[width][height];
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                cells[i][j] = new Cell(i, j);
        id2object = new HashMap<>();
        playerID2money = new HashMap<>();
        playerID2penalty = new HashMap<>();
        id2owner = new HashMap<>();
        playerID2moneyPerTurn = new HashMap<>();
        playerID2ids = new HashMap<>();
        playerIDs = new TreeSet<>();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Cell getCellByIndex(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height)
            return cells[x][y];
        return null;
    }

    public GameObject getObjectByID(Long id) {
        return id2object.get(id);
    }

    public Long getownerByID(Long id) {
        return id2owner.get(id);
    }

    public Long getMyID() {
        return myID;
    }

    public int getMoneyByID(Long id) {
        Integer res = playerID2money.get(id);
        return (res == null) ? 0 : res;
    }

    public int getPenaltyByID(Long id) {
        Integer res = playerID2penalty.get(id);
        return (res == null) ? 0 : res;
    }

    public int getMoneyPerTurn(Long id) {
        Integer res = playerID2moneyPerTurn.get(id);
        return ((res == null) ? 0 : res);
    }

    public Cell getCellByDirection(Cell cell, Direction dir) {
        int x = cell.getX() + dir.dx(), y = cell.getY() + dir.dy();
        if (x < 0 || x > width || y < 0 || y > width) return null;
        else return getCellByIndex(x, y);
    }

    public List<Long> idsByPlayerID(Long id) {
        return playerID2ids.get(id);
    }

    public Set<Long> getPlayerIDs() {
        return playerIDs;
    }

    @Override
    public String toString() {
        return "GameBoard{" +
                ", height=" + height +
                ", width=" + width +
                ", id2object=" + id2object +
                ", myID=" + myID +
                ", playerID2money=" + playerID2money +
                ", playerID2moneyPerTurn=" + playerID2moneyPerTurn +
                ", id2owner=" + id2owner +
                ", playerID2penalty=" + playerID2penalty +
                ", playerID2ids=" + playerID2ids +
                ", playerIDs=" + playerIDs +
                '}';
    }
}
