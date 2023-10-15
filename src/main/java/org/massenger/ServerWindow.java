package org.massenger;

import javax.print.StreamPrintServiceFactory;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerWindow extends JFrame {
    public static final String WINDOW_CAPTION = "Chat server";
    private static final int WINDOW_HEIGHT = 230;
    private static final int WINDOW_WIDTH = 350;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JTextArea textArea;
    private JButton btnStart;
    private JButton btnStop;
    private static final String BTN_START_CAPTION = "Start";
    private static final String BTN_STOP_CAPTION = "Stop";
    ClientWindow clientWindow;

    ServerWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle(WINDOW_CAPTION);
        setResizable(false);
        add(createBottomPanel(), BorderLayout.SOUTH);
        add(createTopPanel(), BorderLayout.NORTH);
        setVisible(true);

        createClientWindow();

    }


    Component createTopPanel() {
        topPanel = new JPanel(new GridLayout(1, 1));
        textArea = new JTextArea();
        textArea.setEditable(false);
        topPanel.add(textArea);
        return topPanel;
    }

    Component createBottomPanel() {
        bottomPanel = new JPanel(new GridLayout(1, 2));
        btnStart = new JButton(BTN_START_CAPTION);
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                startChat();
            }
        });
        btnStop = new JButton(BTN_STOP_CAPTION);

        bottomPanel.add(btnStart);
        bottomPanel.add(btnStop);
        return bottomPanel;
    }


    private void startChat() {
        clientWindow.setVisible(true);
    }

    private void createClientWindow() {
        clientWindow = new ClientWindow();
        clientWindow.setVisible(false);
    }
}

