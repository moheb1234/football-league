package com.example.footballleague.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
public class MatchRequest {

    @NotEmpty
    private String team1Name;

    @NotEmpty
    private String team2Name;

    @Min(0)
    @Max(10)
    private int team1Goals;

    @Min(0)
    @Max(10)
    private int team2Goals;

}
