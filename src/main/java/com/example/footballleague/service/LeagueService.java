package com.example.footballleague.service;

import com.example.footballleague.model.League;
import com.example.footballleague.model.Team;
import com.example.footballleague.model.User;
import com.example.footballleague.repository.LeagueRepository;
import com.example.footballleague.repository.UserRepository;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.management.InstanceNotFoundException;
import javax.validation.constraints.NotEmpty;

@Service
@Slf4j
@Validated
@RequiredArgsConstructor
public class LeagueService {
    private final LeagueRepository leagueRepository;
    private final TeamService teamService;

    private final UserRepository userRepository;

    @SneakyThrows
    public League findByName(@NotEmpty String leagueName, User user) {
        League league = user.findLeagueByName(leagueName);
        if (league == null)
            throw new InstanceNotFoundException("no league found with name: " + leagueName);
        return league;
    }

    public League create(@NotEmpty String leagueName, User user) {
        if (user.findLeagueByName(leagueName)!=null)
            throw new DuplicateRequestException("league: "+leagueName+" is duplicate");
        League league = new League(leagueName,user);
         leagueRepository.save(league);
         user.getLeagues().add(league);
         userRepository.save(user);
         return league;
    }


    public Team addTeam(String teamName , String leagueName, User user){
        League league = findByName(leagueName, user);
        Team team = teamService.create(teamName,league);
        league.getTeams().add(team);
        leagueRepository.save(league);
        return team;
    }
}
