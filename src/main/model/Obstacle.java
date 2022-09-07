package model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/*
    Obstacle is a class that generates and keeps track of a list of the positions of the obstacles
*/
public class Obstacle {
    // constants
    final int minDist = 250;
    final int maxDist = 1080;
    //fields
    int obstaclePos;
    List<Integer> obstacles;
    int groundLevel = 337;

    //constructor
    public Obstacle() {
        obstacles = new ArrayList<>();
    }

    // EFFECTS: return the newest generated obstacle position
    public int getObstaclePos() {
        return obstaclePos;
    }

    // MODIFIES: this
    // EFFECTS: randomly generate an obstacle position based on minDist and maxDist and add it to the list of obstacles
    public void addObstacle() {
        obstaclePos = (int) Math.floor(minDist + Math.random() * maxDist);
        obstacles.add(obstaclePos);
    }

    // MODIFIES: this
    // EFFECTS: randomly generate an obstacle position based on parameters and add it to the list of obstacles
    public void addObstacle(int min, int max) {
        obstaclePos = (int) Math.floor(min + Math.random() * max);
        obstacles.add(obstaclePos);
    }

    // MODIFIES: this
    // EFFECTS: remove the first obstacle from the list of obstacles
    public void removeFirstObstacle() {
        if (!obstacles.isEmpty()) {
            obstacles.remove(0);
        }
    }

    // MODIFIES: Graphics g
    // EFFECTS: draws the Obstacles on the gamePanel
    public void drawObstacles(Graphics g) {
        for (int pos : obstacles) {
            Image obstacleImage = new ImageIcon("src/broccoli.png").getImage();
            g.drawImage(obstacleImage, pos, groundLevel + 20, null);
        }
    }

    // EFFECTS: return the length of the list of obstacles
    public int size() {
        return obstacles.size();
    }

    // EFFECTS: return the sum of all obstacle positions
    public int sum() {
        int sum = 0;
        for (int o : obstacles) {
            sum += o;
        }
        return sum;
    }

    // EFFECTS: return the first obstacle in the list
    public int getFirst() {
        return obstacles.get(0);
    }

    // MODIFIES: this
    // EFFECTS: increment/decrement every int in the obstacles arraylist by specified value
    public void advance(int dx) {
        for (int i = 0; i < obstacles.size(); i++) {
            obstacles.set(i, obstacles.get(i) + dx);
        }
    }
}
