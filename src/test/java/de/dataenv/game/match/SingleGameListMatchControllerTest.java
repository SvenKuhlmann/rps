package de.dataenv.game.match;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.dataenv.game.match.rest.MatchListResponse;
import de.dataenv.game.match.rest.MatchRequest;
import de.dataenv.game.match.rest.MatchResponse;
import de.dataenv.game.move.domain.Gesture;
import de.dataenv.game.move.rest.MoveRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SingleGameListMatchControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    private String matchLocation;

    @BeforeEach
    void post_myGame_and_make_move_rock() throws Exception {
        var body = objectMapper.writeValueAsString(new MatchRequest("myGame"));
        var result = mockMvc.perform(post("/api/match").contentType(APPLICATION_JSON).content(body))
                            .andExpect(status().isCreated())
                            .andReturn();
        var move = objectMapper.writeValueAsString(new MoveRequest(Gesture.ROCK));
        this.matchLocation = result.getResponse().getHeader("location");
        mockMvc.perform(post(matchLocation + "/move").contentType(APPLICATION_JSON).content(move))
               .andExpect(status().isCreated())
               .andReturn();
    }


    @Test
    void when_matches_are_requested_response_contains_myGame() throws Exception {
        var result = mockMvc.perform(get("/api/match")).andExpect(status().isOk()).andReturn();
        var matchList = objectMapper.readValue(result.getResponse().getContentAsString(), MatchListResponse.class);
        assertEquals(1, matchList.getMatches().size());
        assertEquals("myGame", matchList.getMatches().get(0).getName());
    }

    @Test
    void when_myGame_is_requested_response_contains_move() throws Exception {
        var result = mockMvc.perform(get(matchLocation)).andExpect(status().isOk()).andReturn();
        var match = objectMapper.readValue(result.getResponse().getContentAsString(), MatchResponse.class);
        assertEquals(1, match.getMoves().size());
        assertEquals(Gesture.ROCK, match.getMoves().get(0).getPlayerGesture());
    }

    @Test
    void when_unknown_match_id_is_requested_response_is_404() throws Exception {
        var result = mockMvc.perform(get("/api/match/42")).andExpect(status().isNotFound()).andReturn();
    }

}
