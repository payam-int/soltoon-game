package ir.pint.soltoon.soltoongame.shared.result;

import ir.pint.soltoon.utils.shared.comminucation.ComRemoteInfo;
import ir.pint.soltoon.utils.shared.facades.result.DefaultEventLog;
import ir.pint.soltoon.utils.shared.facades.result.EventLog;

public class PlayerJoined extends DefaultEventLog {
    private long id;
    private ComRemoteInfo remoteInfo;

    public PlayerJoined(long id, ComRemoteInfo remoteInfo) {
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
