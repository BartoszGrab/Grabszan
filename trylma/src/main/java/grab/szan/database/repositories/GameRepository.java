package grab.szan.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import grab.szan.database.entities.GameEntity;

/**
 * Repository interface for GameEntity.
 */
@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long> {
    /**
     * Finds a game by its room name.
     *
     * @param roomName the name of the game room
     * @return the GameEntity if found, otherwise null
     */
    GameEntity findByRoomName(String roomName);
}
