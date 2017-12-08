package ir.pint.soltoon.soltoongame.shared.result;

import ir.pint.soltoon.utils.shared.facades.result.DefaultEventLog;

public class AgentAdded extends DefaultEventLog {
    private long agentId;
    private long client;

    public AgentAdded() {
    }

    public AgentAdded(long agentId, long client) {
        this.agentId = agentId;
        this.client = client;
    }

    public long getAgentId() {
        return agentId;
    }

    public void setAgentId(long agentId) {
        this.agentId = agentId;
    }

    public long getClient() {
        return client;
    }

    public void setClient(long client) {
        this.client = client;
    }
}
