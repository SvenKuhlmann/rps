package de.dataenv.game.match.dao;

import de.dataenv.game.match.domain.MatchEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepo extends CrudRepository<MatchEntity, Integer> {

    List<MatchEntity> findAll();
}
