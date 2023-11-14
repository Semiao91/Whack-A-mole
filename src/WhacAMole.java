import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class WhacAMole {
    int boardWidth = 600;
    int boardHeight = 650;
    int score = 0;

    JFrame frame = new JFrame("Mario: Whac A Mole");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JButton newGame = new JButton("New Game");
    FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 50, 0);

    JButton[] board = new JButton[9];
    ImageIcon moleIcon, plantIcon;

    JButton currMoleTile;
    JButton currPlantTile;

    Random random = new Random();
    Timer setMoleTimer;
    Timer setPlantTimer;

    WhacAMole() {
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setFont(new Font("Arial", Font.PLAIN, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Score: ");
        textLabel.setOpaque(false);

        textPanel.setLayout(layout);
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        textPanel.add(newGame).setFocusable(false);
        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setPlantTimer.start();
                setMoleTimer.start();
                textLabel.setText("Score: " + score);
                for (int i = 0; i < 9; i++) {
                    board[i].setEnabled(true);
                    board[i].setIcon(null);
                }
                frame.setVisible(true);
            }
        });

        boardPanel.setLayout(new GridLayout(3, 3));
        frame.add(boardPanel);

        Image plantImage = new ImageIcon(getClass().getResource("./piranha.png")).getImage();
        plantIcon = new ImageIcon(plantImage.getScaledInstance(150, 150, java.awt.Image.SCALE_AREA_AVERAGING));

        Image moleImage = new ImageIcon(getClass().getResource("./monty.png")).getImage();
        moleIcon = new ImageIcon(moleImage.getScaledInstance(150, 150, java.awt.Image.SCALE_AREA_AVERAGING));

        for (int i = 0; i < 9; i++) {
            JButton tile = new JButton();
            board[i] = tile;
            boardPanel.add(tile);
            tile.setFocusable(false);
            tile.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton tile = (JButton) e.getSource();

                    if (tile == currMoleTile) {
                        score += 1;
                        textLabel.setText("Score: " + score);
                    } else if (tile == currPlantTile) {
                        score = 0;
                        textLabel.setText("Game Over");
                        setPlantTimer.stop();
                        setMoleTimer.stop();
                        for (int i = 0; i < 9; i++) {
                            board[i].setEnabled(false);
                            board[i].setIcon(null);
                        }
                    }
                }
            });
        }
        setMoleTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currMoleTile != null && currMoleTile != currPlantTile) {
                    currMoleTile.setIcon(null);
                    currMoleTile = null;
                }

                int num = random.nextInt(9);
                JButton tile = board[num];

                currMoleTile = tile;
                currMoleTile.setIcon(moleIcon);
            }
        });

        setPlantTimer = new Timer(1500, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currPlantTile != null && currPlantTile != currMoleTile) {
                    currPlantTile.setIcon(null);
                    currPlantTile = null;
                }

                int num = random.nextInt(9);
                JButton tile = board[num];

                currPlantTile = tile;
                currPlantTile.setIcon(plantIcon);
            }
        });

        setPlantTimer.start();
        setMoleTimer.start();
        frame.setVisible(true);
    }
}