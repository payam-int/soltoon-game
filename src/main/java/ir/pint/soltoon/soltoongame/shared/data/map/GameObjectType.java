package ir.pint.soltoon.soltoongame.shared.data.map;

/**
 * Created by amirkasra on 9/30/2017 AD.
 */
public enum GameObjectType {
    musketeer, bomber, giant
    , tower, mortar, tesla, inferno;

    public Integer getHP() {
        switch (this) {
            case musketeer:
                return 100;
            case bomber:
                return 50;
            case giant:
                return 500;
            case tower:
                return 2000;
            case mortar:
                return 300;
            case tesla:
                return 300;
            case inferno:
                return 300;
        }
        return null;
    }

    public Integer getReloadingTime() {
        switch (this) {
            case bomber:
                return 1;
            case giant:
                return 4;
            case tower:
                return 1;
            case musketeer:
                return 2;
            case mortar:
                return 5;
            case tesla:
                return 2;
            case inferno:
                return 0;
        }
        return null;
    }

    public Integer getRestingTime() {
        switch (this) {
            case bomber:
                return 0;
            case giant:
                return 2;
            case tower:
                return Integer.MAX_VALUE;
            case musketeer:
                return 1;
            case mortar:
                return Integer.MAX_VALUE;
            case tesla:
                return Integer.MAX_VALUE;
            case inferno:
                return Integer.MAX_VALUE;
        }
        return null;
    }

    public GameObject getFactory(Long id) {
        switch (this) {
            case musketeer:
                return new Giant(id);
            case tower:
                return new Tower(id);
        }
        return null;
    }

    public Integer getCost() {
        switch (this) {
            case bomber:
                return 10;
            case giant:
                return 50;
            case tower:
                return 1000;
            case musketeer:
                return 20;
            case mortar:
                return 200;
            case tesla:
                return 150;
            case inferno:
                return 300;
        }
        return null;
    }

    public Integer getShootingRange() {
        switch (this) {
            case musketeer:
                return 5;
            case bomber:
                return 3;
            case giant:
                return 2;
            case tower:
                return 8;
            case mortar:
                return 6;
            case tesla:
                return 4;
            case inferno:
                return 5;
        }
        return null;
    }

    public Integer getShootingPower() {
        switch (this) {
            case tower:
               return  50;
            case musketeer:
                return 5;
        }
        return null;
    }

    public Integer getPenalty() {
        return getCost()/2;
    }
}
