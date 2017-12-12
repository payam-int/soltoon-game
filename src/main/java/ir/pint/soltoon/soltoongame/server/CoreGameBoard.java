package ir.pint.soltoon.soltoongame.server;

import ir.pint.soltoon.soltoongame.shared.GameConfiguration;
import ir.pint.soltoon.soltoongame.shared.data.map.Cell;
import ir.pint.soltoon.soltoongame.shared.data.map.GameBoard;
import ir.pint.soltoon.soltoongame.shared.data.map.GameObject;
import ir.pint.soltoon.soltoongame.shared.result.AgentDamagedEvent;
import ir.pint.soltoon.utils.shared.facades.json.SerializeAs;
import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by amirkasra on 9/30/2017 AD.
 */

@SerializeAs(GameBoard.class)
public class CoreGameBoard extends GameBoard {
    public transient ArrayList<Long> recentlyKilledIDs;


    public CoreGameBoard(int h, int w) {
        super(h, w);
        recentlyKilledIDs = new ArrayList<>();
    }

    public void setRound(int r) {
        this.currentRound = r;
    }

    public ArrayList<Long> getRecentlyKilledIDs() {
        return recentlyKilledIDs;
    }

    public void setRecentlyKilledIDs(ArrayList<Long> recentlyKilledIDs) {
        this.recentlyKilledIDs = recentlyKilledIDs;
    }

    public Map<Long, Long> getPlayerByFighterMap() {
        return playerByFighter;
    }


    public void setPlayerByFighter(Map<Long, Long> playerByFighter) {
        this.playerByFighter = playerByFighter;
    }

    public void setMoneyPerTurn(Long id, int x) {
        this.moneyPerTurnByPlayer.put(id, x);
    }

    public void setPlayerMoney(Long id, int value) {
        moneyByPlayer.put(id, value);
    }

    public void timePassedForCurrentPlayer() {
        GameObject o = getObjectByID(getMyId());
        if (o == null) {
            moneyByPlayer.put(getMyId(), getMoneyById(myId) + getMoneyPerTurn(myId));
        } else {
            o.remainingReloadingTimeMM();
            o.remainingRestingTimeMM();
        }
    }

    public GameObject ShootToCell(Cell target, Integer amount) {
        GameObject o = target.getGameObject();
        if (o == null) return null;


        if (o.damageBy(amount)) {
            deleteObjectByID(o.id);
            recentlyKilledIDs.add(o.id);
            return o;
        }

        ResultStorage.addEvent(new AgentDamagedEvent(o.getId(), playerByFighter.get(o.getId()), getMyId(), amount, o.getHp(), o.getCell().getX(), o.getCell().getY()));
        return null;
    }

    private void deleteObjectByID(Long id) {
        GameObject o = gameObjectById.get(id);
        if (o == null) return;
        o.getCell().setGameObject(null);
        gameObjectById.remove(id);
        playerByFighter.remove(id);
        for (Long pid : fightersByPlayer.keySet())
            fightersByPlayer.get(pid).remove(o);
    }

    public void decreaseMoneyByID(Long id, int value) {
        if (value < 0) return;
        if (getMoneyById(id) - value < 0) value = getMoneyById(id);
        moneyByPlayer.put(id, getMoneyById(id) - value);
    }

    public void increasePenaltyById(Long id, int value) {
        penaltyByPlayer.put(id, getPenaltyByID(id) + value);
    }


    public void addObject(GameObject o) {
        gameObjectById.put(o.id, o);
        playerByFighter.put(o.id, myId);
        fightersByPlayer.get(getMyId()).add(o.id);
    }


    public Map<Long, GameObject> getGameObjectByIdMap() {
        return gameObjectById;
    }

    public void addPlayer(Long player) {
        playerIDs.add(player);
        if (!fightersByPlayer.containsKey(player))
            fightersByPlayer.put(player, new ArrayList<>());

        moneyByPlayer.put(player, GameConfiguration.PLAYERS_INITIAL_MONEY);
    }

    public void setMyId(Long myId) {
        this.myId = myId;
    }


    public Map<Long, Integer> getMoneyByPlayer() {
        return moneyByPlayer;
    }
}
