import javax.swing.JFrame;

public class GameFrame extends JFrame {

    GameFrame() {
        this.setTitle("Connect The Chromosomes");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setContentPane(new MainMenuPanel(this));
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}