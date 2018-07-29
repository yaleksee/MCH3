package ch;

import ch.view.CheckersBoard;

import javax.swing.*;

public class ApplicationRun {
    public static void main(String[] args) {
        Runnable r = () -> {
            CheckersBoard chBrd = new CheckersBoard();
            chBrd.setVisible(true);
            chBrd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        };
        SwingUtilities.invokeLater(r);
    }
}
