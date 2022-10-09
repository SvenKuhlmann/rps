package de.dataenv.game.move.unit;

import de.dataenv.game.move.domain.MoveEntity;
import de.dataenv.game.move.domain.Result;
import org.junit.jupiter.api.Test;

import static de.dataenv.game.move.domain.Gesture.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResultTest {

    @Test
    void when_player_is_rock_and_ki_is_rock_its_a_pair() {
        assertEquals(Result.TIE, new MoveEntity(ROCK, ROCK).getResult());
    }
    @Test
    void when_player_is_rock_and_ki_is_paper_player_looses() {
        assertEquals(Result.LOOSE, new MoveEntity(ROCK, PAPER).getResult());
    }
    @Test
    void when_player_is_rock_and_ki_is_scissor_player_wins() {
        assertEquals(Result.WIN, new MoveEntity(ROCK, SCISSOR).getResult());
    }
    @Test
    void when_player_is_scissor_and_ki_is_rock_player_looses() {
        assertEquals(Result.LOOSE, new MoveEntity(SCISSOR, ROCK).getResult());
    }
    @Test
    void when_player_is_paper_and_ki_is_rock_player_wins() {
        assertEquals(Result.WIN, new MoveEntity(PAPER, ROCK).getResult());
    }

}
