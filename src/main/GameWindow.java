package main;
import javax.swing.JFrame;


public class GameWindow extends JFrame {

    public GameWindow() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Shadows Of Despair");

        GamePanel gamePanel = new GamePanel();
        this.add(gamePanel);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        gamePanel.setUpGame();
        gamePanel.startGameThread();
    }
}
