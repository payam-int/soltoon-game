package ir.pint.soltoon.soltoongame.ui.actions;

import ir.pint.soltoon.soltoongame.ui.SuiManager;
import ir.pint.soltoon.soltoongame.ui.elements.SuiFighter;
import ir.pint.soltoon.soltoongame.ui.elements.SuiGameBoard;

public class SuiActionAdd extends SuiAction {
    private Long id;

    public SuiActionAdd() {
    }

    public SuiActionAdd(int x, int y, Long id, Long player) {
        super(x, y, player);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public void apply(SuiManager suiManager) {
        SuiFighter fighter = suiManager.getFighter(id);
        SuiGameBoard gameBoard = suiManager.getGameBoard();
        fighter.setSuiManager(suiManager);
        fighter.rebound();
        gameBoard.add(fighter);
    }
}
