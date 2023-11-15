import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel implements MouseListener, MouseMotionListener {
	static final int SCREEN_WIDTH = 450;
	static final int SCREEN_HEIGHT = 450;

	private Level levelOne;

	GamePanel() {
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		init();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		render(g);
	}

	public void init() {
		// int[][] levelData = {
		// { 1, 1, 2 },
		// { 0, 0, 0 },
		// { 0, 0, 2 }
		// };
		int[][] levelData = {
				{ 1, 0, 0, 0, 0 },
				{ 2, 1, 0, 5, 0 },
				{ 0, 3, 4, 0, 0 },
				{ 0, 0, 5, 0, 4 },
				{ 2, 0, 0, 0, 3 }
		};
		// int[][] levelData = {
		// { 1, 0, 2 },
		// { 0, 0, 3 },
		// { 0, 1, 2 },
		// { 0, 3, 0 }
		// };
		levelOne = new Level(SCREEN_WIDTH, SCREEN_HEIGHT);
		levelOne.setGrid(levelData);
	}

	public void update() { // Unused for now
	}

	public void render(Graphics g) {
		levelOne.render(g);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		levelOne.mousePressEvent(new Vector2i(e.getX(), e.getY()));
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		levelOne.mouseDragEvent(new Vector2i(e.getX(), e.getY()));
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		levelOne.mouseReleasedEvent();
		repaint();

		if (levelOne.isGameOver()) {
			System.exit(0);
		}
	}

	// ------------------------- IGNORE CODE BELOW ------------------------ //

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

}