package ir.pint.soltoon.soltoongame.shared.data;

import shared.data.action.Action;
import shared.data.map.GameBoard;

import java.io.Serializable;
import java.util.Random;

public abstract class Agent implements Serializable{
    public Long id;

    protected Agent() {
        this.id = Math.abs(new Random().nextLong());
    }

    protected Agent(Long id) {
        this.id = id;
    }

    public abstract void init();
    public abstract void lastThingsToDo(GameBoard gameBoard);
    public abstract Action getAction(GameBoard gb);
}
