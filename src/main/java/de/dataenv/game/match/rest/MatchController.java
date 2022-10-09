package de.dataenv.game.match.rest;

import de.dataenv.game.match.domain.MatchEntity;
import de.dataenv.game.match.domain.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/match")
public class MatchController {

    @Autowired
    MatchService matchService;

    @GetMapping("")
    public MatchListResponse getMatches() {
        return new MatchListResponse(matchService.findAll());
    }

    @PostMapping("")
    public ResponseEntity<Object> addMatch(@RequestBody MatchRequest match) {
        var entity = matchService.save(new MatchEntity(match));
        var link = linkTo(methodOn(MatchController.class).getMatch(entity.getId())).toUri();
        return ResponseEntity.created(link).build();
    }

    @GetMapping("/{id}")
    public MatchResponse getMatch(@PathVariable Integer id) {
        var entity = matchService.findById(id);
        return new MatchResponse(entity);
    }

}
