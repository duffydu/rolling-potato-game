package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ObstacleTest {
    Obstacle obstacles;
    int pos1;
    int pos2;

    @BeforeEach
    public void runBefore(){
        obstacles = new Obstacle();
        obstacles.addObstacle();
        pos1 = obstacles.getObstaclePos();
        obstacles.addObstacle(obstacles.getObstaclePos(), 1080);
        pos2 = obstacles.getObstaclePos();
    }

    @Test
    public void testAddObstacle() {
        assertEquals(2,obstacles.size());
    }

    @Test
    public void testRemoveFirstObstacle() {
        obstacles.removeFirstObstacle();
        assertEquals(pos2,obstacles.getFirst());
    }

    @Test
    public void testSum() {
        assertEquals(pos1 + pos2,obstacles.sum());
    }

    @Test
    public void testAdvance() {
        obstacles.advance(-1);
        assertEquals(pos1-1,obstacles.getFirst());
        obstacles.removeFirstObstacle();
        assertEquals(pos2-1,obstacles.getFirst());
    }

}
