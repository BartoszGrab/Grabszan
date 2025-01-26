package grab.szan.database.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a game in the database.
 */
@Entity
@Table(name = "games")
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String roomName;

    @Column(nullable = false)
    private int maxPlayers;

    @Column(nullable = false)
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MoveEntity> moves = new ArrayList<>();

    // Constructors
    public GameEntity() {}

    public GameEntity(String roomName, int maxPlayers, LocalDateTime startTime) {
        this.roomName = roomName;
        this.maxPlayers = maxPlayers;
        this.startTime = startTime;
    }

    public Long getId() {
        return id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public List<MoveEntity> getMoves() {
        return moves;
    }

    public void setMoves(List<MoveEntity> moves) {
        this.moves = moves;
    }

    // Helper Methods
    public void addMove(MoveEntity move) {
        moves.add(move);
        move.setGame(this);
    }
}
