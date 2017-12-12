package ir.pint.soltoon.soltoongame.shared.data.map;


import java.util.*;

public class GameBoard {
    protected Cell[][] cells;

    protected int height, width;
    protected Map<Long, GameObject> gameObjectById;

    protected int currentRound;
    protected Long myId;


    protected Map<Long, Integer> moneyByPlayer = new HashMap<>();
    protected Map<Long, Integer> moneyPerTurnByPlayer;
    protected Map<Long, Integer> penaltyByPlayer;
    protected Map<Long, Long> playerByFighter;
    protected Map<Long, List<Long>> fightersByPlayer;
    protected Set<Long> playerIDs;



    public GameBoard() {
        this.gameObjectById = new HashMap<>();
        this.penaltyByPlayer = new HashMap<>();
        this.playerByFighter = new HashMap<>();
        this.moneyPerTurnByPlayer = new HashMap<>();
        this.fightersByPlayer = new HashMap<>();
        this.playerIDs = new TreeSet<>();
    }

    public void afterDeserialize() {
        for (Cell[] row : cells) {
            if (row == null || row.length == 0)
                continue;
            for (Cell cell : row) {
                cell.setGameObject(gameObjectById.getOrDefault(cell.gameObjectId, null));
                cell.init();
            }
        }
    }

    protected GameBoard(int h, int w) {
        this();
        this.height = h;
        this.width = w;
        this.cells = new Cell[width][height];

        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                this.cells[i][j] = new Cell(i, j);
    }

    public Long getMyId() {
        return this.myId;
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
        return gameObjectById.get(id);
    }

    public Long getPlayerIdByFighter(Long id) {
        return playerByFighter.get(id);
    }


    public int getMoneyById(Long id) {
        Integer res = moneyByPlayer.get(id);
        return (res == null) ? 0 : res;
    }

    public int getPenaltyByID(Long id) {
        Integer res = penaltyByPlayer.get(id);
        return (res == null) ? 0 : res;
    }

    public int getMoneyPerTurn(Long id) {
        Integer res = moneyPerTurnByPlayer.get(id);
        return ((res == null) ? 0 : res);
    }

    public Cell getCellByDirection(Cell cell, Direction dir) {
        int x = cell.getX() + dir.dx(), y = cell.getY() + dir.dy();
        if (x < 0 || x > width || y < 0 || y > width) return null;
        else return getCellByIndex(x, y);
    }

    public List<Long> getFightersByPlayer(Long id) {
        return fightersByPlayer.get(id);
    }

    public Set<Long> getPlayerIDs() {
        return playerIDs;
    }

    public int getCurrentRound() {
        return currentRound;
    }
}
