package de.dataenv.game.move.domain;

import de.dataenv.game.match.domain.MatchService;
import de.dataenv.game.move.dao.MoveRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MoveService {
    @Autowired
    MoveRepo moveRepo;
    @Autowired
    MatchService matchService;
    @Autowired
    RandomArtificialIntelligence easyArtificialIntelligence;

    public MoveEntity save(Integer matchId, Gesture playerGesture, Gesture kiGesture){
        var entity = new MoveEntity();
        entity.setPlayerGesture(playerGesture);
        entity.setKiGesture(kiGesture);
        entity.setMatch(matchService.findById(matchId));
        return moveRepo.save(entity);
    }

    public MoveEntity makeMove(Integer matchId, Gesture gesture) {
        return save(matchId, gesture, easyArtificialIntelligence.makeMove());
    }

    public Optional<MoveEntity> findById(Integer moveId){
        return moveRepo.findById(moveId);
    }
}
