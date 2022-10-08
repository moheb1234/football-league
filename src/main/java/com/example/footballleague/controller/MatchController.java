package com.example.footballleague.controller;

import com.example.footballleague.dto.MatchRequest;
import com.example.footballleague.model.Match;
import com.example.footballleague.model.User;
import com.example.footballleague.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @PostMapping("/match/create-custom")
    public Match customMatch(@RequestBody MatchRequest matchRequest, @RequestParam String leagueName,
                             @AuthenticationPrincipal User user) {
        return matchService.customMatch(matchRequest, leagueName, user);
    }

    @PostMapping("/match/create-random")
    public Match customMatch(@RequestParam String team1Name,@RequestParam String team2Name
            , @RequestParam String leagueName, @AuthenticationPrincipal User user) {
        return matchService.randomMatch(team1Name, team2Name, leagueName, user);
    }
}
