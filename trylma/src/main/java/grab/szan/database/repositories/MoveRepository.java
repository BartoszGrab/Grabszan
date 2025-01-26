package grab.szan.database.repositories;

import grab.szan.database.entities.MoveEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for MoveEntity.
 */
@Repository
public interface MoveRepository extends JpaRepository<MoveEntity, Long> {
    /**
     * Finds all moves associated with a specific game ID.
     *
     * @param gameId the ID of the game
     * @return a list of MoveEntity objects
     */
    List<MoveEntity> findByGame_Id(Long gameId);
}
