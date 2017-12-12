package ir.pint.soltoon.soltoongame.ui;

import ir.pint.soltoon.soltoongame.FighterUI;
import ir.pint.soltoon.soltoongame.shared.Platform;
import ir.pint.soltoon.soltoongame.ui.elements.*;
import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;

import javax.swing.*;

public class Sui extends Thread {
    private boolean gameIsReady = false;
    private SuiManager suiManager;

    private Sui(SuiManager suiManager) {
        this.suiManager = suiManager;
    }

    public static Sui initiate() {
        SuiConfiguration suiConfiguration = new SuiConfiguration();
        SuiManager suiManager = SuiManager.initiate(suiConfiguration);
        Sui sui = new Sui(suiManager);
        Platform.register(sui);
        return sui;
    }


    @Override
    public void run() {

        while (true) {
            try {
                Thread.sleep(500);
                if (suiManager.getSuiConfiguration().isPlay())
                    suiManager.nextAction();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//
//        SuiGameFrame soltoonFrame = new SuiGameFrame(suiConfiguration);
//
//        soltoonFrame.setVisible(true);
//        soltoonFrame.revalidate();
//        soltoonFrame.repaint();
//
//        soltoonFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        System.out.println("hello");
//
//        while (!suiConfiguration.isPlayersAllJoined() || !suiConfiguration.isAllSettingsRecieved()) {
//            try {
//                System.out.println(suiConfiguration.isPlayersAllJoined());
//                System.out.println(suiConfiguration.isAllSettingsRecieved());
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                // ignore
//            }
//        }
//        System.out.println("readyyyyy");
//
//        soltoonFrame.readyForGame();
//        soltoonFrame.revalidate();
//        soltoonFrame.repaint();
//
//        while (true) {
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                // ignore
//            }
//        }

//        soltoonFrame.resize();

//        SuiFighter soltoonGameElement = new SuiFighter(FighterUI.TOWER, soltoonFrame);
//        SuiFighter soltoonGameElement2 = new SuiFighter(FighterUI.BOMBER, soltoonFrame);
//        SuiFighter soltoonGameElement3 = new SuiFighter(FighterUI.GIANT, soltoonFrame);
//        soltoonGameElement.setBounds(0, 0, SuiConfiguration.CELL_SIZE, SuiConfiguration.CELL_SIZE);
//        soltoonGameElement2.setBounds(SuiConfiguration.CELL_SIZE * 2, SuiConfiguration.CELL_SIZE * 2, SuiConfiguration.CELL_SIZE, SuiConfiguration.CELL_SIZE);
//        soltoonGameElement3.setBounds(SuiConfiguration.CELL_SIZE * 4, SuiConfiguration.CELL_SIZE * 2, SuiConfiguration.CELL_SIZE, SuiConfiguration.CELL_SIZE);
//
//
//
//        suiGameBoard.add(suiActionDrawer);
//
//
//        suiGameBoard.add(soltoonGameElement);
//        suiGameBoard.add(soltoonGameElement2);
//        suiGameBoard.add(soltoonGameElement3);
//
//
//        soltoonFrame.add(suiGameBoard);
//        soltoonFrame.resize();


    }

    public boolean isGameReady() {
        return gameIsReady;
    }

    public void setGameReady() {
        gameIsReady = true;
    }
}
