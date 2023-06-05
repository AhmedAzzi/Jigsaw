package puzzle;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author soumia
 */
public class SetLevel extends JFrame implements ActionListener {

    private final JLabel intro;
    private final JPanel panelSelectLevel; // // That Containe All the radio btns
    private final JRadioButton easy;
    private final JRadioButton medium;
    private final JRadioButton hard;
    private final JRadioButton so_hard;
    private JButton play;
    private int xOffset;
    private int yOffset;
    private int row;
    private int col;
    private String name;
    private final JPanel imagePanel;
    private final JLabel imageLabel;
    private final JButton prevButton;
    private final JButton nextButton;
    private int currentImage = 0;
    ArrayList<String> imageFiles = new ArrayList<>() {
        {
            add("src/cow.jpg");
            add("src/dolphin.jpg");
            add("src/Nasa.jpg");
        }
    };

    // Définition de la classe SetLevel
    public SetLevel() {
        setUndecorated(true);//tagla3 li fiha - et x
        customFrameBar();
        this.setLayout(null);//bach nwalou n9adou npozitiono lobj kima rana baghyin

        //hight ta3 l'ecran-panel
        int screenHeight = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
        int ScreenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;//3rd ta3 ay pc
        setSize(ScreenWidth, screenHeight);//size ta3 frame

        intro = new JLabel("Choose another Image Here");
        intro.setFont(new Font("Z003", Font.ITALIC, 30));
        intro.setBounds(ScreenWidth / 2 - 200, 40, 400, 50);//blassatha
        this.add(intro);

        //
        JButton fileChooserButton = new JButton(new ImageIcon(new ImageIcon("src/choose.png").getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT)));
        fileChooserButton.setBounds(ScreenWidth / 2 + 150, 30, 100, 60);
        fileChooserButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                fileChooserButton.setIcon(new ImageIcon(new ImageIcon("src/choose.png").getImage().getScaledInstance(60 + 20, 60 + 20, Image.SCALE_FAST)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                fileChooserButton.setIcon(new ImageIcon(new ImageIcon("src/choose.png").getImage().getScaledInstance(60, 60, Image.SCALE_FAST)));
            }

        });
        fileChooserButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        fileChooserButton.setBackground(new Color(0, 0, 0, 0));

        fileChooserButton.addActionListener(e -> {
            fileChooser();
        });
        this.add(fileChooserButton);

        imagePanel = new JPanel();
        imagePanel.setBounds(ScreenWidth / 2 - 340, 90, 680, 480);
        add(imagePanel);

        ImageIcon image = new ImageIcon(imageFiles.get(0));
        imageLabel = new JLabel(new ImageIcon(image.getImage().getScaledInstance(680, 480, Image.SCALE_FAST)));
        imagePanel.add(imageLabel);

        prevButton = new JButton(new ImageIcon(new ImageIcon("src/prev.png").getImage().getScaledInstance(80, 100, Image.SCALE_SMOOTH)));
        prevButton.setBackground(new Color(0, 0, 0, 0));
        prevButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        prevButton.setBounds(ScreenWidth / 2 - 500, 280, 100, 100);
        add(prevButton);

        nextButton = new JButton(new ImageIcon(new ImageIcon("src/next.png").getImage().getScaledInstance(80, 100, Image.SCALE_SMOOTH)));
        nextButton.setBackground(new Color(0, 0, 0, 0));
        nextButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        nextButton.setBounds(ScreenWidth / 2 + 400, 280, 100, 100);
        add(nextButton);

        prevButton.addActionListener((ActionEvent e) -> {
            currentImage--;
            if (currentImage < 0) {
                currentImage = imageFiles.size() - 1;
            }
            imageLabel.setIcon(new ImageIcon(new ImageIcon(imageFiles.get(currentImage)).getImage().getScaledInstance(680, 480, Image.SCALE_FAST)));
        });

        nextButton.addActionListener((ActionEvent e) -> {
            currentImage++;
            if (currentImage >= imageFiles.size()) {
                currentImage = 0;
            }
            imageLabel.setIcon(new ImageIcon(new ImageIcon(imageFiles.get(currentImage)).getImage().getScaledInstance(680, 480, Image.SCALE_FAST)));

        });

        // Création et personnalisation du JPanel panelSelectLevel
        panelSelectLevel = new JPanel(null);
        panelSelectLevel.setBounds(0, 600, ScreenWidth, 60);
        this.add(panelSelectLevel);

        // Création des boutons radio et positionnement
        easy = createRadioButton("Easy", ScreenWidth / 2 - 500);
        easy.setSelected(true);
        medium = createRadioButton("Medium", ScreenWidth / 2 - 200);
        hard = createRadioButton("Hard", ScreenWidth / 2 + 100);
        so_hard = createRadioButton("So Hard", ScreenWidth / 2 + 400);

        // Regroupement des boutons radio en un groupe
        groupButtonLevel();

        // Création et personnalisation des boutons play et exitButton
        play = new JButton(new ImageIcon(new ImageIcon("src/playbtn.png").getImage().getScaledInstance(200, 190, Image.SCALE_FAST)));
        play.setBounds(ScreenWidth / 2 - 100, 660, 200, 50);
        play.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        play.setBackground(new Color(0, 0, 0, 0));

        //howver ta3 lbutton
        play.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                play.setIcon(new ImageIcon(new ImageIcon("src/playbtn.png").getImage().getScaledInstance(200 + 20, 190 + 20, Image.SCALE_FAST)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                play.setIcon(new ImageIcon(new ImageIcon("src/playbtn.png").getImage().getScaledInstance(200, 190, Image.SCALE_FAST)));
            }

        });
        play.addActionListener(this);
        this.add(play);
    }// fin du constructeur

    private void ncustomFrameBar() {
        // Add a custom frame bar
        JPanel frameBar = new JPanel(null);
        frameBar.setBackground(Color.decode("#387ba5"));
        frameBar.setBounds(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, 30);

        frameBar.addMouseListener(new MouseAdapter() {
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

        JButton minimizeButton = new JButton("_");
        minimizeButton.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width - 60 - 50, -15, 60, 50);
        minimizeButton.setForeground(Color.WHITE);
        minimizeButton.setFocusable(false);
        minimizeButton.setFont(new Font("Arial", Font.BOLD, 16));
        minimizeButton.setBorder(null);
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
        exitButton.setFocusable(false);
        exitButton.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width - 55, -10, 60, 50);
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

    private void customFrameBar() {
        // Add a custom frame bar
        JPanel frameBar = new JPanel(null);
        frameBar.setBackground(Color.decode("#387ba5"));
        frameBar.setBounds(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, 40);

        JLabel icon = new JLabel(new ImageIcon(new ImageIcon("src/icon.png").getImage().getScaledInstance(40, 30, Image.SCALE_SMOOTH)));
        icon.setBounds(5, 5, 40, 30);
        frameBar.add(icon);

        MouseAdapter frameBarMouseListener = new MouseAdapter() {
            private int xOffset;
            private int yOffset;

            @Override
            public void mousePressed(MouseEvent e) {
                xOffset = e.getX();
                yOffset = e.getY();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                setLocation(e.getXOnScreen() - xOffset, e.getYOnScreen() - yOffset);
            }
        };

        frameBar.addMouseListener(frameBarMouseListener);
        frameBar.addMouseMotionListener(frameBarMouseListener);

        JButton minimizeButton = new JButton("_");
        minimizeButton.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width - 60 - 50, -20, 60, 70);
        minimizeButton.setForeground(Color.WHITE);

        minimizeButton.setFont(new Font("Arial", Font.BOLD, 16));
        minimizeButton.setBorder(null);
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

    private void fileChooser() {
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView());

        //extenssion accepter
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif"));
        int returnValue = fileChooser.showOpenDialog(null);// quand je click 3la open
        if (returnValue == JFileChooser.APPROVE_OPTION)//le fichier li tfahamna 3lih
        {
            File selectedFile = fileChooser.getSelectedFile();
            if (selectedFile.length() < 1000000) {
                name = selectedFile.getPath(); // get the name of the selected file
                // Do something with the file name, e.g. pass it to a variable
                imageFiles.add(name);
                prevButton.doClick();
            } else {
                JOptionPane.showMessageDialog(this, "Image too Large Choose another one");
                fileChooser();
            }

        }
    }

    private JRadioButton createRadioButton(String text, int x) {
        // Créer un nouveau bouton radio avec le texte spécifié
        JRadioButton radioButton = new JRadioButton(text);
        // Définir l'icône sélectionnée pour le bouton radio
        radioButton.setSelectedIcon(new ImageIcon(new ImageIcon("src/r1_o.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        // Définir l'icône non sélectionnée pour le bouton radio
        radioButton.setIcon(new ImageIcon(new ImageIcon("src/r1_c.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        // Définir la police du texte du bouton radio
        radioButton.setFont(new Font("Z003", Font.ITALIC, 30));
        // Définir la position du bouton radio sur le panneau
        radioButton.setBounds(x, 0, 150, 30);

        radioButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        // Ajouter le bouton radio au panneau "panelSelectLevel"
        panelSelectLevel.add(radioButton);
        // Retourner le bouton radio créé
        return radioButton;
    }// fin de la méthode createRadioButton

    private void levRadioButtonSelected() {
        if (easy.isSelected()) {
            this.row = 3;
            this.col = 3;
        } else if (medium.isSelected()) {
            this.row = 4;
            this.col = 4;
        } else if (hard.isSelected()) {
            this.row = 5;
            this.col = 5;
        } else if (so_hard.isSelected()) {
            this.row = 6;
            this.col = 6;
        }
    }

    private void groupButtonLevel() {
        ButtonGroup bgLev = new ButtonGroup();
        bgLev.add(easy);
        bgLev.add(medium);
        bgLev.add(hard);
        bgLev.add(so_hard);
    }//end method groupButtonLevel()

    /**
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        name = imageFiles.get(currentImage);
        if (e.getSource() == play) {
            levRadioButtonSelected();
            new Thread(() -> {
                SwingUtilities.invokeLater(() -> {
                    PuzzlePanel puzzlePanel = new PuzzlePanel(row, col, name);
                    dispose();
                    puzzlePanel.setVisible(true);
                });
            }).start();
        }
    }//fin de la méthode actionPerformed

}
