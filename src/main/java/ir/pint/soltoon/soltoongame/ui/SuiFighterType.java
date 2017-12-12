package ir.pint.soltoon.soltoongame.ui;

import ir.pint.soltoon.soltoongame.shared.data.map.FighterType;

public enum SuiFighterType {
    MUSKETEER("/musketeer.png", FighterType.MUSKETEER), BOMBER("/bomber.png", FighterType.CANNON), GIANT("/giant.png", FighterType.GIANT), TOWER("/castle.png", FighterType.CASTLE);

    SuiFighterType(String image, FighterType fighterType) {
        this.image = image;
        this.fighterType = fighterType;
    }

    public final String image;
    public final FighterType fighterType;

    public static SuiFighterType get(FighterType agentType) {
        for (SuiFighterType fighterUI : values()) {
            if (fighterUI.fighterType == agentType)
                return fighterUI;
        }
        return null;
    }
}
