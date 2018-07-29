package ch.model;

import javax.swing.*;
import java.awt.*;

public class Field extends JPanel {
    public boolean Pink;
    public boolean Gray;
    public boolean Empty;
    public boolean PinkQueen;
    public boolean GrayQueen;
    public boolean Current;

    public Field() {
        Pink = false;
        Gray = false;
        Empty = true;
        Current = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (Current) { g.setColor(Color.PINK);
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
        if (Pink) {
            g.setColor(Color.PINK);
            g.fillOval(5, 5, getWidth() - 10, getHeight() - 10);
        }
        if (Gray){
            g.setColor(Color.GRAY);
            g.fillOval(5, 5, getWidth() - 10, getHeight() - 10);
        }
        if (GrayQueen) {
            g.setColor(Color.GRAY);
            g.fillOval(5, 5, getWidth() - 10, getHeight() - 10);
            g.setColor(Color.white);
            g.fillOval(10, 10, getWidth() - 20, getHeight() - 20);
        }
        if (PinkQueen) {
            g.setColor(Color.PINK);
            g.fillOval(5, 5, getWidth() - 10, getHeight() - 10);
            g.setColor(Color.white);
            g.fillOval(10, 10, getWidth() - 20, getHeight() - 20);
        }
    }

}

