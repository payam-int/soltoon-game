package ir.pint.soltoon.soltoongame.shared.data.action;

import ir.pint.soltoon.soltoongame.server.CoreGameBoard;
import ir.pint.soltoon.utils.shared.facades.json.Secure;

import java.io.Serializable;

@Secure
public abstract class Action implements Serializable{
    public abstract boolean execute(CoreGameBoard gb, Object... meta);
}
