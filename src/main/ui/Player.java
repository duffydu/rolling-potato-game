package ui;

import javax.swing.*;
import java.awt.*;

/*
    Player class keeps track of the x and y position of the player and updates them accordingly
 */
public class Player {
    //X- and Y-coordinate fields
    private final int posX = 0;
    private int posY = 337;

    //Player graphic
    Image potatoImage = Toolkit.getDefaultToolkit().getImage("src/potato.png");

    // Constructor
    public Player() {
    }

    // MODIFIES: Graphics2D g
    // EFFECTS: draw the potatoImage at appropriate x and y coordinates onto the specified JPanel
    public void draw(Graphics2D g, JPanel jp) {
        //Draw the Image at the right position
        g.drawImage(potatoImage, posX, posY, jp);
    }

    // MODIFIES: Graphics2D g
    // EFFECTS: draw the potatoImage at specified x and y coordinates onto the specified JPanel
    public void draw(Graphics2D g, int x, int y, JPanel jp) {
        //Draw the Image at the right position
        g.drawImage(potatoImage, x, y, jp);
    }

    // EFFECTS: return player X coordinate
    public int getX() {
        return posX;
    }

    // EFFECTS: return player Y coordinate
    public int getY() {
        return posY;
    }

    // EFFECTS: update Y coordinate according to parameters after a jump
    public void jump(int y) {
        posY = y;
    }
}