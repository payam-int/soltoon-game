package ir.pint.soltoon.soltoongame.shared.result;

public class RoundStartEvent extends Event {
    private int round = 0;

    public RoundStartEvent(int round) {
        super(EventType.ROUND_START);
        this.round = round;
    }

    public RoundStartEvent() {
        super(EventType.ROUND_START);
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }
}
