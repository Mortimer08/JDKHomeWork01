package org.tictactoe;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsWindow extends JFrame {
    private static final int WINDOW_HEIGHT = 230;
    private static final int WINDOW_WIDTH = 350;
    public static final int MIN_FIELD_SIZE = 3;
    public static final int MAX_FIELD_SIZE = 10;
    private static int fieldSize;
    private static int winLine;
    private static int mode;
    public static final int HVC_MODE = 0;
    public static final int HVH_MODE = 1;
    private static final String BTN_START_CAPTION = "Start New Game";
    public static final String LBL_CHOICE_CAPTION = "Select game mode";
    public static final String RADIO_BTN_HUMAN_CAPTION = "Human vs Human";
    public static final String RADIO_BTN_AI_CAPTION = "Human vs Computer";
    public static final String LBL_FIELD_SIZE_CAPTION = "Select field size";
    public static final String SELECTED_FIELD_PREFIX = "Selected size: ";

    public static final String LBL_WIN_LINE_CAPTION = "Select win line";
    public static final String SELECTED_WIN_LINE_PREFIX = "Selected win line: ";

    private JButton btnStart;
    private JLabel lblChoice;
    private JRadioButton human;
    private JRadioButton ai;
    private ButtonGroup opponentSelect;
    private JLabel fieldSizeCaption;
    private JLabel fieldSizeSelected;
    private JSlider fieldSizeSelector;
    private JPanel topPanel;
    private JLabel winLineCaption;
    private JLabel winLineSelected;
    private JSlider winLineSelector;

    GameWindow gameWindow;

    SettingsWindow(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        fieldSize = 3;
        winLine = 3;
        mode = 0;
        setLocationRelativeTo(gameWindow);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        add(createBtnStart(), BorderLayout.SOUTH);
        add(createTopPanel());
    }

    private void startGame() {
        if (human.isSelected()) {
            mode = HVH_MODE;

        } else if (ai.isSelected()) {
            mode = HVC_MODE;
        } else {
            throw new RuntimeException("Unknown game mode: " + mode);
        }
        gameWindow.startNewGame(mode, fieldSize, fieldSize, winLine);
        setVisible(false);
    }

    private Component createTopPanel() {
        topPanel = new JPanel(new GridLayout(3, 1));
        topPanel.add(createModePanel());
        topPanel.add(createSizePanel());
        topPanel.add(createWinLinePanel());
        return topPanel;
    }

    private Component createModePanel() {
        JPanel modeSelectorPanel = new JPanel(new GridLayout(3, 1));
        lblChoice = new JLabel(LBL_CHOICE_CAPTION);
        human = new JRadioButton(RADIO_BTN_HUMAN_CAPTION);
        ai = new JRadioButton(RADIO_BTN_AI_CAPTION);
        ai.setSelected(true);
        opponentSelect = new ButtonGroup();
        opponentSelect.add(human);
        opponentSelect.add(ai);
        modeSelectorPanel.add(lblChoice);
        modeSelectorPanel.add(human);
        modeSelectorPanel.add(ai);
        return modeSelectorPanel;
    }

    private Component createSizePanel() {
        JPanel sizeSelectorPanel = new JPanel(new GridLayout(3, 1));
        fieldSizeCaption = new JLabel(LBL_FIELD_SIZE_CAPTION);
        fieldSizeSelected = new JLabel(SELECTED_FIELD_PREFIX + MIN_FIELD_SIZE);
        fieldSizeSelector = new JSlider(MIN_FIELD_SIZE, MAX_FIELD_SIZE, MIN_FIELD_SIZE);
        fieldSizeSelector.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                fieldSize = fieldSizeSelector.getValue();
                fieldSizeSelected.setText(SELECTED_FIELD_PREFIX + fieldSize);
                winLineSelector.setMaximum(fieldSize);
            }
        });
        sizeSelectorPanel.add(fieldSizeCaption);
        sizeSelectorPanel.add(fieldSizeSelected);
        sizeSelectorPanel.add(fieldSizeSelector);
        return sizeSelectorPanel;
    }

    private Component createWinLinePanel() {
        JPanel winLinePanel = new JPanel(new GridLayout(3, 1));
        winLineCaption = new JLabel(LBL_WIN_LINE_CAPTION);
        winLineSelected = new JLabel(SELECTED_WIN_LINE_PREFIX + MIN_FIELD_SIZE);
        winLineSelector = new JSlider(MIN_FIELD_SIZE, fieldSize, MIN_FIELD_SIZE);
        winLineSelector.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                winLine = winLineSelector.getValue();
                winLineSelected.setText(SELECTED_WIN_LINE_PREFIX + winLine);
            }
        });
        winLinePanel.add(winLineCaption);
        winLinePanel.add(winLineSelected);
        winLinePanel.add(winLineSelector);
        return winLinePanel;
    }

    private Component createBtnStart() {
        btnStart = new JButton(BTN_START_CAPTION);
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                startGame();
            }
        });
        return btnStart;
    }
}
