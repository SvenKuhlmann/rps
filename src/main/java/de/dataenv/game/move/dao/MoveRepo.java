package de.dataenv.game.move.dao;

import de.dataenv.game.match.domain.MatchEntity;
import de.dataenv.game.move.domain.MoveEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MoveRepo extends PagingAndSortingRepository<MoveEntity, Integer> {

}
