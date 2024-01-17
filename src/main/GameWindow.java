package main;
import javax.swing.JFrame;


/** Window frame where the game panel is displayed.
 * @author david.f@opendeusto.es*/
@SuppressWarnings("serial")
public class GameWindow extends JFrame {

    /** Creates a GameWindow.*/
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
