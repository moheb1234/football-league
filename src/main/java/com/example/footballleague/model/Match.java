package com.example.footballleague.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany
    @JsonIgnoreProperties("matches")
    private List<Team> teams;

    @Min(0)
    @Max(10)
    private int team1Gols;

    @Min(0)
    @Max(10)
    private int team2Gols;

    @CreationTimestamp
    private Date matchTime;

    public Match(List<Team> teams, int team1Gols, int team2Gols) {
        this.teams = teams;
        this.team1Gols = team1Gols;
        this.team2Gols = team2Gols;
    }
}
