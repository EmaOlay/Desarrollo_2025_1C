package main;

import ui.MainFrame;

public class App {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}