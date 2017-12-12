package ir.pint.soltoon.soltoongame.ui.elements;

import ir.pint.soltoon.soltoongame.ui.SuiConfiguration;
import ir.pint.soltoon.soltoongame.ui.SuiManager;
import ir.pint.soltoon.soltoongame.ui.actions.SuiAction;
import ir.pint.soltoon.soltoongame.ui.actions.SuiActionAdd;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SuiGameFrame extends JFrame {

    private SuiManager suiManager;

    public SuiGameFrame(SuiManager suiManager) throws HeadlessException {
        this.suiManager = suiManager;

        setTitle("Soltoon - Game viewer");

        SuiConfiguration suiConfiguration = suiManager.getSuiConfiguration();
        setBounds(suiConfiguration.getFrameX(), suiConfiguration.getFrameY(), suiConfiguration.getFrameWidth(), suiConfiguration.getFrameHeight());

        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void readyForGame() {
//        remove(WAIT_FOR_PLAYERS_LABEL);
//
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//
//        double screenSizeWidth = screenSize.getWidth();
//        double screenSizeHeight = screenSize.getHeight();
//
//        final SuiGameBoard suiGameBoard = new SuiGameBoard(suiConfiguration);
//        setSize(suiGameBoard.getWidth(), suiGameBoard.getHeight() + 20);
//        add(suiGameBoard);
//
//        suiGameBoard.revalidate();
//        suiGameBoard.repaint();
//
//        final SuiConfiguration s = suiConfiguration;
//
//        final SuiGameFrame gf = this;
//
//        JButton next = new JButton("next");
//        next.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
////                s.nextAction();
//                suiGameBoard.revalidate();
//                suiGameBoard.repaint();
//                gf.nextAction();
//            }
//        });
//
//        next.setBounds(500, 500, 100, 50);
//
//        add(next);
    }

    public void nextAction() {
//        List<SuiAction> currentActions = getSuiConfiguration().getCurrentActions();
//
//        for (SuiAction currentAction : currentActions) {
//            if (currentAction instanceof SuiActionAdd) {
//                SuiActionAdd add = (SuiActionAdd) currentAction;
//                Long id = add.getId();
//                System.out.println("111" + id);
//                SuiFighter fighter = getSuiConfiguration().getFighter(id);
//                System.out.println("fffff" + fighter);
//                if (fighter != null) {
//                    fighter.setPosition(fighter.getX(), fighter.getY());
//                    add(fighter);
//                }
//            }
//        }
//
//        repaint();
    }

    @Override
    public void repaint() {
        super.repaint();
//        System.out.println("CCC");
//        for (Component component : getComponents()) {
////            System.out.println("qeqe" + component.cast);
//            component.revalidate();
//            component.repaint();
//        }
    }


    public void forceRepaint() {

    }
}
