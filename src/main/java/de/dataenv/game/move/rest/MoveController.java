package de.dataenv.game.move.rest;

import de.dataenv.game.move.domain.MoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/match/{matchId}/move")
public class MoveController {

    @Autowired
    MoveService moveService;

    @GetMapping("/{moveId}")
    public MoveResponse get(@PathVariable Integer matchId, @PathVariable Integer moveId) {
        return new MoveResponse(moveService.findById(moveId).orElseThrow());
    }

    @PostMapping
    public ResponseEntity<MoveResponse> post(@PathVariable Integer matchId, @RequestBody MoveRequest moveRequest) {
        var move = moveService.makeMove(matchId, moveRequest.getGesture());
        var link = linkTo(methodOn(MoveController.class).get(matchId, move.getId())).toUri();
        return ResponseEntity.created(link).body(new MoveResponse(move));
    }

}
