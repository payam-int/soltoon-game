package ir.pint.soltoon.soltoongame.shared.data.map;


/**
 * Created by amirkasra on 9/30/2017 AD.
 */


public enum FighterType {
    MUSKETEER, CANNON, GIANT, CASTLE;

    public Integer getHP() {
        switch (this) {
            case MUSKETEER:
                return 100;
            case CANNON:
                return 50;
            case GIANT:
                return 500;
            case CASTLE:
                return 2000;
        }
        return null;
    }

    public Integer getReloadingTime() {
        switch (this) {
            case CANNON:
                return 1;
            case GIANT:
                return 4;
            case CASTLE:
                return 1;
            case MUSKETEER:
                return 2;
        }
        return null;
    }

    public Integer getRestingTime() {
        switch (this) {
            case CANNON:
                return Integer.MAX_VALUE;
            case GIANT:
                return 2;
            case CASTLE:
                return Integer.MAX_VALUE;
            case MUSKETEER:
                return 1;
        }
        return null;
    }

    public GameObject getFactory(Long id) {

        switch (this) {
            case MUSKETEER:
                return new Musketeer(id);
            case CASTLE:
                return new Castle(id);
            case CANNON:
                return new Cannon(id);
            case GIANT:
                return new Giant(id);
        }
        return null;
    }

    public Integer getCost() {
        switch (this) {
            case CANNON:
                return 10;
            case GIANT:
                return 50;
            case CASTLE:
                return 1000;
            case MUSKETEER:
                return 20;
        }
        return null;
    }

    public Integer getShootingRange() {
        switch (this) {
            case MUSKETEER:
                return 5;
            case CANNON:
                return 3;
            case GIANT:
                return 2;
            case CASTLE:
                return 8;
        }
        return null;
    }

    public Integer getShootingPower() {
        switch (this) {
            case CASTLE:
                return 50;
            case MUSKETEER:
                return 5;
            // @todo add these
            case CANNON:
                return 5;
            case GIANT:
                return 5;
        }
        return 0;
    }

    public Integer getPenalty() {
        return getCost() / 2;
    }
    public Integer getCreatePoint() {
        return getCost() / 4;
    }

    public static FighterType getRandomType() {
        int i = (int) ((Math.random() - 1e-10) * values().length);
        return values()[i];
    }
}
