package de.tmexner.mqtt.chat.java;

import javax.swing.*;
import java.awt.*;

public class ChatClientGUI extends JFrame {

    private final DefaultListModel<String> chatListModel;
    private final JList<String> chatList;
    private final JTextArea chatDisplayArea;
    private final JTextField messageInputField;

    public ChatClientGUI() {
        setTitle("MQTT Chat Client");
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        chatListModel = new DefaultListModel<>();
        chatList = new JList<>(chatListModel);
        chatList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        chatList.addListSelectionListener(e -> chatSelected());
        JScrollPane chatListScrollPane = new JScrollPane(chatList);
        chatListScrollPane.setPreferredSize(new Dimension(200, 0));
        add(chatListScrollPane, BorderLayout.WEST);

        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new BorderLayout());
        chatDisplayArea = new JTextArea();
        chatDisplayArea.setEditable(false);
        JScrollPane chatDisplayScrollPane = new JScrollPane(chatDisplayArea);
        chatPanel.add(chatDisplayScrollPane, BorderLayout.CENTER);

        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BorderLayout());
        messageInputField = new JTextField();
        JButton sendMessageButton = new JButton("Send");
        sendMessageButton.addActionListener(e -> sendMessage());
        messagePanel.add(messageInputField, BorderLayout.CENTER);
        messagePanel.add(sendMessageButton, BorderLayout.EAST);

        chatPanel.add(messagePanel, BorderLayout.SOUTH);

        add(chatPanel, BorderLayout.CENTER);

        initializeChats();
    }

    private void initializeChats() {
        chatListModel.addElement("messanger/john_doe/jane_doe");
        chatListModel.addElement("messanger/jane_doe/john_doe");
    }

    private void chatSelected() {
        String selectedChat = chatList.getSelectedValue();
        chatDisplayArea.setText("");
        chatDisplayArea.append("Chat history for " + selectedChat + "\n");
    }

    private void sendMessage() {
        String selectedChat = chatList.getSelectedValue();
        if (selectedChat == null || selectedChat.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No chat selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String message = messageInputField.getText().trim();
        if (!message.isEmpty()) {
            chatDisplayArea.append("Me: " + message + "\n");
            messageInputField.setText("");
            sendMQTTMessage(selectedChat, message);
        }
    }

    private void sendMQTTMessage(String topic, String message) {

    }

    public void receiveMQTTMessage(String topic, String message) {

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChatClientGUI().setVisible(true));
    }
}