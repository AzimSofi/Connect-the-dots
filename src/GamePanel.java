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
		this.currentLevel = 0;

		int[][] levelData1 = {
				{ 1, 1 },
				{ 0, 0 }
		};
		int[][] levelData2 = {
				{ 1, 1 },
				{ 4, 4 }
		};
		int[][] levelData3 = {
				{ 1, 1, 3 },
				{ 0, 0, 0 },
				{ 0, 0, 3 }
		};
		int[][] levelData4 = {
				{ 1, 1, 6 },
				{ 0, 3, 6 },
				{ 0, 0, 3 }
		};
		int[][] levelData5 = {
				{ 1, 0, 0, 0, 0 },
				{ 4, 1, 0, 5, 0 },
				{ 0, 3, 6, 0, 0 },
				{ 0, 0, 5, 0, 6 },
				{ 4, 0, 0, 0, 3 }
		};
		int[][] levelData6 = {
				{ 1, 3, 0, 6, 0 },
				{ 0, 0, 0, 0, 0 },
				{ 2, 0, 0, 0, 0 },
				{ 0, 6, 1, 3, 0 },
				{ 2, 0, 0, 0, 0 }
		};
		int[][] levelData7 = {
				{ 5, 0, 0, 0, 3 },
				{ 0, 4, 1, 0, 0 },
				{ 0, 0, 0, 0, 3 },
				{ 0, 1, 6, 0, 0 },
				{ 0, 5, 0, 6, 4 }
		};
		int[][] levelData8 = {
				{ 5, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0 },
				{ 0, 0, 6, 0, 0 },
				{ 1, 6, 3, 0, 5 },
				{ 3, 0, 0, 0, 1 }
		};
		int[][] levelData9 = {
				{ 0, 0, 0, 0, 2, 5 },
				{ 0, 3, 0, 0, 1, 0 },
				{ 2, 0, 0, 5, 0, 0 },
				{ 0, 0, 0, 0, 0, 6 },
				{ 0, 6, 0, 0, 1, 0 },
				{ 0, 0, 3, 0, 0, 0 }
		};
		int[][] levelData10 = {
				{ 0, 0, 2, 0, 0, 0 },
				{ 0, 3, 0, 0, 6, 0 },
				{ 2, 0, 0, 0, 0, 0 },
				{ 3, 5, 6, 0, 5, 0 },
				{ 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0 }
		};
		int[][] levelData11 = {
				{ 0, 1, 0, 0, 0, 0 },
				{ 0, 5, 0, 0, 2, 0 },
				{ 0, 1, 3, 0, 7, 5 },
				{ 0, 0, 0, 0, 0, 0 },
				{ 0, 6, 2, 0, 6, 0 },
				{ 3, 0, 0, 0, 0, 7 }
		};
		int[][] levelData12 = {
				{ 0, 0, 0, 2, 3, 6 },
				{ 0, 0, 5, 0, 0, 0 },
				{ 2, 0, 3, 0, 0, 0 },
				{ 0, 0, 0, 1, 0, 7 },
				{ 0, 0, 0, 6, 0, 0 },
				{ 5, 1, 7, 0, 0, 0 }
		};
		int[][] levelData13 = {
				{ 0, 0, 0, 3, 0, 0, 5, 0 },
				{ 0, 1, 0, 0, 6, 0, 0, 0 },
				{ 0, 0, 0, 2, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 1, 0, 0, 0 },
				{ 7, 0, 4, 8, 0, 6, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 3, 0 },
				{ 0, 0, 2, 0, 0, 0, 0, 5 },
				{ 0, 0, 0, 7, 0, 4, 0, 8 }
		};
		int[][] levelData14 = {
				{ 0, 0, 0, 0, 0, 1, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 2, 0 },
				{ 0, 4, 5, 0, 0, 4, 0, 0, 0 },
				{ 0, 0, 0, 6, 0, 0, 0, 2, 0 },
				{ 0, 6, 0, 0, 0, 0, 0, 1, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 5, 0, 0, 0, 3, 0, 3, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }
		};

		int[][][] levelDatas = {
				levelData1,
				levelData2,
				levelData3,
				levelData4,
				levelData5,
				levelData6,
				levelData7,
				levelData8,
				levelData9,
				levelData10,
				levelData11,
				levelData12,
				levelData13,
				levelData14,
		};
		this.setLevel(levelDatas);
	}

	public void setLevel(int[][][] levelDatas) {
		this.levels = new Level[levelDatas.length];
		for (int i = 0; i < levelDatas.length; i++) {
			Level level = new Level(SCREEN_WIDTH, SCREEN_HEIGHT);
			level.setGrid(levelDatas[i]);
			this.levels[i] = level;
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