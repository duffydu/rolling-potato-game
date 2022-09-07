package ui;

import model.Obstacle;
import model.ScoreManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
    class GamePanel constructs a JPanel that runs the rolling potato game UI
 */
public class GamePanel extends JPanel implements ActionListener, KeyListener {
    // constants
    final int panelWidth = 1080;
    final int panelHeight = 500;
    final int scorePosX = 450;
    final int scorePosY = 15;

    // Player fields
    Player player;
    Image potatoImage;
    Image backgroundImage;
    Timer timerX;
    int dx = -5;
    int dy = -7;
    int groundLevel = 337;
    int xcoord = 0;
    int ycoord = groundLevel;
    Boolean jump = false;
    int jumpingHeight = 120;

    // obstacles Fields
    Image obstacleImage;
    Obstacle obstacle;

    // scores fields
    int score;
    int highScore;
    ScoreManager scoreManager;

    // Constructor
    // EFFECTS: initialize variables (scoreManager, player, images, timer, obstacles)
    //          initialize panel metrics
    public GamePanel(ScoreManager sc) {
        scoreManager = sc;
        highScore = scoreManager.getHighScore();
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setBackground(Color.white);
        player = new Player();
        potatoImage = new ImageIcon("src/potato.png").getImage();
        backgroundImage = new ImageIcon("src/groundSquare.png").getImage();
        obstacleImage = new ImageIcon("src/broccoli.png").getImage();
        timerX = new Timer(20, this);
        score = 0;
        obstacle = new Obstacle();
        obstacle.addObstacle(1000, 1080);
    }

    // MODIFIES: Graphics g, this
    // EFFECTS: draws images (background, player, obstacles, scores)
    //          according to their corresponding x and y coordinates on the gamePanel
    @Override
    public void paint(Graphics g) {
        super.paint(g); // paint background

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(backgroundImage, xcoord, 25, null);
        g2d.drawImage(backgroundImage, xcoord + 360, 25, null);
        g2d.drawImage(backgroundImage, xcoord + 360 * 2, 25, null);
        g2d.drawImage(backgroundImage, xcoord + 360 * 3, 25, null);
        player.draw(g2d, 0, ycoord, this);
        if (obstacle.sum() <= panelWidth) {
            obstacle.addObstacle(obstacle.sum(), 1080);
        }
        obstacle.drawObstacles(g2d);
        highScore = scoreManager.getHighScore();
        String text = ("Score: " + score + "  High Score: " + highScore);
        g2d.drawString(text, scorePosX, scorePosY);
    }

    // MODIFIES: this
    // EFFECTS: starts the time to start the game and add keyListener to the panel
    public void start() {
        timerX.start();
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
    }

    // EFFECTS: check if the potato has crashed into a broccoli
    //          if yes return true, otherwise false
    public boolean crashed() {
        if (obstacle.getFirst() > -20 && obstacle.getFirst() < 20 && player.getY() >= groundLevel - 60) {
            System.out.println("first obstacle coordinate = " + obstacle.getFirst());
            System.out.println("player Y coordinate = " + player.getY());
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: if the player crashed into an obstacle, end the game
    //          otherwise keep going
    @Override
    public void actionPerformed(ActionEvent e) {
        if (crashed()) {
            //stop timer
            timerX.stop();
            // pop up game over
            String message = "Crashed into an obstacle! Game Over! Score: " + score;
            System.out.println(message);
            scoreManager.addScoreEntry(score);
            scoreManager.updateHighScore();
            repaint();
            JOptionPane.showMessageDialog(null, message);
        } else {
            // otherwise, keep the game going
            updateCoordinates();
            score += 20;
            repaint();
        }
    }

    // MODIFIES: this
    // EFFECTS: updates the x coordinates of player, obstacles, background after each timer tick
    //          if player jumped, then update the y coordinates after each timer tick accordingly
    private void updateCoordinates() {
        // decrement x coordinate per timer tick by x velocity
        if (xcoord <= -360) {
            xcoord = 0;
        } else {
            xcoord += dx;
            obstacle.advance(dx);
        }

        if (obstacle.getFirst() <= -50) {
            obstacle.removeFirstObstacle();
        }

        // jumping and change in y direction
        if (jump) {
            if (ycoord <= groundLevel - jumpingHeight) {
                dy = -dy;
            }
            ycoord += dy;
            if (ycoord > groundLevel) {
                jump = false;
                ycoord = groundLevel;
                dy = -5;
            }
            player.jump(ycoord);
        }
    }

    // MODIFIES: this
    // EFFECTS: if the player pressed the space bar, set jump to true
    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyCode() == 0) {
            jump = true;
        }
    }

    // STUB
    @Override
    public void keyPressed(KeyEvent e) {
    }

    // STUB
    @Override
    public void keyReleased(KeyEvent e) {
    }
}
