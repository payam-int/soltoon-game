package ir.pint.soltoon.soltoongame.shared.result;

import ir.pint.soltoon.soltoongame.shared.data.map.FighterType;

public class AgentAddEvent extends Event {
    private long agent;
    private FighterType agentType;
    private int hp;
    private int x, y;
    private long player;

    public AgentAddEvent(long agent, int x, int y, FighterType agentType, int hp, long player) {
        super(EventType.AGENT_ADD);
        this.agent = agent;
        this.x = x;
        this.y = y;
        this.agentType = agentType;
        this.player = player;
        this.hp = hp;
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

    public FighterType getAgentType() {
        return agentType;
    }

    public void setAgentType(FighterType agentType) {
        this.agentType = agentType;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
