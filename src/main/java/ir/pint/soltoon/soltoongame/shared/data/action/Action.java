package ir.pint.soltoon.soltoongame.shared.data.action;

import server.CoreGameBoard;

import java.io.Serializable;

public abstract class Action implements Serializable{
    public abstract boolean execute(CoreGameBoard gb);
}
