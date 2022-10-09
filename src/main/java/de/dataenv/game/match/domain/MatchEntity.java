package de.dataenv.game.match.domain;

import de.dataenv.game.match.rest.MatchRequest;
import de.dataenv.game.move.domain.MoveEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name"}, name = "unique_match_name")
})
public class MatchEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull(message = "name_not_null")
    @Size(min = 4, message = "name_size")
    private String name;

    @OneToMany(mappedBy = "match")
    private List<MoveEntity> moves;


    public MatchEntity() {
    }

    public MatchEntity(MatchRequest match) {
        this.setName(match.getName());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<MoveEntity> getMoves() {
        return moves;
    }

    public void setMoves(List<MoveEntity> moves) {
        this.moves = moves;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
