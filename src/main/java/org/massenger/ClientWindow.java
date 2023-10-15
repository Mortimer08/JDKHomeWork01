package org.massenger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientWindow extends JFrame {
    public static final String WINDOW_CAPTION = "Chat client";
    private static final int WINDOW_HEIGHT = 230;
    private static final int WINDOW_WIDTH = 350;
    private JPanel topPanel;
    private JPanel addressPanel;
    private JPanel userPanel;
    private JTextField ipArea;
    private String defaultIP = "127.0.0.1";
    private JTextField portArea;
    private String defaultPort = "8080";
    private JTextField nameArea;
    private String defaultName = "John Smith";

    private JPasswordField passwordArea;
    private String defaultPassword = "password";
    private JPanel middlePanel;
    private JTextArea messagesField;
    private JPanel bottomPanel;
    private JTextField inputField;
    private JButton sendButton;
    private final String sendButtonCaption = "send";
    private JButton btnLogin;
    public static final String BTN_LOGIN_CAPTION = "login";

    ClientWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle(WINDOW_CAPTION);
        setResizable(false);
        add(createTopPanel(), BorderLayout.NORTH);
        add(createMiddlePanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);
    }

    Component createTopPanel() {
        topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.add(createAddressPanel());
        topPanel.add(createUserPanel());

        return topPanel;
    }

    Component createAddressPanel() {
        addressPanel = new JPanel(new GridLayout(1, 3));
        ipArea = new JTextField(defaultIP);
        portArea = new JTextField(defaultPort);
        addressPanel.add(ipArea);
        addressPanel.add(portArea);
        return addressPanel;
    }

    Component createUserPanel() {
        userPanel = new JPanel(new GridLayout(1, 3));
        nameArea = new JTextField(defaultName);
        passwordArea = new JPasswordField(defaultPassword);
        btnLogin = new JButton(BTN_LOGIN_CAPTION);
        userPanel.add(nameArea);
        userPanel.add(passwordArea);
        userPanel.add(btnLogin);
        return userPanel;
    }

    Component createMiddlePanel() {
        middlePanel = new JPanel(new GridLayout(1, 1));
        middlePanel.add(createMessagesField());
        return middlePanel;
    }

    Component createMessagesField() {
        messagesField = new JTextArea();
        messagesField.setEditable(false);
        return messagesField;
    }

    Component createBottomPanel() {
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.LINE_AXIS));
        bottomPanel.add(createInputField());
        bottomPanel.add(createSendButton());
        return bottomPanel;
    }

    Component createInputField() {
        inputField = new JTextField();
        return inputField;
    }

    Component createSendButton() {
        sendButton = new JButton(sendButtonCaption);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                sendMessage();
            }
        });
        return sendButton;
    }

    private void sendMessage() {
        System.out.println("Send button is pressed");
    }
}
