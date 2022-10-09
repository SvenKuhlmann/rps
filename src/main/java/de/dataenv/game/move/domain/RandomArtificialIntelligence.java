package de.dataenv.game.move.domain;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomArtificialIntelligence {

    public Gesture makeMove() {
        return Gesture.values()[new Random().nextInt(Gesture.values().length)];
    }
}
