package grab.szan;

/**
 * Represents a single field on the game board.
 */
public class Field {
    /**
     * The row index of this field on the board.
     */
    private int row;

    /**
     * The column index of this field on the board.
     */
    private int column;

    /**
     * The player who occupies this field (if any).
     */
    private Player player;

    /**
     * Constructs a new Field at the specified row and column.
     *
     * @param row    the row index of the field
     * @param column the column index of the field
     */
    public Field(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Gets the row index of this field.
     *
     * @return the row index
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the column index of this field.
     *
     * @return the column index
     */
    public int getColumn() {
        return column;
    }

    /**
     * Gets the player occupying this field.
     *
     * @return the player, or null if the field is empty
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Assigns a player to this field.
     *
     * @param player the player to set
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
}
