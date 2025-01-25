package grab.szan.database.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Represents a move in the database.
 */
@Entity
@Table(name = "moves")
public class MoveEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many moves belong to one game
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private GameEntity game;

    @Column(nullable = false)
    private int playerId;

    @Column(nullable = false)
    private int startRow;

    @Column(nullable = false)
    private int startCol;

    @Column(nullable = false)
    private int endRow;

    @Column(nullable = false)
    private int endCol;

    @Column(nullable = false)
    private LocalDateTime madeAt;

    // Constructors
    public MoveEntity() {}

    public MoveEntity(int playerId, int startRow, int startCol, int endRow, int endCol) {
        this.playerId = playerId;
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
        this.madeAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public GameEntity getGame() {
        return game;
    }

    public void setGame(GameEntity game) {
        this.game = game;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getStartCol() {
        return startCol;
    }

    public void setStartCol(int startCol) {
        this.startCol = startCol;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getEndCol() {
        return endCol;
    }

    public void setEndCol(int endCol) {
        this.endCol = endCol;
    }

    public LocalDateTime getMadeAt() {
        return madeAt;
    }

    public void setMadeAt(LocalDateTime madeAt) {
        this.madeAt = madeAt;
    }
}
