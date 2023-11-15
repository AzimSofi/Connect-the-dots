public class Trail {
    private Vector2i[] points;
    private int[] dirs; // directions
    private int currentIndex;
    private int currentColor; // index value

    Trail(int size) {
        this.points = new Vector2i[size];
        this.dirs = new int[size];
        this.clear();
    }

    public void setColor(int colorIndex) {
        this.currentColor = colorIndex;
    }

    public int getColor() {
        return this.currentColor;
    }

    public int getIndex() {
        return this.currentIndex;
    }

    public void addPoint(Vector2i point) {
        this.currentIndex += 1;
        this.points[this.currentIndex] = point;
    }

    public Vector2i getTop() { // return null if trail is empty, else return last trail point
        if (this.currentIndex >= 0) {
            return points[this.currentIndex];
        } else {
            return null;
        }
    }

    public Vector2i[] getPoints() {
        return this.points;
    }

    public int[] getDirs() {
        if (this.currentIndex > 0) {
            for (int i = 1; i < this.points.length; i++) {
                if (points[i] == null)
                    break;
                int n_x = this.points[i].x - this.points[i - 1].x;
                int n_y = this.points[i].y - this.points[i - 1].y;

                int n_dir1 = 0;
                int n_dir2 = 0;

                if (n_y == 1) {
                    n_dir1 = 3;
                    n_dir2 = 1;
                } else if (n_x == 1) {
                    n_dir1 = 2;
                    n_dir2 = 4;
                } else if (n_y == -1) {
                    n_dir1 = 1;
                    n_dir2 = 3;
                } else if (n_x == -1) {
                    n_dir1 = 4;
                    n_dir2 = 2;
                }

                int n_dirCase = this.dirs[i - 1] * 10 + n_dir1;
                if (n_dirCase == 12 || n_dirCase == 21) {
                    n_dir1 = 5;
                } else if (n_dirCase == 23 || n_dirCase == 32) {
                    n_dir1 = 6;
                } else if (n_dirCase == 34 || n_dirCase == 43) {
                    n_dir1 = 7;
                } else if (n_dirCase == 14 || n_dirCase == 41) {
                    n_dir1 = 8;
                } else if (n_dirCase == 13 || n_dirCase == 31) {
                    n_dir1 = 9;
                } else if (n_dirCase == 24 || n_dirCase == 42) {
                    n_dir1 = 10;
                }
                this.dirs[i - 1] = n_dir1;
                this.dirs[i] = n_dir2;
            }
        } else {
            dirs[0] = 0; // solves EDGE case (non-error)
        }

        return this.dirs;
    }

    public Vector2i pop() { // removes last trail point
        Vector2i point = this.getTop();
        if (point != null) {
            this.points[this.currentIndex] = null;
            this.currentIndex -= 1;
        }
        return point;
    }

    public void clear() { // clears points, dirs, color and index
        for (int i = 0; i < this.points.length; i++) {
            if (this.points[i] == null)
                break;
            this.points[i] = null;
        }
        for (int i = 0; i < this.dirs.length; i++) {
            this.dirs[i] = 0;
        }
        this.setColor(0);
        this.currentIndex = -1;
    }

    public boolean isCollision(Vector2i point) { // check if cell has existing trail
        for (Vector2i trailpoint : this.points) {
            if (trailpoint == null)
                break;
            if (trailpoint.x == point.x && trailpoint.y == point.y) {
                return true;
            }
        }
        return false;
    }

    public boolean isPrevPoint(Vector2i point) { // check if previous trail contains point
        if (this.currentIndex >= 1) { // must have atleast 2 trail points
            int n_prev_x = this.points[currentIndex - 1].x;
            int n_prev_y = this.points[currentIndex - 1].y;
            if (n_prev_x == point.x && n_prev_y == point.y) {
                return true;
            }
        }
        return false;
    }

    public boolean isNextPoint(Vector2i point) { // check if next cell is a possible trail point
        if (this.getTop() != null) { // is trail not empty
            Vector2i top = this.getTop();
            if (point.y == top.y && (point.x == top.x + 1 || point.x == top.x - 1)) { // same y, 1 x offset
                return true;
            } else if (point.x == top.x && (point.y == top.y + 1 || point.y == top.y - 1)) { // same x, 1 y offset
                return true;
            }
        }
        return false;
    }
}
