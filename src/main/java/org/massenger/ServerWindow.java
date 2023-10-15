package org.massenger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class ServerWindow extends JFrame {
    public static final String WINDOW_CAPTION = "Chat server";
    private static final int WINDOW_HEIGHT = 235;
    private static final int WINDOW_WIDTH = 350;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JTextArea textArea;
    private JButton btnStart;
    private JButton btnStop;
    private static final String BTN_START_CAPTION = "Start";
    private static final String BTN_STOP_CAPTION = "Stop";
    public static final String SERVER_STARTED_MESSAGE = "Server started";
    public static final String SERVER_IS_RUNNING = "Server is already running";
    public static final String SERVER_STOPPED_MESSAGE = "Server stopped";
    public static final String SERVER_IS_STOPPED = "Server is not running";
    ClientWindow clientWindow;
    private boolean serverRunning;
    private BufferedWriter bufferedWriter;
    private FileWriter fileWriter;
    private static String fileName;

    ServerWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle(WINDOW_CAPTION);
        setResizable(false);
        add(createBottomPanel(), BorderLayout.SOUTH);
        add(createTopPanel(), BorderLayout.NORTH);
        setVisible(true);

        createClientWindow();
        serverRunning = false;
        fileName = "logfile";
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(fileName, true));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    Component createTopPanel() {
        topPanel = new JPanel(new GridLayout(1, 1));
        textArea = new JTextArea(12, 25);
        textArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        topPanel.add(scroll);
        return topPanel;
    }

    Component createBottomPanel() {
        bottomPanel = new JPanel(new GridLayout(1, 2));
        btnStart = new JButton(BTN_START_CAPTION);
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                startServer();
            }
        });
        btnStop = new JButton(BTN_STOP_CAPTION);
        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    stopServer();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        bottomPanel.add(btnStart);
        bottomPanel.add(btnStop);
        return bottomPanel;
    }


    private void startServer() {
        if (!serverRunning) {
            serverRunning = true;
            textArea.append(SERVER_STARTED_MESSAGE + "\n");
            clientWindow.setVisible(true);
            try {
                clientWindow.putMessagesInMessageField(readFromFile(fileName));
            } catch (IOException e) {
                System.out.println(e.getMessage());

            }

        } else {
            textArea.append(SERVER_IS_RUNNING + "\n");
        }
    }

    private void stopServer() throws IOException {
        if (serverRunning) {
            clientWindow.closeConnection();
            clientWindow.setVisible(false);
            bufferedWriter.close();
            serverRunning = false;
            textArea.append(SERVER_STOPPED_MESSAGE + "\n");
            clientWindow.setVisible(false);
        } else {
            textArea.append(SERVER_IS_STOPPED + "\n");
        }
    }

    private void createClientWindow() {
        clientWindow = new ClientWindow();
        clientWindow.setServer(this);
        clientWindow.setVisible(false);
    }

    protected void saveMessage(String message) {
        writeToFile(bufferedWriter, message);
    }

    private void writeToFile(BufferedWriter bufferedWriter, String message) {
        try {
            bufferedWriter.append(message);
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String readFromFile(String fileName) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        StringBuilder messages = new StringBuilder();
        String line = bufferedReader.readLine();
        while (line != null) {
            messages.append(line+"\n");
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        return messages.toString();
    }
}

