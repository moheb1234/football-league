package com.example.footballleague.service;

import com.example.footballleague.dto.MatchRequest;
import com.example.footballleague.model.League;
import com.example.footballleague.model.Match;
import com.example.footballleague.model.Team;
import com.example.footballleague.model.User;
import com.example.footballleague.repository.MatchRepository;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@Service
@Slf4j
@Validated
@RequiredArgsConstructor
public class MatchService {
    private final MatchRepository matchRepository;
    private final TeamService teamService;

    public Match customMatch(@Valid MatchRequest matchRequest, @NotEmpty String leagueName, User user) {
        League league = user.findLeagueByName(leagueName);
        if (league == null)
            throw new DuplicateRequestException("league: " + leagueName + " is duplicate");
        if (Objects.equals(matchRequest.getTeam1Name(), matchRequest.getTeam2Name()))
            throw new IllegalArgumentException("team names should be difference");
        Team team1 = teamService.findByName(matchRequest.getTeam1Name(), league);
        Team team2 = teamService.findByName(matchRequest.getTeam2Name(), league);
        List<Team> teams = new ArrayList<>();
        teams.add(team1);
        teams.add(team2);
        Match match = new Match(teams, matchRequest.getTeam1Goals(), matchRequest.getTeam2Goals());
        matchRepository.save(match);
        updateTeamsAfterMatch(match);
        return match;
    }

    public Match randomMatch(@NotEmpty String team1name, @NotEmpty String team2name, @NotEmpty String leagueName, User user) {
        if (Objects.equals(team1name, team2name))
            throw new IllegalArgumentException("team names should be difference");
        League league = user.findLeagueByName(leagueName);
        if (league == null)
            throw new DuplicateRequestException("league: " + leagueName + " is duplicate");
        Team team1 = teamService.findByName(team1name, league);
        Team team2 = teamService.findByName(team2name, league);
        int team1goals = (int) ((Math.random() * 10) + 0);
        int team2goals = (int) ((Math.random() * 10) + 0);
        List<Team> teams = new ArrayList<>();
        teams.add(team1);
        teams.add(team2);
        Match match = new Match(teams, team1goals, team2goals);
        matchRepository.save(match);
        updateTeamsAfterMatch(match);
        return match;
    }

    private void updateTeamsAfterMatch(Match match) {
        List<Team> teams = match.getTeams();
        Team team1 = teams.get(0);
        Team team2 = teams.get(1);
        int team1goals = match.getTeam1Gols();
        int team2goals = match.getTeam2Gols();
        int deference = Math.abs(team1goals - team2goals);
        if (team1goals > team2goals) {
            team1.setPoint(team1.getPoint() + 3);
            team1.setGoalAverage(team1.getGoalAverage() + deference);
            team2.setGoalAverage(team2.getGoalAverage() - deference);
        } else if (team1goals < team2goals) {
            team2.setPoint(team2.getPoint() + 3);
            team2.setGoalAverage(team2.getGoalAverage() + deference);
            team1.setGoalAverage(team1.getGoalAverage() - deference);
        } else {
            team1.setPoint(team1.getPoint() + 1);
            team2.setPoint(team2.getPoint() + 1);
        }
        team1.getMatches().add(match);
        team2.getMatches().add(match);
        teamService.save(team1);
        teamService.save(team2);
    }
}
