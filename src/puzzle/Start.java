package puzzle;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

/**
 *
 * @author soumia
 */
public class Start extends JFrame implements ActionListener {

    private final JButton start;
    private final JButton about;
    private final JButton exit;
    private final JButton demo;

    // Le constructeur de la classe Start
    public Start() {

        customFrameBar(this, 700, 40, 590);

        setLayout(null); // Définir la disposition à null pour permettre un positionnement manuel des éléments
        setPreferredSize(new Dimension(700, 600)); // Définir les dimensions préférées de la fenêtre

        JLabel name = new JLabel(new ImageIcon(new ImageIcon("resources/images/icon.png").getImage().getScaledInstance(250, 150, 1)));
        name.setBounds(430, 130, 250, 150);
        this.add(name);

        // Ajouter un bouton "Start" pour lancer le jeu
        start = new JButton(new ImageIcon(new ImageIcon("resources/images/start2.png").getImage().getScaledInstance(250, 180, Image.SCALE_FAST)));
        start.setBackground(new Color(0, 0, 0, 0));
        start.setBounds(420, 320, 250, 60);
        start.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        start.addActionListener(this); // Ajouter un écouteur d'événements pour le clic
        start.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                start.setIcon(new ImageIcon(new ImageIcon("resources/images/start2.png").getImage().getScaledInstance(250 + 20, 180 + 20, Image.SCALE_FAST)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                start.setIcon(new ImageIcon(new ImageIcon("resources/images/start2.png").getImage().getScaledInstance(250, 180, Image.SCALE_FAST)));
            }

        });
        this.add(start);

        demo = new JButton(new ImageIcon(new ImageIcon("resources/images/demo.png").getImage().getScaledInstance(250, 180, Image.SCALE_FAST)));
        demo.setBackground(new Color(0, 0, 0, 0));
        demo.setBounds(420, 380, 250, 60);
        demo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        demo.addActionListener(this); // Ajouter un écouteur d'événements pour le clic
        demo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                demo.setIcon(new ImageIcon(new ImageIcon("resources/images/demo.png").getImage().getScaledInstance(250 + 20, 180 + 20, Image.SCALE_FAST)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                demo.setIcon(new ImageIcon(new ImageIcon("resources/images/demo.png").getImage().getScaledInstance(250, 180, Image.SCALE_FAST)));
            }

        });
        this.add(demo);

        about = new JButton(new ImageIcon(new ImageIcon("resources/images/about.png").getImage().getScaledInstance(250, 180, Image.SCALE_FAST)));

        about.setBackground(new Color(0, 0, 0, 0));
        about.setBounds(420, 440, 250, 60);
        about.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        about.addActionListener(this); // Ajouter un écouteur d'événements pour le clic
        about.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                about.setIcon(new ImageIcon(new ImageIcon("resources/images/about.png").getImage().getScaledInstance(250 + 20, 180 + 20, Image.SCALE_FAST)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                about.setIcon(new ImageIcon(new ImageIcon("resources/images/about.png").getImage().getScaledInstance(250, 180, Image.SCALE_FAST)));
            }

        });
        this.add(about);

        exit = new JButton(new ImageIcon(new ImageIcon("resources/images/exit.png").getImage().getScaledInstance(250, 180, Image.SCALE_FAST)));
        exit.setBackground(new Color(0, 0, 0, 0));
        exit.setBounds(420, 500, 250, 60);
        exit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        exit.addActionListener(this); // Ajouter un écouteur d'événements pour le clic
        exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                exit.setIcon(new ImageIcon(new ImageIcon("resources/images/exit.png").getImage().getScaledInstance(250 + 20, 180 + 20, Image.SCALE_FAST)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exit.setIcon(new ImageIcon(new ImageIcon("resources/images/exit.png").getImage().getScaledInstance(250, 180, Image.SCALE_FAST)));
            }

        });
        this.add(exit);

        // Ajouter une image de fond pour la fenêtre getClass().getResource(imagePath)
        JLabel bg = new JLabel(new ImageIcon(new ImageIcon("resources/images/bg4.jpg").getImage().getScaledInstance(400, 600, Image.SCALE_SMOOTH)));
        bg.setBounds(0, 40, 400, 600);
        this.add(bg);
        this.pack(); // Ajuster la taille de la fenêtre en fonction des éléments
        this.setLocationRelativeTo(null); // Centrer la fenêtre sur l'écran
    }

    private void customFrameBar(JFrame f, int width, int height, int x) {
        f.setUndecorated(true);

        JPanel frameBar = new JPanel(null);
        frameBar.setBackground(Color.decode("#387ba5"));
        frameBar.setBounds(0, 0, width, height);

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

        JButton minimizeButton = new JButton("_");
        minimizeButton.setBounds(x, -20, 60, 70);
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
        exitButton.setBounds(x + 55, -5, 60, 50);
        exitButton.setBackground(new Color(0, 0, 0, 0));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFont(new Font("Arial", Font.BOLD, 16));
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
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

        JLabel title = new JLabel("Puzzle Game");
        title.setFont(new Font("Z003", Font.BOLD, 20));
        title.setForeground(Color.white);
        title.setBounds(width / 2 - 50, 10, 100, 30);
        frameBar.add(title);

        f.add(frameBar);
    }

    // La méthode principale
    public static void main(String[] args) throws IOException, UnsupportedLookAndFeelException {
        // Définit l'apparence de l'interface utilisateur comme Nimbus
        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        // Crée une nouvelle instance de la classe Start et la rend visible
        new Start().setVisible(true);
    }

    // La méthode appelée lorsqu'un événement est déclenché
    @Override
    public void actionPerformed(ActionEvent e) {
        // Vérifie si l'événement a été déclenché par le bouton "Start"
        if (e.getSource() == start) {
            new Thread(() -> {
                // Effectue la transition vers une autre fenêtre en créant une nouvelle instance de SetLevel et en la rendant visible
                SwingUtilities.invokeLater(() -> {
                    SetLevel setLevel = new SetLevel();
                    setLevel.setVisible(true);
                    dispose(); // ferme la fenêtre actuelle
                });
            }).start();
        } else if (e.getSource() == exit) {
            dispose();
        } else if (e.getSource() == about) {
            new About();
        } else if (e.getSource() == demo) {
            new Demo();

        }
    }

}
