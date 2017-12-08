package ir.pint.soltoon.soltoongame.shared.data;

import ir.pint.soltoon.soltoongame.shared.communication.result.Result;
import ir.pint.soltoon.soltoongame.shared.data.action.Action;
import ir.pint.soltoon.soltoongame.shared.data.map.GameBoard;
import ir.pint.soltoon.utils.clients.proxy.ProxyReturnStorage;
import ir.pint.soltoon.utils.clients.proxy.TimeAwareBean;
import ir.pint.soltoon.utils.clients.proxy.TimeAwareProxyInterface;

import java.io.Serializable;
import java.util.Random;

public abstract class Agent implements Serializable, TimeAwareBean {

    private TimeAwareBean parentBean;
    private Long id;

    protected Agent() {
    }

    public abstract void init();

    public abstract void lastThingsToDo(GameBoard gameBoard);

    public abstract Action getAction(GameBoard gb);

    public void recieveResults(Result result){

    }

    public void setParentBean(TimeAwareBean parentBean) {
        this.parentBean = parentBean;
    }

    @Override
    public void repair() {

    }

    @Override
    public void setProxyReturnStorage(ProxyReturnStorage proxyReturnStorage) {
        // do nothing
    }

    @Override
    public ProxyReturnStorage getProxyReturnStorage() {
        if (this.parentBean != null)
            return parentBean.getProxyReturnStorage();
        else
            return null;
    }

    @Override
    public int getRemainingTime() {
        if (this.parentBean != null)
            return parentBean.getRemainingTime();
        else
            return -2;
    }

    @Override
    public void setRemainingTime(int i) {
        // ignore
    }

    @Override
    public int getDurationType() {
        return parentBean.getDurationType();
    }

    @Override
    public void setDurationType(int i) {
        // ignore
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
