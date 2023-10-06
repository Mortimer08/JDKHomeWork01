package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsWindow extends JFrame {
    private static final int WINDOW_HEIGHT = 230;
    private static final int WINDOW_WIDTH = 350;
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


    SettingsWindow(GameWindow gameWindow) {
        btnStart = new JButton(BTN_START_CAPTION);
        lblChoice = new JLabel(LBL_CHOICE_CAPTION);
        topPanel = new JPanel(new GridLayout(10, 1));
        human = new JRadioButton(RADIO_BTN_HUMAN_CAPTION);
        ai = new JRadioButton(RADIO_BTN_AI_CAPTION);
        opponentSelect = new ButtonGroup();
        fieldSizeCaption = new JLabel(LBL_FIELD_SIZE_CAPTION);
        fieldSizeSelected = new JLabel(SELECTED_FIELD_PREFIX);
        fieldSizeSelector = new JSlider(3, 10,3);
        winLineCaption = new JLabel(LBL_WIN_LINE_CAPTION);
        winLineSelected = new JLabel(SELECTED_WIN_LINE_PREFIX);
        winLineSelector = new JSlider(3, 10,3);
        setLocationRelativeTo(gameWindow);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                gameWindow.startNewGame(0, 5, 5, 5);
                setVisible(false);
            }
        });
        add(btnStart, BorderLayout.SOUTH);
        //Game Mode components
        opponentSelect.add(human);
        opponentSelect.add(ai);
        topPanel.add(lblChoice);
        topPanel.add(human);
        topPanel.add(ai);
        topPanel.add(fieldSizeCaption);
        topPanel.add(fieldSizeSelected);
        topPanel.add(fieldSizeSelector);
        topPanel.add(winLineCaption);
        topPanel.add(winLineSelected);
        topPanel.add(winLineSelector);
        add(topPanel);
    }
}
