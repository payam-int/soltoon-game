package ir.pint.soltoon.soltoongame.server.manager;

import ir.pint.soltoon.soltoongame.shared.map.Game;
import ir.pint.soltoon.soltoongame.shared.map.GameKhadang;
import ir.pint.soltoon.soltoongame.shared.map.GameSoltoon;
import ir.pint.soltoon.utils.shared.facades.json.SerializeAs;

import java.util.LinkedList;

@SerializeAs(GameSoltoon.class)
public class ManagerGameSoltoon extends GameSoltoon {
    protected Integer moneyPerTurn;
    protected Integer money;

    public int getMoney() {
        return money;
    }


    public ManagerGameSoltoon(Long id) {
        super(id);
    }

    public ManagerGameSoltoon(Long id, Integer moneyPerTurn, Integer money) {
        super(id);
        this.moneyPerTurn = moneyPerTurn;
        this.money = money;
    }

    public ManagerGameSoltoon(Long id, int score, Integer moneyPerTurn, Integer money) {
        super(id, score);
        this.moneyPerTurn = moneyPerTurn;
        this.money = money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setMoneyPerTurn(int moneyPerTurn) {
        this.moneyPerTurn = moneyPerTurn;
    }

    public int getMoneyPerTurn() {
        return moneyPerTurn;
    }

    public void removeKhadang(GameKhadang gameKhadang) {
        khadangsId.remove(gameKhadang.getId());
        khadangs.remove(gameKhadang);
    }


    public void changeMoney(int value) {
        if (money + value < 0)
            money = 0;
        else
            money += value;
    }

    public void changeScore(int value) {
        score += value;
    }


    public void passTurn() {
        money += moneyPerTurn;
    }

    @Override
    public void setGame(Game game) {
        super.setGame(game);
    }

    public void addKhadang(ManagerGameKhadang khadang) {
        if (khadangs == null)
            this.khadangs = new LinkedList<>();

        khadangsId.add(khadang.getId());
        khadangs.add(khadang);
    }
}
