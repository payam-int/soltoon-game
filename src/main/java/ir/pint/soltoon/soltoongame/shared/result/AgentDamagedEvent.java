package ir.pint.soltoon.soltoongame.shared.result;

public class AgentDamagedEvent extends Event {
    private long agent;
    private long player;
    private long attacker;
    private int damage;
    private int finalHp;

    public AgentDamagedEvent(long agent, long player, long attacker, int damage, int finalHp) {
        super(EventType.AGENT_DAMAGED);
        this.agent = agent;
        this.player = player;
        this.attacker = attacker;
        this.damage = damage;
        this.finalHp = finalHp;
    }

    public long getAgent() {
        return agent;
    }

    public void setAgent(long agent) {
        this.agent = agent;
    }

    public long getPlayer() {
        return player;
    }

    public void setPlayer(long player) {
        this.player = player;
    }


    public long getAttacker() {
        return attacker;
    }

    public void setAttacker(int attacker) {
        this.attacker = attacker;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getFinalHp() {
        return finalHp;
    }

    public void setFinalHp(int finalHp) {
        this.finalHp = finalHp;
    }
}
