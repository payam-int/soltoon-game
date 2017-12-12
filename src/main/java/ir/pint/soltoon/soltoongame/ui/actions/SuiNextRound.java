package ir.pint.soltoon.soltoongame.ui.actions;

import ir.pint.soltoon.soltoongame.ui.SuiManager;
import ir.pint.soltoon.soltoongame.ui.elements.SuiPlayer;

import java.util.Hashtable;
import java.util.Map;

public class SuiNextRound implements SuiStep {
    private Map<Long, Integer> playersMoney;
    private int round;
    private Map<Long, Integer> prevPlayersMoney = new Hashtable<>();

    public SuiNextRound(int round, Map<Long, Integer> playersMoney) {
        this.round = round;
        this.playersMoney = playersMoney;
    }

    @Override
    public void apply(SuiManager suiManager) {
        for (Long player : playersMoney.keySet()) {
            SuiPlayer p = suiManager.getPlayer(player);
            if (p != null) {
                prevPlayersMoney.put(player, p.getMoney());
                p.setMoney(playersMoney.get(player));
            }
        }
    }

    @Override
    public void revert(SuiManager suiManager) {
        for (Long player : prevPlayersMoney.keySet()) {
            SuiPlayer p = suiManager.getPlayer(player);
            if (p != null) {
                p.setMoney(prevPlayersMoney.get(player));
            }
        }
    }
}
