import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener{

    static final int UNIT_SIZE = 150;
	static final int SCREEN_WIDTH = UNIT_SIZE*3;
	static final int SCREEN_HEIGHT = UNIT_SIZE*3;
	boolean running = false;

    private Level levelOne;
	
	GamePanel(){
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	
	public void startGame() {
        running = true;

        int[][] levelData = {
            {1, 0, 2},
            {0, 0, 0},
            {0, 1, 2}
        };

        levelOne = new Level(levelData);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {

        if (running) {

            for (int i = 0; i < SCREEN_WIDTH / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }

            if (levelOne != null) {
                levelOne.paint(g, UNIT_SIZE);
            }
        } else {
            gameOver(g);
        }
    }

	public void move(){
	}

	public void checkCollisions() {
		//checks if head collides with body
		// for(int i = bodyParts;i>0;i--) {
		// 	if((x[0] == x[i])&& (y[0] == y[i])) {
		// 		running = false;
		// 	}
		// }
		// //check if head touches left border
		// if(x[0] < 0) {
		// 	running = false;
		// }
		// //check if head touches right border
		// if(x[0] > SCREEN_WIDTH) {
		// 	running = false;
		// }
		// //check if head touches top border
		// if(y[0] < 0) {
		// 	running = false;
		// }
		// //check if head touches bottom border
		// if(y[0] > SCREEN_HEIGHT) {
		// 	running = false;
		// }
		
		// if(!running) {
		// 	timer.stop();
		// }
	}

	public void gameOver(Graphics g) {
		//Score
		// g.setColor(Color.red);
		// g.setFont( new Font("Ink Free",Font.BOLD, 40));
		// FontMetrics metrics1 = getFontMetrics(g.getFont());
		// g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
		//Game Over text
		g.setColor(Color.red);
		g.setFont( new Font("Ink Free",Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Good game", (SCREEN_WIDTH - metrics2.stringWidth("Good game"))/2, SCREEN_HEIGHT/2);
	}
    
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(running) {
			// move();
			// checkApple();
			// checkCollisions();
		}
		repaint();
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			// switch(e.getKeyCode()) {
			// case KeyEvent.VK_LEFT:
			// 	if(direction != 'R') {
			// 		direction = 'L';
			// 	}
			// 	break;
			// case KeyEvent.VK_RIGHT:
			// 	if(direction != 'L') {
			// 		direction = 'R';
			// 	}
			// 	break;
			// case KeyEvent.VK_UP:
			// 	if(direction != 'D') {
			// 		direction = 'U';
			// 	}
			// 	break;
			// case KeyEvent.VK_DOWN:
			// 	if(direction != 'U') {
			// 		direction = 'D';
			// 	}
			// 	break;
			// }
		}
	}
	
}