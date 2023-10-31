package main;
import javax.swing.JFrame;


public class GameWindow extends JFrame {

    public GameWindow() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Shadows Of Dispair");
        
        //Add title screen and game panel to card panel
        GamePanel gamePanel = new GamePanel();
        this.add(gamePanel);

        //Add card panel to frame
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}
