package grab.szan.spring.services;

import grab.szan.database.entities.GameEntity;
import grab.szan.database.entities.MoveEntity;
import grab.szan.database.repositories.GameRepository;
import grab.szan.database.repositories.MoveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing moves.
 */
@Service
public class MoveService {

    private final MoveRepository moveRepository;
    private final GameRepository gameRepository;

    @Autowired
    public MoveService(MoveRepository moveRepository, GameRepository gameRepository) {
        this.moveRepository = moveRepository;
        this.gameRepository = gameRepository;
    }

    /**
     * Records a move in the database.
     *
     * @param roomName the name of the game room
     * @param playerId the ID of the player making the move
     * @param startRow the starting row of the move
     * @param startCol the starting column of the move
     * @param endRow   the ending row of the move
     * @param endCol   the ending column of the move
     */
    public void recordMove(String roomName,
                           int playerId,
                           int startRow,
                           int startCol,
                           int endRow,
                           int endCol) {

        // Find the game entity by room name
        GameEntity gameEntity = gameRepository.findByRoomName(roomName);
        if (gameEntity == null) {
            throw new IllegalArgumentException("Game not found in DB with roomName = " + roomName);
        }

        // Create a new move entity
        MoveEntity move = new MoveEntity(playerId, startRow, startCol, endRow, endCol);
        move.setGame(gameEntity);

        // Save the move to the database
        moveRepository.save(move);
    }
}
