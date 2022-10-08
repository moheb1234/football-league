package com.example.footballleague.controller;

import com.example.footballleague.model.League;
import com.example.footballleague.model.Team;
import com.example.footballleague.model.User;
import com.example.footballleague.service.LeagueService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LeagueController {
    private final LeagueService leagueService;

    @PostMapping("/league/create")
    public League create(@AuthenticationPrincipal User user , @RequestParam String leagueName){
        return leagueService.create(leagueName, user);
    }

    @PostMapping("/league/add-team")
    public Team addTeam(@RequestParam String teamName ,@RequestParam String leagueName,@AuthenticationPrincipal User user) {
        return leagueService.addTeam(teamName, leagueName, user);
    }
}
