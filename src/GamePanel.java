import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel implements MouseListener, MouseMotionListener {
	static final int SCREEN_WIDTH = 450;
	static final int SCREEN_HEIGHT = 450;

	private Level[] levels;
	private int currentLevel;

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
		int TOTAL_LEVELS = 3;
		this.levels = new Level[TOTAL_LEVELS];
		this.currentLevel = 0;

		int[][] levelData1 = {
				{ 1, 1 },
				{ 2, 2 }
		};
		this.addLevel(levelData1);

		int[][] levelData2 = {
				{ 1, 1, 2 },
				{ 0, 0, 0 },
				{ 0, 0, 2 }
		};
		this.addLevel(levelData2);

		int[][] levelData3 = {
				{ 1, 0, 0, 0, 0 },
				{ 2, 1, 0, 5, 0 },
				{ 0, 3, 4, 0, 0 },
				{ 0, 0, 5, 0, 4 },
				{ 2, 0, 0, 0, 3 }
		};
		this.addLevel(levelData3);
		// int[][] levelData = {
		// { 1, 0, 2 },
		// { 0, 0, 3 },
		// { 0, 1, 2 },
		// { 0, 3, 0 }
		// };
	}

	public void addLevel(int[][] levelData) {
		Level level = new Level(SCREEN_WIDTH, SCREEN_HEIGHT);
		level.setGrid(levelData);

		for (int i = 0; i < this.levels.length; i++) {
			if (this.levels[i] == null) {
				this.levels[i] = level;
				break;
			}
		}
	}

	public void render(Graphics g) {
		this.levels[this.currentLevel].render(g);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.levels[this.currentLevel].mousePressEvent(new Vector2i(e.getX(), e.getY()));
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		this.levels[this.currentLevel].mouseDragEvent(new Vector2i(e.getX(), e.getY()));
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.levels[this.currentLevel].mouseReleasedEvent();
		repaint();

		if (this.levels[this.currentLevel].isGameOver()) {
			this.currentLevel += 1;

			if (this.currentLevel >= this.levels.length || this.levels[this.currentLevel] == null) {
				System.exit(0);
			}
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