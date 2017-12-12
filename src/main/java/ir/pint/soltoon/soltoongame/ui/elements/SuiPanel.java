package ir.pint.soltoon.soltoongame.ui.elements;

import ir.pint.soltoon.soltoongame.ui.SuiConfiguration;
import ir.pint.soltoon.soltoongame.ui.SuiManager;
import ir.pint.soltoon.soltoongame.ui.analysis.AnalysisType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

public class SuiPanel extends JPanel {
    private SuiManager suiManager;
    private Box playersBox;

    public SuiPanel(SuiManager suiManager) {
        this.suiManager = suiManager;

        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(boxLayout);

        SuiConfiguration suiConfiguration = suiManager.getSuiConfiguration();

        setSize(suiConfiguration.getPanelWidth(), suiConfiguration.getMapHeight());
        setMaximumSize(new Dimension(suiConfiguration.getPanelWidth(), suiConfiguration.getMapHeight()));

        Box controlsBox = Box.createHorizontalBox();
        controlsBox.add(new SuiPrevTurnButton());
        controlsBox.add(new SuiPauseStartButton());
        controlsBox.add(new SuiNextTurnButton());
        controlsBox.setAlignmentY(JComponent.TOP_ALIGNMENT);

        controlsBox.setSize(suiConfiguration.getPanelWidth(), controlsBox.getHeight());

        Box analysisBox = Box.createHorizontalBox();
        AnalysisComboBox analysisComboBox = new AnalysisComboBox();
        analysisBox.add(analysisComboBox);
        ShowAnalysisButton showAnalysisButton = new ShowAnalysisButton();
        analysisBox.add(showAnalysisButton);


        this.playersBox = Box.createVerticalBox();

        Collection<SuiPlayer> values = suiManager.getPlayers().values();
        for (SuiPlayer value : values) {
            value.setSuiManager(suiManager);
            playersBox.add(value);
        }


        add(controlsBox);
        add(playersBox);
    }

    public Box getPlayersBox() {
        return playersBox;
    }

    abstract class SuiTimeButton extends JButton {
        public SuiTimeButton(String s) {
            super(s);
            setPreferredSize(new Dimension(suiManager.getSuiConfiguration().getPanelWidth() / 3, 30));
            setSize(suiManager.getSuiConfiguration().getPanelWidth() / 3, 30);
            setMaximumSize(new Dimension(suiManager.getSuiConfiguration().getPanelWidth() / 3, getMinimumSize().height));
        }
    }

    private class SuiNextTurnButton extends SuiTimeButton {
        public SuiNextTurnButton() {
            super(">>");

            setToolTipText("Next Turn");

            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    SuiPanel.this.suiManager.nextAction();
                }
            });
        }
    }

    private class SuiPrevTurnButton extends SuiTimeButton {
        public SuiPrevTurnButton() {
            super("<<");

            setToolTipText("Previous Turn");

            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    suiManager.previousAction();
                }
            });
        }
    }

    private class SuiPauseStartButton extends SuiTimeButton {
        private static final String pauseText = "||";
        private static final String resumeText = ">";

        public SuiPauseStartButton() {
            super(resumeText);

            setToolTipText("Pause/Resume");

            final SuiPauseStartButton pauseStartButton = this;

            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    SuiPanel.this.suiManager.getSuiConfiguration().setPlay(!SuiPanel.this.suiManager.getSuiConfiguration().isPlay());

                    pauseStartButton.refresh();
                }
            });

            refresh();
        }

        private void refresh() {
            if (suiManager.getSuiConfiguration().isPlay()) {
                setText(pauseText);
            } else {
                setText(resumeText);
            }
        }
    }

    private class AnalysisComboBox extends JComboBox {
        public final String[] TYPES = AnalysisType.valuesAsString();

        public AnalysisComboBox() {

            setMaximumSize(new Dimension(suiManager.getSuiConfiguration().getPanelWidth(), getPreferredSize().height));
            for (String type : TYPES) {
                addItem(type);
            }


        }
    }

    private class ShowAnalysisButton extends JButton {
        public ShowAnalysisButton() {
            setText("Show");

        }
    }

}
