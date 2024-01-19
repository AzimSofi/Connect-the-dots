import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;

public class MainMenuPanel extends JPanel {
    private Image backgroundImage;
    
    public MainMenuPanel(JFrame frame) {
        setPreferredSize(new Dimension(GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        try {
            backgroundImage = ImageIO.read(this.getClass().getResource("/image/the-kingdom-of-zeal2.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
            backgroundImage = null;
        }

        Dimension buttonSize = new Dimension((int)(GamePanel.SCREEN_WIDTH * 0.6), GamePanel.SCREEN_HEIGHT / 10);
        
        add(Box.createVerticalStrut(GamePanel.SCREEN_HEIGHT / 4));

        // 'Start Game' button
        JButton startButton = new JButton("Start Game");
        startButton.setMaximumSize(buttonSize);
        startButton.setPreferredSize(buttonSize);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.addActionListener(e -> {
            frame.setContentPane(new GamePanel());
            frame.pack();
            frame.revalidate();
        });
        add(startButton);

        // 'Continue' button (game's save/load system)
        JButton instructionsButton = new JButton("Instructions");
        instructionsButton.setMaximumSize(buttonSize);
        instructionsButton.setPreferredSize(buttonSize);
        instructionsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionsButton.addActionListener(e -> {
            showInstructionsDialog(frame);
        });
        add(instructionsButton);

        add(Box.createVerticalStrut(GamePanel.SCREEN_HEIGHT / 6));

        // 'Exit' button
        JButton exitButton = new JButton("Exit");
        exitButton.setMaximumSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener(e -> System.exit(0));
        add(exitButton);

        // Background music setup
        playBackgroundMusic();
    }

    private void playBackgroundMusic() {
        try {
            URL audioUrl = this.getClass().getResource("/audio/作業用BGM時の回廊クロノトリガー.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(audioUrl);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // Determine the scale to make the image cover the entire panel
            double widthScale = (double) getWidth() / backgroundImage.getWidth(null);
            double heightScale = (double) getHeight() / backgroundImage.getHeight(null);
            double scale = Math.max(widthScale, heightScale); // This will ensure the image covers the whole panel

            // Calculate the width and height to maintain the aspect ratio
            int imageWidth = (int) (scale * backgroundImage.getWidth(null));
            int imageHeight = (int) (scale * backgroundImage.getHeight(null));

            // Calculate the top-left corner (x,y) for drawing the image so that we draw only the central part
            int x = (getWidth() - imageWidth) / 2;
            int y = (getHeight() - imageHeight) / 2;

            // Draw the image
            g.drawImage(backgroundImage, x, y, imageWidth, imageHeight, this);
        }
    }

    private void showInstructionsDialog(JFrame frame) {
        String instructionsText = "<html><body><h1>Game Instructions</h1><p>To play Connect The Dots, connect the corresponding dots with lines by clicking and dragging from one dot to another. " + 
        "<br>The goal is to connect all pairs without crossing lines.</p>" +
        "<br><p>Additional rules:</p>" +
        "<ul>" +
        "<li>Lines cannot cross each other.</li>" +
        "<li>All dots must be connected to complete the level.</li>" +
        "</ul>" +
        "<br><p>Good luck and have fun!</p></body></html>";
        JOptionPane.showMessageDialog(frame, instructionsText, "Instructions", JOptionPane.INFORMATION_MESSAGE);
    }

}
