package grab.szan;

public class Field {
    private int row;
    private int column;
    private Player player;

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Field(int row, int column) {
        this.row = row;
        this.column = column;
    }
}
