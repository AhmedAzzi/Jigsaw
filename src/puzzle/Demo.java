/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puzzle;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

/**
 *
 * @author soumia
 */
public class Demo extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel frameBar;
    private boolean isMaximized;
    private final JLabel gifLabel;
    private JButton minimizeButton;
    private JButton maximizeButton;
    private JButton exitButton;
    private JLabel title;
    private final int screenHeight;

    public Demo() {
        setLayout(null);
        setSize(700, 540);
        //bayna bayna - x bar fougani
        customFrameBar(this, 700, 40, 590);
        screenHeight = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
        // Create a JLabel to hold the GIF
        ImageIcon gifIcon = new ImageIcon(new ImageIcon("resources/images/demo4.gif").getImage().getScaledInstance(700, 500, Image.SCALE_DEFAULT));
        gifLabel = new JLabel(gifIcon);
        gifLabel.setBounds(0, 40, 700, 500);
        this.add(gifLabel);
        setVisible(true);
    }

    private void customFrameBar(JFrame f, int width, int height, int x) {
        f.setUndecorated(true);

        frameBar = new JPanel(null);
        frameBar.setBackground(Color.decode("#387ba5"));
        frameBar.setBounds(0, 0, width, height);

        //bach n7arkou l'interface
        frameBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                setLocation(e.getXOnScreen(), e.getYOnScreen());
            }
        });

        frameBar.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                setLocation(e.getXOnScreen(), e.getYOnScreen());
            }
        });

        JLabel icon = new JLabel(new ImageIcon(new ImageIcon("resources/images/icon.png").getImage().getScaledInstance(40, 30, Image.SCALE_SMOOTH)));
        icon.setBounds(5, 5, 40, 30);
        frameBar.add(icon);

        minimizeButton = new JButton("_");
        minimizeButton.setBounds(x - 55, -20, 60, 70);
        minimizeButton.setForeground(Color.WHITE);
        minimizeButton.setFont(new Font("Arial", Font.BOLD, 16));
        minimizeButton.setBackground(new Color(0, 0, 0, 0));
        minimizeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //state ta3 frame bach yatminimiza
                setState(Frame.ICONIFIED);
            }

            @Override
            public void mouseEntered(MouseEvent e) {

                minimizeButton.setBackground(Color.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                minimizeButton.setBackground(new Color(0, 0, 0, 0));
            }
        });

        maximizeButton = new JButton("[]");
        maximizeButton.setBounds(x, -15, 60, 70);
        maximizeButton.setForeground(Color.WHITE);
        maximizeButton.setFont(new Font("Arial", Font.BOLD, 16));
        maximizeButton.setBackground(new Color(0, 0, 0, 0));

        isMaximized = false; // Flag variable to track frame state

        maximizeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isMaximized) {
                    setExtendedState(JFrame.NORMAL); // Restore to normal size
                    isMaximized = false;
                    minimizeButton.setBounds(x - 55, -20, 60, 70);
                    exitButton.setBounds(x + 55, -5, 60, 50);
                    maximizeButton.setBounds(x, -15, 60, 70);
                    gifLabel.setBounds(0, 40, 700, 500);
                    title.setBounds(width / 2 - 50, 10, 100, 30);
                    ImageIcon gifIcon = new ImageIcon(new ImageIcon("resources/images/demo4.gif").getImage().getScaledInstance(700, 500, Image.SCALE_DEFAULT));
                    gifLabel.setIcon(gifIcon);
                    gifLabel.setBounds(0, 40, 700, 500);

                } else {

                    setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the frame
                    isMaximized = true;
                    frameBar.setBounds(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, 40);
                    maximizeButton.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width - 60 - 50, -20, 60, 70);
                    exitButton.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width - 55, -15, 60, 70);
                    minimizeButton.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width - 60 - 50 - 50, -20, 60, 70);
                    title.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 50, 5, 100, 30);
                    ImageIcon gifIcon = new ImageIcon(new ImageIcon("resources/images/demo4.gif").getImage().getScaledInstance(Toolkit.getDefaultToolkit().getScreenSize().width, screenHeight - 40, Image.SCALE_DEFAULT));
                    gifLabel.setIcon(gifIcon);
                    gifLabel.setBounds(0, 40, Toolkit.getDefaultToolkit().getScreenSize().width, screenHeight - 40);

                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                maximizeButton.setBackground(Color.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                maximizeButton.setBackground(new Color(0, 0, 0, 0));
            }
        });

        exitButton = new JButton("X");
        exitButton.setBounds(x + 55, -5, 60, 50);
        exitButton.setBackground(new Color(0, 0, 0, 0));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFont(new Font("Arial", Font.BOLD, 16));
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                exitButton.setBackground(Color.RED);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitButton.setBackground(new Color(0, 0, 0, 0));
            }
        });
        frameBar.add(maximizeButton);
        frameBar.add(minimizeButton);
        frameBar.add(exitButton);

        title = new JLabel("Demo");
        title.setFont(new Font("Z003", Font.BOLD, 20));
        title.setForeground(Color.white);
        title.setBounds(width / 2 - 50, 10, 100, 30);
        frameBar.add(title);

        f.add(frameBar);
    }

}
