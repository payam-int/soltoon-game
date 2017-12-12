package ir.pint.soltoon.soltoongame.shared.communication.query;

import ir.pint.soltoon.soltoongame.shared.data.map.GameBoard;

/**
 * Created by amirkasra on 9/30/2017 AD.
 */
public class QueryInitialize extends Query {
    private GameBoard gameBoard;

    public QueryInitialize(Long id) {
        super(id);
    }

    public QueryInitialize(Long id, GameBoard gameBoard) {
        super(id);
        this.gameBoard = gameBoard;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }
}
