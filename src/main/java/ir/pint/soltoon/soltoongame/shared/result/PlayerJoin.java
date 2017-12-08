package ir.pint.soltoon.soltoongame.shared.result;

import ir.pint.soltoon.utils.shared.comminucation.ComRemoteInfo;

public class PlayerJoin extends Event {
    private long id;
    private ComRemoteInfo remoteInfo;

    public PlayerJoin(long id, ComRemoteInfo remoteInfo) {
        super(EventType.PLAYER_JOIN);

        this.id = id;
        this.remoteInfo = remoteInfo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ComRemoteInfo getRemoteInfo() {
        return remoteInfo;
    }

    public void setRemoteInfo(ComRemoteInfo remoteInfo) {
        this.remoteInfo = remoteInfo;
    }
}
