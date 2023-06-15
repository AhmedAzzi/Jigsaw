/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puzzle;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 *
 * @author soumia
 */
public class About extends JFrame {

    private int xOffset;
    private int yOffset;

    private JPanel aboutPanel;
    private JPanel instructionPanel;

    public About() {
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);// bah tji fel wast
        customFrameBar();//framebare li jay foug
        setupAboutPanel();
        setupInstructionPanel();
        this.add(aboutPanel);
        this.setVisible(true);
    }

    private void setupAboutPanel() {
        aboutPanel = new JPanel(null);
        aboutPanel.setBackground(Color.decode("#c5935e"));
        //JLabel titleLabel = new JLabel("Jigsaw HSR");
        JLabel titleLabel = new JLabel(
                "<html><body style='font-family: Z003, sans-serif; font-size: 24; text-align: center'>"
                + "<h1 style='font-size: 28'>Jigsaw HSR</h1>"
                + "<p><strong>Version :</strong> 1.0</p>"
                + "<p><strong>Creation Date:</strong> May 29, 2023</p>"
                + "<p>This puzzle game was created using Java Swing.</p>"
                + "<p><strong>Développeur :</strong> Soumia Rokia</p>"
                + "<p><strong>Date de création :</strong> 29 mai 2023</p>"
                + "<br>"
                + "<p>Ce jeu de puzzle a été créé avec Java Swing.</p>"
                + "<p>Amusez-vous bien !</p>"
                + "</body></html>");
        titleLabel.setBounds(500 / 2 - 150, 70, 300, 390);
        aboutPanel.add(titleLabel);

        JButton helpButton = new JButton("Help?");
        helpButton.setBounds(500 / 2 - 50, 450, 100, 30);
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show instruction panel when the help button is clicked
                SwingUtilities.invokeLater(() -> {
                    getContentPane().remove(aboutPanel);
                    getContentPane().add(instructionPanel);
                    // yakhdmou kifkif bah y3awdo yorganizo l frame
                    revalidate();
                    repaint();
                });
            }
        });
        aboutPanel.add(helpButton);
    }

    private void setupInstructionPanel() {
        instructionPanel = new JPanel();
        instructionPanel.setLayout(null);
        instructionPanel.setBackground(Color.decode("#c5935e"));

        JLabel instructionLabel = new JLabel("<html><body style='font-family: Z003, sans-serif; font-size: 14px; text-align: center'>"
                + "<h1'>Jigsaw HSR - Game Instructions</h1>"
                + "<ul style='text-align: left'>"
                + "<li>Move puzzle pieces by clicking on them with the mouse.</li>"
                + "<li>The goal is to reassemble the image by swaping the pieces in the correct order.</li>"
                + "<li>Use the 'Back' button to go back to the levels interface.</li>"
                + "<li>Pause the game by clicking the pause button.</li>"
                + "<li>Use the reset button to restart the game at any time.</li>"
                + "<li>Finish the game by placing all the pieces in the correct order.</li>"
                + "<li>The game also ends if you close the game window.</li>"
                + "</ul>"
                + "</body></html>");
        instructionLabel.setBounds(500 / 2 - 180, 40, 360, 400);
        instructionPanel.add(instructionLabel);

        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 450, 100, 30);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    getContentPane().remove(instructionPanel);
                    getContentPane().add(aboutPanel);
                    revalidate();
                    repaint();
                });
            }
        });
        instructionPanel.add(backButton);
    }

    private void customFrameBar() {
        setUndecorated(true);
        JPanel frameBar = new JPanel(null);
        frameBar.setBackground(Color.decode("#387ba5"));
        frameBar.setBounds(0, 0, 500, 40);

        frameBar.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                setLocation(e.getXOnScreen() - xOffset, e.getYOnScreen() - yOffset);
            }
        });
        frameBar.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                xOffset = e.getX();
                yOffset = e.getY();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                setLocation(e.getXOnScreen() - xOffset, e.getYOnScreen() - yOffset);
            }
        });

        JLabel icon = new JLabel(new ImageIcon(new ImageIcon("resources/images/icon.png").getImage().getScaledInstance(40, 30, Image.SCALE_SMOOTH)));
        icon.setBounds(5, 5, 40, 30);
        frameBar.add(icon);

        JButton minimizeButton = new JButton("_");
        minimizeButton.setBounds(390, -20, 60, 70);
        minimizeButton.setForeground(Color.WHITE);
        minimizeButton.setFont(new Font("Arial", Font.BOLD, 16));
        minimizeButton.setBackground(new Color(0, 0, 0, 0));
        minimizeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
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

        JButton exitButton = new JButton("X");
        exitButton.setBounds(390 + 55, -5, 60, 50);
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
        frameBar.add(minimizeButton);
        frameBar.add(exitButton);

        JLabel title = new JLabel("About");
        title.setFont(new Font("Z003", Font.BOLD, 20));
        title.setForeground(Color.white);
        title.setBounds(500 / 2 - 25, 10, 50, 30);
        frameBar.add(title);

        add(frameBar);
    }

}
