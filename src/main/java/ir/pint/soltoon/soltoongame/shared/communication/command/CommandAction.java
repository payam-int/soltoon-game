package ir.pint.soltoon.soltoongame.shared.communication.command;

import shared.data.action.Action;

public class CommandAction extends Command{

    public Action action;
    public CommandAction(Long id, Action action) {
        super(id);
        this.action=action;
    }
}