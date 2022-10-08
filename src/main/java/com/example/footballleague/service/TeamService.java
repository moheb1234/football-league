package com.example.footballleague.service;

import com.example.footballleague.model.League;
import com.example.footballleague.model.Team;
import com.example.footballleague.repository.TeamRepository;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.management.InstanceNotFoundException;
import javax.validation.constraints.NotEmpty;

@Service
@Validated
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    public Team create(@NotEmpty String teamName, League league) {
        if (league.findTeamByName(teamName) != null)
            throw new DuplicateRequestException("team: " + teamName + " is duplicate");
        Team team = new Team(teamName, league);
        return teamRepository.save(team);
    }

    public void save(Team team) {
        teamRepository.save(team);
    }

    @SneakyThrows
    public Team findByName(@NotEmpty String teamName, League league) {
       Team team = league.findTeamByName(teamName);
       if (team==null)
            throw new InstanceNotFoundException("no team found with name: " + teamName);
       return team;
    }
}
