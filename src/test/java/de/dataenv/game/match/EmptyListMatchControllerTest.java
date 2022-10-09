package de.dataenv.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.dataenv.game.match.rest.BadRequestResponse;
import de.dataenv.game.match.rest.MatchListResponse;
import de.dataenv.game.match.rest.MatchRequest;
import de.dataenv.game.match.rest.MatchResponse;
import de.dataenv.game.move.domain.Gesture;
import de.dataenv.game.move.rest.MoveRequest;
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
public class EmptyListMatchControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void when_matchs_are_requested_response_contains_list_of_matchs() throws Exception {
        var result = mockMvc.perform(get("/api/match")).andExpect(status().isOk()).andReturn();
        var matchList = objectMapper.readValue(result.getResponse().getContentAsString(), MatchListResponse.class);
        assertEquals(0, matchList.getMatches().size());
    }

    @Test
    void when_match_is_posted_location_header_is_set() throws Exception {
        var body = objectMapper.writeValueAsString(new MatchRequest("myGame"));
        var result = mockMvc.perform(post("/api/match").contentType(APPLICATION_JSON).content(body))
                            .andExpect(status().isCreated())
                            .andReturn();
        var locationHeader = result.getResponse().getHeader("location");
        assertThat(locationHeader, matchesPattern("http://localhost/api/match/[0-9]+"));
    }

    @Test
    void when_match_is_posted_match_can_be_requested() throws Exception {
        var body = objectMapper.writeValueAsString(new MatchRequest("myGame"));
        var result = mockMvc.perform(post("/api/match").contentType(APPLICATION_JSON).content(body))
                            .andExpect(status().isCreated())
                            .andReturn();
        var locationHeader = result.getResponse().getHeader("location");
        result = mockMvc.perform(get(locationHeader)).andExpect(status().isOk()).andReturn();
        var match = objectMapper.readValue(result.getResponse().getContentAsString(), MatchResponse.class);
        assertNotNull(match.getId());
        assertEquals("myGame", match.getName());
    }

    @Test
    void when_two_matches_with_same_name_are_posted_response_is_error() throws Exception {
        var body = objectMapper.writeValueAsString(new MatchRequest("myGame"));
        var result = mockMvc.perform(post("/api/match").contentType(APPLICATION_JSON).content(body))
                            .andExpect(status().isCreated())
                            .andReturn();
        result = mockMvc.perform(post("/api/match").contentType(APPLICATION_JSON).content(body))
                        .andExpect(status().isBadRequest())
                        .andReturn();
        var response = objectMapper.readValue(result.getResponse().getContentAsString(), BadRequestResponse.class);
        assertEquals("Match names have to be unique. Please choose an other name.", response.getMessage());
    }
    @Test
    void when_game_name_is_to_short_is_created_response_is_error() throws Exception {
        var body = objectMapper.writeValueAsString(new MatchRequest("g"));
        var result = mockMvc.perform(post("/api/match").contentType(APPLICATION_JSON).content(body))
                            .andExpect(status().isBadRequest())
                            .andReturn();
        var response = objectMapper.readValue(result.getResponse().getContentAsString(), BadRequestResponse.class);
        assertEquals("The name of a match must be at least 4 characters long.", response.getMessage());
    }
    @Test
    void when_game_name_is_empty_response_is_error() throws Exception {
        var body = objectMapper.writeValueAsString(new MatchRequest());
        var result = mockMvc.perform(post("/api/match").contentType(APPLICATION_JSON).content(body))
                            .andExpect(status().isBadRequest())
                            .andReturn();
        var response = objectMapper.readValue(result.getResponse().getContentAsString(), BadRequestResponse.class);
        assertEquals("Match name cannot be empty.", response.getMessage());
    }
}
