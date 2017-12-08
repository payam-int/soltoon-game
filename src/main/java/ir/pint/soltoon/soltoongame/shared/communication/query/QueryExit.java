package ir.pint.soltoon.soltoongame.shared.communication.query;

import ir.pint.soltoon.soltoongame.shared.data.map.GameBoard;

public class QueryExit extends QueryAction {
    public QueryExit(Long id, GameBoard gameBoard) {
        super(id, gameBoard);
    }
}
