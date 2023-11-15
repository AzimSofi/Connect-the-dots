public class Cell {
    public int color;
    public int dir; // directions
    public boolean node; // source, destination or neither

    Cell(int color) {
        this.color = color;
        this.dir = 0;
        this.node = color != 0 ? true : false;
    }
}
