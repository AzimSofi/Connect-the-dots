import java.awt.Color;
import java.awt.Graphics;

public class Level {
    private int SCREEN_WIDTH;
    private int SCREEN_HEIGHT;
    private int MAX_ROWS;
    private int MAX_COLS;
    private int CELLSIZE;
    private Cell[][] grid;
    private Vector2i OFFSET;
    private Trail trail;
    private static final Color[] COLORS = {
            Color.BLUE, // 1
            Color.MAGENTA, // 2
            Color.RED, // 3
            Color.GREEN, // 4
            Color.YELLOW // 5
    };

    Level(int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        this.SCREEN_WIDTH = SCREEN_WIDTH;
        this.SCREEN_HEIGHT = SCREEN_HEIGHT;
    }

    public void setGrid(int[][] grid) {
        this.MAX_ROWS = grid.length;
        try {
            this.MAX_COLS = grid[0].length;
        } catch (ArrayIndexOutOfBoundsException exception) {
            System.err.println("Grid Structure invalid. Please create a NxN grid!");
        }

        this.grid = new Cell[this.MAX_ROWS][];
        for (int i = 0; i < grid.length; i++) {
            Cell[] cells = new Cell[this.MAX_COLS];
            for (int j = 0; j < grid[i].length; j++) {
                Cell cell = new Cell(grid[i][j]);
                cells[j] = cell;
            }
            this.grid[i] = cells;
        }

        this.CELLSIZE = this.MAX_COLS > this.MAX_ROWS ? this.SCREEN_WIDTH / this.MAX_COLS
                : this.SCREEN_HEIGHT / this.MAX_ROWS;
        this.OFFSET = new Vector2i();
        this.OFFSET.x = (this.SCREEN_WIDTH - this.CELLSIZE * this.MAX_COLS) / 2;
        this.OFFSET.y = (this.SCREEN_HEIGHT - this.CELLSIZE * this.MAX_ROWS) / 2;

        this.trail = new Trail(this.MAX_ROWS * this.MAX_COLS);
    }

    private int getGridColor(Vector2i point) {
        return this.grid[point.y][point.x].color;
    }

    private boolean isNode(Vector2i point) {
        return this.grid[point.y][point.x].node;
    }

    private Vector2i mouseToPoint2i(Vector2i mousePos) {
        Vector2i n = new Vector2i();
        for (int i = 0; i < this.MAX_COLS; i++) {
            int n_col_x = this.OFFSET.x + i * this.CELLSIZE;
            if (mousePos.x >= n_col_x && mousePos.x < n_col_x + this.CELLSIZE) {
                n.x = i;
            }
        }
        for (int i = 0; i < this.MAX_ROWS; i++) {
            int n_col_y = this.OFFSET.y + i * this.CELLSIZE;
            if (mousePos.y >= n_col_y && mousePos.y < n_col_y + this.CELLSIZE) {
                n.y = i;
            }
        }
        return n;
    }

    private boolean isMouseInBound(Vector2i mousePos) {
        int N_PANEL_H = this.MAX_COLS * this.CELLSIZE;
        int N_PANEL_W = this.MAX_ROWS * this.CELLSIZE;

        boolean b_x = mousePos.x >= this.OFFSET.x && mousePos.x < this.OFFSET.x + N_PANEL_H;
        boolean b_y = mousePos.y >= this.OFFSET.y && mousePos.y < this.OFFSET.y + N_PANEL_W;

        return b_x && b_y;
    }

    public void mousePressEvent(Vector2i mousePos) {
        if (this.isMouseInBound(mousePos)) {
            Vector2i point = this.mouseToPoint2i(mousePos);
            if (this.isNode(point)) {
                this.trail.setColor(this.grid[point.y][point.x].color);
                this.trail.addPoint(point);
                // System.out.println("Mouse Pressed = Point Added (" + point.x + ", " + point.y
                // + ")"); // temp (can delete)
            } else {
                // System.out.println("Mouse Pressed = Not a point color"); // temp (can delete)
            }
        } else {
            // System.out.println("Mouse Pressed = Out of bound"); // temp (can delete)
        }
    }

    public void mouseReleasedEvent(Vector2i mousePos) {
        if (this.isMouseInBound(mousePos)) {
            Vector2i point = this.mouseToPoint2i(mousePos);
            if (this.isNode(point) && this.getGridColor(point) == trail.getColor() && trail.getIndex() > 0) {
                // System.out.println("Mouse Released = Points Connected"); // temp

                int[] dirs = trail.getDirs();
                while (trail.getTop() != null) {
                    int currentIndex = trail.getIndex();
                    Vector2i trailPoint = trail.pop();

                    this.grid[trailPoint.y][trailPoint.x].dir = dirs[currentIndex];
                    this.grid[trailPoint.y][trailPoint.x].color = trail.getColor();
                }
            } else {
                // System.out.println("Mouse Released = No Connections"); // temp
            }
        } else {
            // System.out.println("Mouse Released = No Connections"); // temp
        }
        trail.clear(); // reset trail points and color
    }

    public void mouseDragEvent(Vector2i mousePos) {
        if (this.isMouseInBound(mousePos)) {
            Vector2i point = this.mouseToPoint2i(mousePos);

            if (!this.trail.isCollision(point) &&
                    trail.isNextPoint(point) &&
                    (trail.getColor() == this.getGridColor(point) || this.getGridColor(point) == 0) &&
                    !(this.isNode(this.trail.getTop()) && this.trail.getIndex() > 0)) {
                this.trail.addPoint(point);
                // System.out.println("Mouse Drag = Point Added (" + point.x + ", " + point.y +
                // ")"); // temp (can delete)
            } else if (this.trail.isPrevPoint(point)) {
                this.trail.pop();
            }
        }
    }

    public void mouseClickedEvent(Vector2i mousePos) {
        if (this.isMouseInBound(mousePos)) {
            Vector2i point = this.mouseToPoint2i(mousePos);

            int n_color = this.grid[point.y][point.x].color;
            for (int i = 0; i < this.grid.length; i++) {
                for (int j = 0; j < this.grid.length; j++) {
                    if (this.grid[i][j].color == n_color) {
                        this.grid[i][j].dir = 0;
                        if (!this.grid[i][j].node) {
                            this.grid[i][j].color = 0;
                        }
                    }
                }
            }
        }
    }

    public void render(Graphics g) {
        renderBorders(g);
        for (int i = 0; i < this.grid.length; i++) {
            for (int j = 0; j < this.grid[i].length; j++) {
                int n_color = this.grid[i][j].color;

                if (this.grid[i][j].node) {
                    renderDot(g, j, i, this.CELLSIZE * 7 / 10, n_color);
                }
                if (this.grid[i][j].color > 0) {
                    renderLine(g, j, i, grid[i][j].dir, this.CELLSIZE * 5 / 10, n_color);
                }
            }
        }

        Vector2i[] points = this.trail.getPoints();
        int[] dirs = this.trail.getDirs();
        for (int i = 0; i < points.length; i++) {
            if (points[i] != null) {
                renderLine(g, points[i].x, points[i].y, dirs[i], this.CELLSIZE * 5 / 10, this.trail.getColor());
            }
        }
    }

    private void renderDot(Graphics g, int x, int y, int n_diameter, int n_color) {
        Color c_color = COLORS[n_color - 1];

        x = x * this.CELLSIZE + (this.CELLSIZE - n_diameter) / 2 + this.OFFSET.x;
        y = y * this.CELLSIZE + (this.CELLSIZE - n_diameter) / 2 + this.OFFSET.y;

        g.setColor(c_color);
        g.fillOval(x, y, n_diameter, n_diameter);
    }

    private void renderLine(Graphics g, int x, int y, int n_dir, int n_rectThickness, int n_color) {
        Color c_color = COLORS[n_color - 1];
        int n_length = this.CELLSIZE / 2;

        g.setColor(c_color);
        Vector2i n = new Vector2i();
        if (n_dir == 1 || n_dir == 5 || n_dir == 8 || n_dir == 9) {
            n.x = x * this.CELLSIZE + (this.CELLSIZE - n_rectThickness) / 2 + this.OFFSET.x;
            n.y = y * this.CELLSIZE;
            g.fillRect(n.x, n.y, n_rectThickness, n_length);
        }
        if (n_dir == 2 || n_dir == 5 || n_dir == 6 || n_dir == 10) {
            n.x = x * this.CELLSIZE + this.CELLSIZE / 2;
            n.y = y * this.CELLSIZE + (this.CELLSIZE - n_rectThickness) / 2 + this.OFFSET.y;
            g.fillRect(n.x, n.y, n_length, n_rectThickness);
        }
        if (n_dir == 3 || n_dir == 6 || n_dir == 7 || n_dir == 9) {
            n.x = x * this.CELLSIZE + (this.CELLSIZE - n_rectThickness) / 2 + this.OFFSET.x;
            n.y = y * this.CELLSIZE + this.CELLSIZE / 2;
            g.fillRect(n.x, n.y, n_length, n_rectThickness);
        }
        if (n_dir == 4 || n_dir == 7 || n_dir == 8 || n_dir == 10) {
            n.x = x * this.CELLSIZE;
            n.y = y * this.CELLSIZE + (this.CELLSIZE - n_rectThickness) / 2 + this.OFFSET.y;
            g.fillRect(n.x, n.y, n_length, n_rectThickness);
        }

        if (!this.grid[y][x].node) {
            this.renderDot(g, x, y, n_rectThickness, n_color);
        }
    }

    private void renderBorders(Graphics g) {
        g.setColor(Color.WHITE);
        Vector2i n1 = new Vector2i();
        Vector2i n2 = new Vector2i();
        for (int i = 0; i <= this.MAX_COLS; i++) {
            n1.x = this.OFFSET.x + i * this.CELLSIZE;
            n1.y = this.OFFSET.y;
            n2.x = n1.x;
            n2.y = this.OFFSET.y + this.MAX_ROWS * this.CELLSIZE;
            g.drawLine(n1.x, n1.y, n2.x, n2.y);
            n1.x = n1.x + this.CELLSIZE - 1;
            n2.x = n1.x;
            g.drawLine(n1.x, n1.y, n2.x, n2.y);
        }
        for (int i = 0; i <= this.MAX_ROWS; i++) {
            n1.x = this.OFFSET.x;
            n1.y = this.OFFSET.y + i * this.CELLSIZE;
            n2.x = this.OFFSET.x + this.MAX_COLS * this.CELLSIZE;
            n2.y = n1.y;
            g.drawLine(n1.x, n1.y, n2.x, n2.y);
            n1.y = n1.y + this.CELLSIZE - 1;
            n2.y = n1.y;
            g.drawLine(n1.x, n1.y, n2.x, n2.y);
        }
    }
}
