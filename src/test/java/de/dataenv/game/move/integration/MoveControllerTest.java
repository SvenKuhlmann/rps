package de.dataenv.game.move.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import de.dataenv.game.match.rest.MatchRequest;
import de.dataenv.game.match.rest.MatchResponse;
import de.dataenv.game.move.domain.RandomArtificialIntelligence;
import de.dataenv.game.move.domain.Result;
import de.dataenv.game.move.rest.MoveRequest;
import de.dataenv.game.move.rest.MoveResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static de.dataenv.game.move.domain.Gesture.ROCK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MoveControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    RandomArtificialIntelligence mockAi;
    private String matchLocation;

    @BeforeEach
    void setUpMockAi() {
        when(mockAi.makeMove()).thenReturn(ROCK);
    }

    @BeforeEach
    void post_myGame() throws Exception {
        var myGame = objectMapper.writeValueAsString(new MatchRequest("myGame"));
        var result = mockMvc.perform(post("/api/match").contentType(APPLICATION_JSON).content(myGame))
                            .andExpect(status().isCreated())
                            .andReturn();
        this.matchLocation = result.getResponse().getHeader("location");
    }

    @Test
    void when_move_is_added_to_match_result_is_computed() throws Exception {
        var content = objectMapper.writeValueAsString(new MoveRequest(ROCK));
        mockMvc.perform(post(matchLocation + "/move").contentType(APPLICATION_JSON).content(content))
               .andExpect(status().isCreated())
               .andReturn();
        var result = mockMvc.perform(get(matchLocation)).andReturn();
        var match = objectMapper.readValue(result.getResponse().getContentAsString(), MatchResponse.class);
        assertEquals(ROCK, match.getMoves().get(0).getPlayerGesture());
        assertEquals(ROCK, match.getMoves().get(0).getKiGesture());
        assertEquals(Result.TIE, match.getMoves().get(0).getResult());
    }

    @Test
    void when_move_is_added_to_match_location_header_can_be_used_to_retrieve_move() throws Exception {
        var content = objectMapper.writeValueAsString(new MoveRequest(ROCK));
        var result = mockMvc.perform(post(matchLocation + "/move").contentType(APPLICATION_JSON).content(content))
                        .andExpect(status().isCreated())
                        .andReturn();
        var moveLocation = result.getResponse().getHeader("location");
        result = mockMvc.perform(get(moveLocation)).andReturn();
        var move = objectMapper.readValue(result.getResponse().getContentAsString(), MoveResponse.class);
        assertEquals(ROCK, move.getPlayerGesture());
    }

    @Test
    void when_rock_move_is_added_response_contains_result() throws Exception {
        var content = objectMapper.writeValueAsString(new MoveRequest(ROCK));
        var result = mockMvc.perform(post(this.matchLocation + "/move").contentType(APPLICATION_JSON).content(content))
                        .andExpect(status().isCreated())
                        .andReturn();
        var response = objectMapper.readValue(result.getResponse().getContentAsString(), MoveResponse.class);
        assertEquals(Result.TIE, response.getResult());
    }
}
