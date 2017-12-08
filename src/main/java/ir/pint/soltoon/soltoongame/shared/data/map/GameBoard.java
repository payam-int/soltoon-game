package ir.pint.soltoon.soltoongame.shared.data.map;

import ir.pint.soltoon.utils.shared.facades.json.Secure;

import java.io.Serializable;
import java.util.*;

@Secure
public class GameBoard {
    private Cell[][] cells;
    protected int height, width;
    protected Map<Long, GameObject> id2object;
    protected Long myID;
    protected int myMoney;

    protected int round;

    private Long playerId;

    protected Map<Long, Integer> playerID2money = new HashMap<>();

    protected Map<Long, Integer> playerID2moneyPerTurn;

    protected Map<Long, Long> id2owner;

    protected Map<Long, Integer> playerID2penalty;


    protected Map<Long, List<Long>> playerID2ids;
    protected Set<Long> playerIDs;


    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }
    public GameBoard() {

    }

    public void init() {
        for (Cell[] row : cells) {
            if (row == null || row.length == 0)
                continue;

            for (Cell cell : row) {
                cell.setGameObject(id2object.getOrDefault(cell.gameObjectId, null));
                cell.init();
            }
        }
    }

    protected GameBoard(int h, int w) {

        height = h;
        width = w;
        cells = new Cell[width][height];
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                cells[i][j] = new Cell(i, j);
        id2object = new HashMap<>();
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

    public Map<Long, GameObject> getId2object() {

        return id2object;
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

    public int getMyMoney() {
        return myMoney;
    }

    public void setMyMoney(int myMoney) {
        this.myMoney = myMoney;
    }

    public int getRound() {
        return round;
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
