package ir.pint.soltoon.soltoongame.shared.data.action;

import server.CoreGameBoard;

public final class Nothing extends Action{
    @Override
    public boolean execute(CoreGameBoard gb) {
        return false;
    }
}