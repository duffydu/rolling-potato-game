package ui;

import model.*;
import model.Event;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.*;

// RollingPotatoApp is a class that provides console user interface for the player to play the game Rolling Potato
public class RollingPotatoApp extends JFrame implements ActionListener {
    //fields
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 700;
    ScoreManager scoreManager;

    // GUI components
    GamePanel gamePanel;
    JPanel panel = new JPanel();
    JPanel startPanel = new JPanel();
    JPanel gifPanel = new JPanel();
    JPanel menuPanel = new JPanel();
    JButton startButton = new JButton("START");
    JLabel usernameLabel = new JLabel("Enter Username: ");
    JTextField usernameField = new JTextField("username", 10);
    JLabel displayPane = new JLabel();
    JButton menu1 = new JButton("View Past Score Histories");
    JButton menu2 = new JButton("View Highest Score");
    JButton menu3 = new JButton("View Leadership Board");
    JButton menu4 = new JButton("Load Score History from File");
    JButton exitButton = new JButton("EXIT");

    // constructor
    // EFFECTS: runs the Rolling Potato Application
    public RollingPotatoApp() {
        super("Rolling Potato");
        scoreManager = new ScoreManager();
        scoreManager.loadLeadershipBoard();
        scoreManager.updateHighScore();
        initializeGraphics();
    }

    // EFFECTS: set up the JFrame with appropriate GUI components
    private void initializeGraphics() {
        //jFrame set up
        setTitle("Rolling Potato");
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //JPanel set up
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        panel.setLayout(new BorderLayout());

        //GUI
        startPanel.add(usernameLabel, FlowLayout.LEFT);
        startPanel.add(usernameField, FlowLayout.CENTER);
        startPanel.add(startButton);
        startButton.setPreferredSize(new Dimension(100, 65));
        startPanel.setAlignmentX(Component.CENTER_ALIGNMENT);


        gamePanel = new GamePanel(scoreManager);
        gifPanel.add(gamePanel);

        menuPanelSetUp();
        panel.add(startPanel, BorderLayout.PAGE_START);
        panel.add(gifPanel, BorderLayout.CENTER);
        panel.add(menuPanel, BorderLayout.PAGE_END);

        addingActionListeners();

        add(panel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // EFFECTS: add ActionListener to buttons
    private void addingActionListeners() {
        startButton.addActionListener(this);
        menu1.addActionListener(this);
        menu2.addActionListener(this);
        menu3.addActionListener(this);
        menu4.addActionListener(this);
        exitButton.addActionListener(this);
    }

    // EFFECTS: set up menuPanel with appropriate GUI components
    private void menuPanelSetUp() {
        //Menu GUI components
        menuPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        displayPaneSetUp(c);
        menuPanel.add(displayPane, c);
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 3;
        c.gridy = 0;
        menuPanel.add(menu1, c);
        c.gridx = 3;
        c.gridy = 1;
        menuPanel.add(menu2, c);
        c.gridx = 4;
        c.gridy = 0;
        menuPanel.add(menu3, c);
        c.gridx = 4;
        c.gridy = 1;
        menuPanel.add(menu4, c);
        c.gridheight = 2;
        c.gridx = 5;
        c.gridy = 0;
        menuPanel.add(exitButton, c);
    }

    // EFFECTS: set up the displayPane with appropriate text and dimensions
    private void displayPaneSetUp(GridBagConstraints c) {
        displayPane.setText("Display Pane");
        c.ipady = 25;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 2;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.BOTH;
    }

    // EFFECTS: process ActionEvent from JButtons and call appropriate methods
    @Override
    public void actionPerformed(ActionEvent e) {
        // If start button is pressed, potato start moving
        switch (e.getActionCommand()) {
            case "START":
                startGame();
                break;
            case "RESTART":
                restartGame();
                break;
            case "View Past Score Histories":
                viewScores(scoreManager.getPlayerHistory());
                break;
            case "View Highest Score":
                // View the highest score
                displayPane.setText(scoreManager.getPlayerHistory().viewHighestScoreEntry());
                break;
            case "View Leadership Board":
                // View Leadership Board
                viewScores(scoreManager.getLeadershipBoard());
                break;
            case "Load Score History from File":
                // Load score history from file
                loadFromFile();
                break;
            case "EXIT":
                exitGame();
                break;
        }
    }

    // EFFECTS: ask to save scores and exit the game
    private void exitGame() {
        // pop up asking to save current session scores
        int reply = JOptionPane.showConfirmDialog(null,
                "Do you want to save your scores?",
                "Save",
                JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            //save scores
            scoreManager.saveScores();
        }
        scoreManager.printEventLog();

        // end the program
        System.exit(0);
    }

    // EFFECTS: loading past score histories from a file
    //          error message displayed when loading is unsuccessful
    private void loadFromFile() {
        if (scoreManager.loadScoreHistory()) {
            JOptionPane.showMessageDialog(null, "Successfully loaded");
        } else {
            JOptionPane.showMessageDialog(null,
                    "Load from file Unsuccessful!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


    // EFFECTS: updating the displayPane with score histories
    private void viewScores(ScoreHistory playerHistory) {
        // 2 - View all past score histories
        ArrayList<ScoreEntry> scoresList = playerHistory.displayAllScores();
        // update display panel
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        for (ScoreEntry entry : scoresList) {
            sb.append(entry.printEntry());
            sb.append("<br/>");
        }
        sb.append("<html>");
        displayPane.setText(sb.toString());
    }

    // EFFECTS: reset components and restart the game
    private void restartGame() {
        // reset scoreCount
        scoreManager.setScoreCount(0);
        gifPanel.remove(gamePanel);
        gamePanel = new GamePanel(scoreManager);
        gifPanel.add(gamePanel);
        gifPanel.revalidate();
        gifPanel.repaint();
        System.out.println("Game Restarted!");
        gamePanel.start();
    }

    // EFFECTS: start the rolling potato game
    private void startGame() {
        System.out.println("Game Started!");
        if (scoreManager.getPlayerHistory() == null) {
            scoreManager.setPlayerHistory(new PlayerHistory(usernameField.getText()));
        }
        scoreManager.getPlayerHistory().setUsername(usernameField.getText());
        gamePanel.start();
        startButton.setText("RESTART");
    }
}
