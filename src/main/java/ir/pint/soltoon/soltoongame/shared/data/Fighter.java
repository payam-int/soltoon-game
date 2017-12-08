package ir.pint.soltoon.soltoongame.shared.data;

import ir.pint.soltoon.soltoongame.shared.data.map.FighterType;

/**
 * Created by amirkasra on 9/30/2017 AD.
 */
public abstract class Fighter extends Agent {
    final private FighterType type;



    public Fighter(FighterType type) {
        this.type=type;
    }

    public FighterType getType() {
        return type;
    }
}
