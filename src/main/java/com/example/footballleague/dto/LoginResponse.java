package com.example.footballleague.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class LoginResponse {

    private String token;

    private String username;

    private String firstname;

    private String lastname;
}
