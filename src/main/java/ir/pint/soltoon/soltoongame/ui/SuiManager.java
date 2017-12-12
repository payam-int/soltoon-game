package ir.pint.soltoon.soltoongame.ui;

import ir.pint.soltoon.soltoongame.ui.actions.SuiAction;
import ir.pint.soltoon.soltoongame.ui.elements.*;
import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;

import javax.swing.*;
import java.util.Deque;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;

public class SuiManager {
    private Map<Long, SuiPlayer> players = new Hashtable<>();
    private Map<Long, SuiFighter> fighters = new Hashtable<>();
    private SuiConfiguration suiConfiguration;

    private SuiActionDrawer actionDrawer;
    private SuiGameFrame gameFrame;
    private SuiGameBoard gameBoard;
    private SuiPanel gamePanel;

    private volatile Boolean dependenciesResloved = false;
    private volatile Boolean playersAreReady = false;

    private Deque<SuiAction> previousActions = new ConcurrentLinkedDeque<>();
    private Deque<SuiAction> nextActions = new ConcurrentLinkedDeque<>();

    private SuiAction currentAction = null;

    private SuiManager(SuiConfiguration suiConfiguration) {
        this.suiConfiguration = suiConfiguration;
    }

    private void initiate() {
        ResultStorage.addResultHandler(new SuiResultPipe(this));
    }


    public synchronized void addPlayer(SuiPlayer suiPlayer) {
        players.put(suiPlayer.getId(), suiPlayer);


    }

    public synchronized void addFighter(SuiFighter fighter) {
        fighters.put(fighter.getId(), fighter);
    }

    public void addAction(SuiAction suiAction) {
        nextActions.addLast(suiAction);
    }

    public static SuiManager initiate(SuiConfiguration suiConfiguration) {
        SuiManager suiManager = new SuiManager(suiConfiguration);
        suiManager.initiate();

        return suiManager;
    }

    public SuiConfiguration getSuiConfiguration() {
        return suiConfiguration;
    }

    public synchronized void mapDependenciesResloved() {
        if (!dependenciesResloved) {
            dependenciesResloved = true;
            tryStartUI();
        }
    }

    private void tryStartUI() {
        if (!dependenciesResloved || !playersAreReady) {
            return;
        }


        suiConfiguration.initialize();
        this.gameFrame = new SuiGameFrame(this);
        gameFrame.setVisible(true);

        this.actionDrawer = new SuiActionDrawer(this);
        this.gameBoard = new SuiGameBoard(this);

        this.gameBoard.initiate();

        this.gameBoard.add(this.actionDrawer);

        this.gameFrame.add(gameBoard);
        this.gamePanel = new SuiPanel(this);

        this.gameFrame.add(this.gamePanel);

        gameFrame.forceRepaint();
        gameBoard.forceRepaint();
        gameBoard.updateUI();
    }

    public synchronized void playerDependenciesResolved() {
        if (!playersAreReady) {
            this.playersAreReady = true;
            tryStartUI();
        }
    }

    public void nextAction() {
        if (!haveNextAction())
            return;

        if (currentAction != null) {
            previousActions.addLast(currentAction);
        }

        currentAction = nextActions.pollFirst();
        currentAction.apply(this);
        actionDrawer.setCurrentAction(currentAction);
        repaintAll();
    }


    public boolean haveNextAction() {
        return (dependenciesResloved && playersAreReady) && !nextActions.isEmpty();
    }


    public void previousAction() {
        if (currentAction == null)
            return;

        currentAction.revert(this);

        nextActions.addFirst(currentAction);

        SuiAction suiAction = previousActions.pollLast();
        currentAction = suiAction;
        actionDrawer.setCurrentAction(currentAction);
        repaintAll();
    }

    private void repaintAll() {
        actionDrawer.revalidate();
        gameBoard.revalidate();
        actionDrawer.repaint();
        gameBoard.repaint();
    }


    public SuiActionDrawer getActionDrawer() {
        return actionDrawer;
    }

    public SuiGameFrame getGameFrame() {
        return gameFrame;
    }

    public SuiGameBoard getGameBoard() {
        return gameBoard;
    }

    public SuiFighter getFighter(Long fighter) {
        return fighters.getOrDefault(fighter, null);
    }

    public Map<Long, SuiPlayer> getPlayers() {
        return players;
    }

    public SuiPlayer getPlayer(Long player) {
        return this.players.getOrDefault(player, null);
    }
}
