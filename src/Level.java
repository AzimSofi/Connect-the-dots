import java.awt.Color;
import java.awt.Graphics;

public class Level {
    private int[][] grid;
    private static final Color[] COLORS = {Color.BLUE, Color.ORANGE};

    public Level(int[][] grid) {
        this.grid = grid;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }

    public void paint(Graphics g, int cellSize) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                int cellValue = grid[i][j];
                if (cellValue != 0) {
                    Color dotColor = COLORS[cellValue - 1];
                    paintDot(g, j * cellSize, i * cellSize, cellSize, dotColor);
                }
            }
        }
    }

    private void paintDot(Graphics g, int x, int y, int cellSize, Color color) {
        int dotSize = cellSize * 7 / 10;
    
        int dotX = x + (cellSize - dotSize) / 2;
        int dotY = y + (cellSize - dotSize) / 2;
    
        g.setColor(color);
        
        g.fillOval(dotX, dotY, dotSize, dotSize);
    }
    
}
