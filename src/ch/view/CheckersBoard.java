package ch.view;

import ch.controller.Controller;
import ch.model.Field;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CheckersBoard extends JFrame implements ActionListener, MouseListener {
    private ch.controller.Controller Controller;
    private JLabel title;
    private Field[][] fieldArray;
    private final JPanel board = new JPanel();
    private final Dimension dimension = new Dimension(400, 400);
    private Controller controller;

    public CheckersBoard() {
        initializeGui();
    }

    public final void initializeGui() {
        Container cp = getContentPane();
        JPanel boardParent = new JPanel();
        boardParent.setLayout(new GridBagLayout());
        board.addMouseListener(this);
        board.setPreferredSize(new Dimension(dimension));
        board.setMinimumSize(new Dimension(dimension));
        board.setMaximumSize(new Dimension(dimension));

        boardParent.setPreferredSize(new Dimension(dimension));
        boardParent.setMinimumSize(new Dimension(dimension));
        boardParent.setMaximumSize(new Dimension(dimension));

        board.setLayout(new GridLayout(8, 8));
        fieldArray = new Field[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                fieldArray[i][j] = new Field();
                board.add(fieldArray[i][j]);
                if (i % 2 == 0) {
                    if (j % 2 == 0) {
                        if (i < 3) {
                            fieldArray[i][j].Gray = true;
                            fieldArray[i][j].Empty = false;
                        }
                        if (i > 4) {
                            fieldArray[i][j].Pink = true;
                            fieldArray[i][j].Empty = false;
                        }
                        fieldArray[i][j].setBackground(Color.white);
                    } else {
                        fieldArray[i][j].setBackground(Color.black);
                    }
                } else {
                    if (j % 2 == 0) {
                        fieldArray[i][j].setBackground(Color.black);
                    } else {
                        if (i < 3) {
                            fieldArray[i][j].Gray = true;
                            fieldArray[i][j].Empty = false;
                        }
                        if (i > 4) {
                            fieldArray[i][j].Pink = true;
                            fieldArray[i][j].Empty = false;
                        }
                        fieldArray[i][j].setBackground(Color.white);
                    }
                }
            }
        }

        boardParent.add(board);

        JPanel informationText = new JPanel();
        title = new JLabel("Checkers Board");
        informationText.add(title);
        JPanel down = new JPanel();
        JButton reboot = new JButton("Reboot");
        JButton exit = new JButton("Exit");
        JButton next = new JButton("+ Next Game");
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Runnable r = () -> {
                    CheckersBoard chBrd = new CheckersBoard();
                    chBrd.setVisible(true);
                    chBrd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                };
                SwingUtilities.invokeLater(r);
            }
        });
        reboot.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setState(JFrame.ICONIFIED);
                Runnable r = () -> {
                    CheckersBoard chBrd = new CheckersBoard();
                    chBrd.setVisible(true);
                    chBrd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                };
                SwingUtilities.invokeLater(r);
            }
        });

        down.add(next);
        down.add(reboot);
        down.add(exit);

        cp.add(informationText, BorderLayout.NORTH);
        cp.add(boardParent, BorderLayout.CENTER);
        cp.add(down, BorderLayout.SOUTH);
        setBounds(50, 50, 500, 600);

        controller = new Controller(fieldArray);
    }

    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        int y = e.getX() / 50;
        int x = e.getY() / 50;
        int pink = controller.getCountPinkCheckers();
        int gray = controller.getCountGrayCheckers();
        if (pink != 0 && gray != 0) {
            title.setText("Розовых осталось " + pink + " / " + "Серых осталось " + gray);
        } else if (pink == 0) {
            title.setText("Серые выиграли");
        } else {
            title.setText("Розовые выиграли");
        }
        controller.clickController(x, y);
        repaint();

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }
}