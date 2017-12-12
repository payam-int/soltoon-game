package ir.pint.soltoon.soltoongame.ui.elements;

import ir.pint.soltoon.soltoongame.ui.SuiConfiguration;
import ir.pint.soltoon.soltoongame.ui.SuiManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SuiPanel extends JPanel {
    private SuiManager suiManager;

    public SuiPanel(SuiManager suiManager) {
        this.suiManager = suiManager;

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        SuiConfiguration suiConfiguration = suiManager.getSuiConfiguration();

        setSize(suiConfiguration.getPanelWidth(), suiConfiguration.getMapHeight());

        add(new SuiPrevTurnButton());
        add(new SuiNextTurnButton());
    }


    abstract class SuiTimeButton extends JButton {
        public SuiTimeButton(String s) {
            super(s);
            setSize(50, 20);
        }
    }

    private class SuiNextTurnButton extends SuiTimeButton {
        public SuiNextTurnButton() {
            super(">");

            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    SuiPanel.this.suiManager.nextAction();
                }
            });
        }
    }

    private class SuiPrevTurnButton extends JButton {
        public SuiPrevTurnButton() {
            super("<");

            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    System.out.println("hi");
                }
            });
        }
    }
}
