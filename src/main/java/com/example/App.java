package com.example;

import com.example.ui.LobbyFrame;
import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LobbyFrame lobbyFrame = new LobbyFrame();
            lobbyFrame.setVisible(true);
        });
    }
}   