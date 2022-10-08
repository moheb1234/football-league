package com.example.footballleague.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    private String name;

    @Min(0)
    private int point;

    private int goalAverage;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date lastModifiedAt;

    @ManyToMany(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    private Set<Match> matches = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("teams")
    private League league;

    public Team(String name, League league) {
        this.name = name;
        this.league = league;
        point = 0;
        goalAverage = 0;
    }
}
