package puzzle;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.util.Timer;
import java.util.TimerTask;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author soumia
 */
public class PuzzlePanel extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    // Cette variable représente un panneau (ou une zone) de l'interface graphique qui sera utilisé pour afficher quelque chose.
    private final JPanel panel;
    // Cette variable représente une image source, c'est-à-dire une image qui sera utilisée pour créer d'autres images.
    private transient BufferedImage source;
    // Cette variable représente une image redimensionnée qui sera affichée sur l'interface graphique.
    private transient ImageIcon resized;
    // Cette variable représente une liste de boutons qui seront affichés sur l'interface graphique.
    private final transient List<JButton> buttons;
    // Cette variable représente une liste de points qui seront utilisés pour déterminer la solution du puzzle.
    private transient List<Point> solution;
    // Cette variable représente un objet de type Timer qui sera utilisé pour mesurer le temps écoulé.
    private transient Timer timer;
    // Cette variable représente le nombre de secondes qui se sont écoulées depuis le début du jeu.
    private int seconds;
    // Cette variable représente un bouton qui permettra à l'utilisateur de revenir en arrière dans le jeu.
    private final JButton backButton;
    // Cette variable est un booléen qui permettra de savoir si le joueur a déjà effectué un premier clic ou non.
    private boolean firstClick = false;
    // Cette variable représente l'indice du dernier bouton cliqué par le joueur.
    private int lastClicked = 0;
    private final JLabel timerLabel;
    private int period = 0;
    private static boolean gameOver = false;
    private static boolean isWin = false;
    private JRadioButton pause;
    private final JButton restart;
    private static int w, h;
    private String imageName;
    private final JLabel one;
    private final JLabel two;
    private final JLabel three;
    private transient Image icon01 = new ImageIcon("resources/images/01.png").getImage().getScaledInstance(80 + 50, 120 + 50, Image.SCALE_SMOOTH);
    private transient Image icon02 = new ImageIcon("resources/images/02.png").getImage().getScaledInstance(80 + 50, 120 + 50, Image.SCALE_SMOOTH);
    private transient Image icon03 = new ImageIcon("resources/images/03.png").getImage().getScaledInstance(80 + 50, 120 + 50, Image.SCALE_SMOOTH);
    private int sec;
    private JLabel label;
    private JLabel finalImage;
    private JLabel finale;

    public PuzzlePanel(int height, int width, String s) {

        customFrameBar();
        this.getContentPane().setBackground(Color.decode("#d3c3b6"));

        setLayout(null);
        w = h = width;
        imageName = s;
        isWin = false;
        sec = (w == 3 ? 30 : (w == 4 ? 60 : (w == 5 ? 90 : (w == 6 ? 120 : 0))));

        int screenHeight = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;

        setSize(screenWidth, screenHeight);

        // Générer la liste de points de solution pour le puzzle
        // On génère une séquence d'entiers allant de 0 à (hauteur x largeur)-1
        solution = new ArrayList<>(); // Create a new list to store the points

        for (int i = 0; i < height * width; i++) { // Loop through the range of integers
            int w = i / width; // Calculate the w coordinate
            int col = i % width; // Calculate the column coordinate
            Point point = new Point(w, col); // Create a new Point object
            solution.add(point); // Add the Point object to the list
        }

        try {
            // Charger l'image à partir du fichier source
            source = ImageIO.read(new File(s));

            // Redimensionner l'image à une taille fixe pour la grille de boutons
            resized = new ImageIcon(source.getScaledInstance(900, 600, Image.SCALE_SMOOTH));

        } catch (IOException ex) {
            System.err.println("Images not found");
        }

        panel = new JPanel(new GridLayout(height, width, 20, 20));
        panel.setOpaque(false);
        int panelWidth = 900;
        int panelHeight = 580;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        panel.setBounds((screenSize.width - panelWidth) / 2 - 150, (screenSize.height - panelHeight) / 2 + 20, panelWidth, panelHeight);
        add(panel);

        // Créer des boutons pour chaque portion d'image
        buttons = new ArrayList<>(); // Create a new list to store the buttons

        for (int i = 0; i < height * width; i++) { // Loop through the range of integers
            int x = i % width; // Calculate the x position of the image portion
            int y = i / width; // Calculate the y position of the image portion

            // Create a new image by cropping the source image based on x and y positions
            Image image = createImage(new FilteredImageSource(resized.getImage().getSource(),
                    new CropImageFilter(x * resized.getIconWidth() / width,
                            y * resized.getIconHeight() / height,
                            resized.getIconWidth() / width, resized.getIconHeight() / height)));

            // Create a JButton with the cropped image as its icon
            JButton button = new JButton(new ImageIcon(image));

            // Store the position of the image portion in the button's client properties
            button.putClientProperty("position", new Point(y, x));
            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            // Add a click listener to the button
            button.addActionListener(this::handleClick);

            buttons.add(button); // Add the created button to the list of buttons
        }

        // Collecter tous les boutons dans une liste
        // Mélanger les boutons pour le puzzle
        Collections.shuffle(buttons);

        // Ajouter les boutons à la grille de boutons
        buttons.forEach(panel::add);
//        for (JButton btn : buttons) {
//            panel.add(btn);
//        }

        pause = new JRadioButton(new ImageIcon(new ImageIcon("resources/images/pause.png").getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
        pause.setSelectedIcon(new ImageIcon(new ImageIcon("resources/images/play.png").getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
        pause.setRolloverEnabled(false); // disabling hover effect
        pause.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        pause.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width - 150, 35, 80, 80);
        pause.addActionListener(this);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    pause.doClick(); // Trigger the same action as a mouse click
                }
            }
        });
        this.add(pause);

        restart = new JButton(new ImageIcon(new ImageIcon("resources/images/reset.png").getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
        restart.setBackground(new Color(0, 0, 0, 0));
        restart.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        restart.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width - 220, 35, 80, 80);
        restart.addActionListener(e -> {
            restartGame();
        });
        this.add(restart);

        // Create the JLabel
        label = new JLabel();
        label.setBounds(3 * Toolkit.getDefaultToolkit().getScreenSize().width / 4 - 250, 30 + 25, 250, 40);
        label.setFont(new Font("Z003", Font.HANGING_BASELINE, 28));
        add(label);

        try {
            BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
            String line;
            StringBuilder fileContent = new StringBuilder();
            String difficulty = (w == 3 ? "easy" : (w == 4 ? "medium" : (w == 5 ? "hard" : (w == 6 ? "so hard" : "none"))));

            // Read the file line by line and process each line
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    int number = Integer.parseInt(parts[1].trim());
                    if (parts[0].trim().equals(difficulty)) // Perform operations based on the difficulty
                    {
                        label.setText("Less Elapsed Time: " + number);
                    }
                } else {
                    // Handle invalid line format
                    System.out.println("Invalid line format: " + line);
                }

                // Append the original or modified line to the file content
                fileContent.append(line).append("\n");

            }
            reader.close();

            // Write the modified file content back to the file
            BufferedWriter writer = new BufferedWriter(new FileWriter("input.txt"));
            writer.write(fileContent.toString());
            writer.close();
        } catch (IOException e) {
            // Handle file read/write error

        }
        // Créer une étiquette qui affichera le temps écoulé
        timerLabel = new JLabel();
        timerLabel.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 3 - 25, 30 + 25, 100, 40);
        timerLabel.setFont(new Font("Z003", Font.HANGING_BASELINE, 40));
        this.add(timerLabel);

        one = new JLabel(new ImageIcon(icon01));
        one.setBounds(screenWidth - 2 * 115, 335, 80 + 50, 120 + 50);
        add(one);

        two = new JLabel(new ImageIcon(icon02));
        two.setBounds(screenWidth - 2 * 115 - 80, 535, 80 + 50, 120 + 50);
        add(two);

        three = new JLabel(new ImageIcon(icon03));
        three.setBounds(screenWidth - 2 * 115 + 80, 535, 80 + 50, 120 + 50);
        add(three);

        addWindowFocusListener(new WindowFocusListener() {

            @Override
            public void windowLostFocus(WindowEvent e) {
                // Pause the game when the frame loses focus
                pauseGame();
                pause.setSelected(true);
            }

            @Override
            public void windowGainedFocus(WindowEvent we) {
            }
        });

        // Créer une étiquette d'image
        JLabel imageLabel = new JLabel(new ImageIcon(new ImageIcon(s).getImage().getScaledInstance(280, 200, Image.SCALE_DEFAULT)));
        imageLabel.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width - 310, 60 + 60, 280, 180);
        add(imageLabel);

        // Créer un bouton de retour
        backButton = new JButton(new ImageIcon(new ImageIcon("resources/images/back.png").getImage().getScaledInstance(200, 180, Image.SCALE_SMOOTH)));
        backButton.addActionListener(this);
        backButton.setBackground(new Color(0, 0, 0, 0));
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        backButton.setBounds(0, screenHeight - screenHeight + 45, 120, 60);
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                backButton.setIcon(new ImageIcon(new ImageIcon("resources/images/back.png").getImage().getScaledInstance(200 + 5, 180 + 5, Image.SCALE_FAST)));
                backButton.setSize(120 + 5, 60 + 5);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                backButton.setIcon(new ImageIcon(new ImageIcon("resources/images/back.png").getImage().getScaledInstance(200, 180, Image.SCALE_FAST)));
                backButton.setSize(120, 60);
            }

        });

        add(backButton);

        // Launcer le jeu
        startGame();
    }

    private void customFrameBar() {
        setUndecorated(true);

        // Add a custom frame bar
        JPanel frameBar = new JPanel(null);
        frameBar.setBackground(Color.decode("#387ba5"));
        frameBar.setBounds(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, 40);

        JLabel icon = new JLabel(new ImageIcon(new ImageIcon("resources/images/icon.png").getImage().getScaledInstance(40, 30, Image.SCALE_SMOOTH)));
        icon.setBounds(5, 5, 40, 30);
        frameBar.add(icon);

        JButton minimizeButton = new JButton("_");
        minimizeButton.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width - 60 - 50, -20, 60, 70);
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
                minimizeButton.setBorder(null);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                minimizeButton.setBackground(new Color(0, 0, 0, 0));
            }
        });

        JButton exitButton = new JButton("X");
        exitButton.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width - 55, -15, 60, 70);
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
                exitButton.setBorder(null);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitButton.setBackground(new Color(0, 0, 0, 0));
            }
        });

        frameBar.add(minimizeButton);
        frameBar.add(exitButton);

        JLabel title = new JLabel("Puzzle Game");
        title.setFont(new Font("Z003", Font.BOLD, 18));
        title.setForeground(Color.white);
        title.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 50, 5, 100, 30);
        frameBar.add(title);

        add(frameBar);
    }

    private void startGame() {
        seconds = (w == 3 ? 30 : (w == 4 ? 60 : (w == 5 ? 90 : (w == 6 ? 120 : 0))));
        period = 1000;
        // Initialiser un minuteur qui mettra à jour le temps toutes les secondes
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String timeString = String.format("%02d:%02d", minutes, secs);

                timerLabel.setText(timeString);
                seconds--;

                if ((w == 3 && seconds > 10 && seconds < 20) || (w == 4 && seconds > 20 && seconds < 40) || (w == 5 && seconds > 30 && seconds < 60)
                        || (w == 6 && seconds > 40 && seconds < 80)) {
                    one.setEnabled(false);
                }
                if ((w == 3 && seconds < 10) || (w == 4 && seconds < 20) || (w == 5 && seconds < 30)
                        || (w == 6 && seconds < 40)) {
                    two.setEnabled(false);
                }
                if (seconds == -1) {
                    three.setEnabled(false);
                    gameOver();
                }
            }
        }, 0, period);
    }

    private void restartGame() {
        timer.cancel();
        panel.removeAll();
        if (isWin) {
            finalImage.setVisible(false);
        }

        if (isWin) {
            if (((w == 3 && seconds >= 20) || (w == 4 && seconds >= 40) || (w == 5 && seconds >= 60)
                    || (w == 6 && seconds >= 80))) {
                finale.setVisible(false);
                one.setIcon(new ImageIcon(icon01));
                one.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width - 2 * 115, 335, 80 + 50, 120 + 50);
            } else if (((w == 3 && seconds >= 10) || (w == 4 && seconds >= 20) || (w == 5 && seconds >= 30)
                    || (w == 6 && seconds >= 40))) {
                two.setIcon(new ImageIcon(icon02));
                two.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width - 2 * 115 - 80, 535, 80 + 50, 120 + 50);
            } else {
                three.setIcon(new ImageIcon(icon03));
                three.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width - 2 * 115 + 80, 535, 80 + 50, 120 + 50);
            }
        }

        isWin = false;
        panel.setVisible(true);
        // Shuffle the buttons
        Collections.shuffle(buttons);

        // Add the shuffled buttons to the panel
        buttons.forEach(panel::add);

        // Disable all buttons to prevent further user interaction for
        for (JButton button : buttons) {
            button.setEnabled(true);
        }
        one.setEnabled(true);
        two.setEnabled(true);
        three.setEnabled(true);

        startGame();

        // Repaint the panel to show the new button arrangement
        panel.revalidate();
        panel.repaint();
        if (!pause.isEnabled()) {
            pause.setEnabled(true);
        }
        if (pause.isSelected()) {
            pause.setSelected(false);
        }
    }

    private void pauseGame() {
        timer.cancel();
        new Thread(() -> {
            SwingUtilities.invokeLater(() -> {
                for (JButton button : buttons) {
                    button.setEnabled(false);
                }
            });
        }).start();

    }

    private void resumeGame() {
        // Check if the game is paused
        // Resume the game by restarting the timer with the remaining time
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String timeString = String.format("%02d:%02d", minutes, secs);
                timerLabel.setText(timeString);
                seconds--;

                if ((w == 3 && seconds > 10 && seconds < 20) || (w == 4 && seconds > 20 && seconds < 40) || (w == 5 && seconds > 30 && seconds < 60)
                        || (w == 6 && seconds > 40 && seconds < 80)) {
                    one.setEnabled(false);
                }
                if ((w == 3 && seconds < 10) || (w == 4 && seconds < 20) || (w == 5 && seconds < 30)
                        || (w == 6 && seconds < 40)) {
                    two.setEnabled(false);
                }
                if (seconds < 0) {
                    three.setEnabled(false);
                    gameOver();
                }
            }
        }, 0, period);
        // Faire la transition
        SwingUtilities.invokeLater(() -> {
            for (JButton button : buttons) {
                button.setEnabled(true);
            }
        });
    }

    private void gameOver() {

        Icon icon = new ImageIcon(new ImageIcon("resources/images/icon.png").getImage().getScaledInstance(40, 30, Image.SCALE_SMOOTH));
        // Move The one JLabel into the middle of screen and scaled here is the code // Affiche un message de félicitations avec le temps écoulé et le score obtenu
        int option = JOptionPane.showConfirmDialog(null,
                "The Time is Finished, You are lost, Do you Want to play again?",
                "GAME OVER",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, icon);

        if (option == JOptionPane.YES_OPTION) {
            SwingUtilities.invokeLater(() -> {
                timer.cancel();
                restartGame();
            });
        } else {

            pause.setEnabled(false);
            pause.setSelected(false);
            SwingUtilities.invokeLater(() -> {
                for (JButton button : buttons) {
                    button.setEnabled(true);
                }
            });
        }
    }

    private void handleClick(ActionEvent e) {
        // Récupère le bouton qui a été cliqué
        JButton button = (JButton) e.getSource();
        if (e.getSource() == button) {
            try {
                Clip clip = AudioSystem.getClip();
                AudioFormat format = new AudioFormat(44100, 16, 1, true, false);

                //AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100.0f, 16, 2, 4, 44100.0f, false);
                AudioInputStream audio = AudioSystem.getAudioInputStream(new File("resources/audio/winner.wav"));

                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(format, audio);
                clip.open(audioInputStream);
                clip.start();
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
                System.out.println("");
            }

        }
        // Récupère l'indice du bouton dans la liste des boutons
        int buttonIndex = buttons.indexOf(button);
        // Si c'est le premier clic
        if (firstClick) {
            // Echange la position du bouton cliqué avec le dernier bouton cliqué
            Collections.swap(buttons, buttonIndex, lastClicked);
            // Supprime tous les boutons du panel
            panel.removeAll();
            // Ajoute chaque bouton un par un au panel
            for (JButton btn : buttons) {
                // Sauf le bouton qui vient d'être cliqué
                if (!btn.equals(button)) {
                    // Enlève la bordure des autres boutons
                    btn.setBorder(null);
                }
                panel.add(btn);
            }
            // Valide la mise à jour du panel
            panel.repaint();
            panel.validate();
        } else {
            // Si c'est le deuxième clic, ajoute une bordure rouge au bouton cliqué
            button.setBorder(BorderFactory.createLineBorder(Color.red, 3, true));
        }
        // Inverse la valeur de la variable firstClick
        firstClick = !firstClick;
        // Met à jour l'indice du dernier bouton cliqué
        lastClicked = buttonIndex;
        // Vérifie si la solution est trouvée
        checkWinner();
    }

    private void checkWinner() {

        List<Point> currentPositions = new ArrayList<>(); // Create a new list to store the Point objects

        for (JButton button : buttons) { // Loop through the list of buttons
            Point position = (Point) button.getClientProperty("position"); // Get the position from the button's client properties
            currentPositions.add(position); // Add the position to the list of current positions
        }

        // Si la solution correspond à la position actuelle des boutons
        if (solution.equals(currentPositions)) {
            pause.setEnabled(false);
            restart.setEnabled(false);

            try {
                BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
                String line;
                StringBuilder fileContent = new StringBuilder();
                String difficulty = (w == 3 ? "easy" : (w == 4 ? "medium" : (w == 5 ? "hard" : (w == 6 ? "so hard" : "none"))));
                int number = 0;
                // Read the file line by line and process each line
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        if (parts[0].trim().equals(difficulty)) {
                            number = Integer.parseInt(parts[1].trim());
                            String s = String.valueOf(number);
                            String ss = String.valueOf((sec - seconds - 1));

                            if (number > (sec - seconds - 1)) {
                                label.setText("Less Elapsed Time: " + String.valueOf(sec - seconds - 1));

                                fileContent.append(line.replace(s, ss)).append("\n");
                            } else {
                                fileContent.append(line).append("\n");
                            }
                        } else {
                            fileContent.append(line).append("\n");
                        }
                    } else {
                        // Handle invalid line format
                        System.out.println("Invalid line format: " + line);
                        // Append the original line to the file content
                        fileContent.append(line).append("\n");

                    }
                }

                reader.close();

                BufferedWriter writer = new BufferedWriter(new FileWriter("input.txt"));
                writer.write(fileContent.toString());
                writer.close();

            } catch (FileNotFoundException ex) {
                Logger.getLogger(PuzzlePanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(PuzzlePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            isWin = true;
            timer.cancel();

            try {
                Clip clip = AudioSystem.getClip();
                try {
                    AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100.0f, 16, 2, 4, 44100.0f, false);

                    AudioInputStream audio = AudioSystem.getAudioInputStream(new File("resources/audio/winner.wav"));
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(format, audio);
                    clip.open(audioInputStream);
                    clip.addLineListener((LineEvent event) -> {
                        if (event.getType() == LineEvent.Type.STOP) {
                            clip.close();
                        }
                    });
                    clip.loop(2);
                } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
                    System.out.println("");
                }
            } catch (LineUnavailableException ex) {
                System.out.println("");
            }

            new Thread(() -> {
                finalImage = new JLabel(resized);
                finalImage.setBounds(panel.getX(), panel.getY(), panel.getWidth(), panel.getHeight());
                PuzzlePanel.this.add(finalImage);
                panel.setVisible(false);

                if ((w == 3 && seconds >= 20) || (w == 4 && seconds >= 40) || (w == 5 && seconds >= 60)
                        || (w == 6 && seconds >= 80)) {
                    Image bg = new ImageIcon("resources/images/bg2.gif").getImage().getScaledInstance(Toolkit.getDefaultToolkit().getScreenSize().width, GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height, Image.SCALE_DEFAULT);

                    finale = new JLabel(new ImageIcon(bg));
                    finale.setBounds(0, 0, panel.getWidth(), panel.getHeight() + 100);

                    //finale.setBounds(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height);
                    finalImage.add(finale);
                    moveAndScaleImage(one, one.getX(), one.getY(), panel.getWidth() / 2 - 40, panel.getHeight() / 2 - 100, 2);

                } else if ((w == 3 && seconds >= 10) || (w == 4 && seconds >= 20) || (w == 5 && seconds >= 30)
                        || (w == 6 && seconds >= 40)) {
                    moveAndScaleImage(two, two.getX(), two.getY(), panel.getWidth() / 2 - 40, panel.getHeight() / 2 - 100, 2);
                } else if ((w == 3 && seconds >= 0) || (w == 4 && seconds >= 0) || (w == 5 && seconds >= 0)
                        || (w == 6 && seconds >= 0)) {
                    moveAndScaleImage(three, three.getX(), three.getY(), panel.getWidth() / 2 - 40, panel.getHeight() / 2 - 100, 2);
                } else {
                    return;
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PuzzlePanel.class.getName()).log(Level.SEVERE, null, ex);
                }   // Move The one JLabel into the middle of screen and scaled here is the code // Affiche un message de félicitations avec le temps écoulé et le score obtenu
                Icon icon = new ImageIcon(new ImageIcon("resources/images/icon.png").getImage().getScaledInstance(40, 30, Image.SCALE_SMOOTH));

                int option = JOptionPane.showConfirmDialog(null,
                        "You finished in " + (sec - seconds - 1) + " secondes, Do you want to go next level?",
                        "Félicitations",
                        JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, icon);
                if (option == JOptionPane.YES_OPTION) {
                    if (w + 1 > 6) {
                        JOptionPane.showMessageDialog(null, "matigch bla bla bla", "Level End", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        SwingUtilities.invokeLater(() -> {
                            new PuzzlePanel(w + 1, h + 1, imageName).setVisible(true);
                            dispose();
                        });
                    }
                } else {
                    SwingUtilities.invokeLater(() -> {
                        //finalImage.setVisible(false);
                        //restart.doClick();
                    });
                }
                restart.setEnabled(true);

            }).start();

        }

    }

    public void moveAndScaleImage(JLabel icon, int startX, int startY, int endX, int endY, double scaleFactor) {
        int frames = 50;
        int startWidth = icon.getWidth();
        int startHeight = icon.getHeight();

        Image originalImage = ((ImageIcon) icon.getIcon()).getImage();

        for (int i = 0; i <= frames; i++) {
            double scale = 1.0 + (scaleFactor - 1.0) * i / frames;
            int width = (int) (startWidth * scale);
            int height = (int) (startHeight * scale);
            Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            icon.setIcon(new ImageIcon(scaledImage));
            icon.setSize(width, height);

            int x = startX + (endX - startX) * i / frames;
            int y = startY + (endY - startY) * i / frames;
            icon.setLocation(x, y);

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == pause) {
            if (pause.isSelected()) {
                pauseGame();
            } else {
                resumeGame();
            }
        }
        // Si l'événement est causé par le bouton "backButton", arrête le timer et réinitialise le temps écoulé
        // puis crée une nouvelle fenêtre "SetLevel" et ferme la fenêtre actuelle pour revenir à la sélection de niveau
        if (e.getSource() == backButton) {
            timer.cancel();
            pauseGame();
            // Effectue une transition vers la fenêtre "SetLevel"
            SwingUtilities.invokeLater(() -> {
                SetLevel setLevel = new SetLevel();
                setLevel.setVisible(true);
                dispose();
            });
        }
    }

}//end class PuzzlePanel
