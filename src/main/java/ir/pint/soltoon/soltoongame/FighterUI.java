package ir.pint.soltoon.soltoongame;

import ir.pint.soltoon.soltoongame.shared.data.map.FighterType;

public enum FighterUI {
    MUSKETEER("/musketeer.png", FighterType.MUSKETEER), BOMBER("/bomber.png", FighterType.BOMBER), GIANT("/giant.png", FighterType.GIANT), TOWER("/castle.png", FighterType.TOWER);

    FighterUI(String image, FighterType fighterType) {
        this.image = image;
        this.fighterType = fighterType;
    }

    public final String image;
    public final FighterType fighterType;

    public static FighterUI get(FighterType agentType) {
        for (FighterUI fighterUI : values()) {
            if (fighterUI.fighterType == agentType)
                return fighterUI;
        }
        return null;
    }
}
