package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame {
    private static final int WINDOW_HEIGHT = 555;
    private static final int WINDOW_WIDTH = 507;
    private static final int WINDOW_POSX = 800;
    private static final int WINDOW_POSY = 300;
    public static final String BTN_NEW_GAME = "New Game";
    public static final String BTN_EXIT = "Exit";
    Map map;
    SettingsWindow settings;
    JButton btnStart = new JButton(BTN_NEW_GAME);
    JButton btnExit = new JButton(BTN_EXIT);

    GameWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(WINDOW_POSX, WINDOW_POSY);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("TicTacToe");
        setResizable(false);
        map = new Map();
        settings = new SettingsWindow(this);
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                settings.setVisible(true);
            }
        });
        JPanel panBottom = new JPanel(new GridLayout(1, 2));

        panBottom.add(btnStart);
        panBottom.add(btnExit);
        add(panBottom, BorderLayout.SOUTH);
        add(map);
        setVisible(true);
        settings.setVisible(false);

    }

    void startNewGame(int mode, int fSzX, int fSzY, int wLen) {
        map.startNewGame(mode, fSzX, fSzY, wLen);
    }
}
