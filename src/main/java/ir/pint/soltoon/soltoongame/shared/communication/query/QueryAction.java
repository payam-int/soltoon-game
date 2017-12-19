package ir.pint.soltoon.soltoongame.shared.communication.query;

import ir.pint.soltoon.soltoongame.shared.map.Game;

/**
 * Created by amirkasra on 9/28/2017 AD.
 */
public class QueryAction extends Query {
    private Game gameBoard;
    public QueryAction(Long id, Game gameBoard) {
        super(id);
        this.gameBoard = gameBoard;
    }

    public Game getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(Game gameBoard) {
        this.gameBoard = gameBoard;
    }
}
