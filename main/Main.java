package main;



import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main {
    static JFrame window;
    public static void main(String[] args) {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("The Legend Of Zelda");
        new Main().setIcon();

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        gamePanel.config.loadConfig();

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        gamePanel.setUpGame();
        gamePanel.startGameThread();
        
    }

    public void setIcon(){
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("icons/Icon.png"));
        window.setIconImage(icon.getImage());
    }
}
