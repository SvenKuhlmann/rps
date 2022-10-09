package de.dataenv.game.match.domain;

import de.dataenv.game.match.dao.MatchRepo;
import de.dataenv.game.match.rest.MatchNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchService {

    @Autowired
    MatchRepo matchRepo;

    public MatchEntity save(MatchEntity match){
        return matchRepo.save(match);
    }

    public MatchEntity findById(Integer id) {
        return matchRepo.findById(id).orElseThrow(() -> new MatchNotFoundException());
    }

    public List<MatchEntity> findAll() {
        return matchRepo.findAll();
    }
}
