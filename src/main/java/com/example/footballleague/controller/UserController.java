package com.example.footballleague.controller;

import com.example.footballleague.dto.LoginResponse;
import com.example.footballleague.model.User;
import com.example.footballleague.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public String signup(@RequestBody User user) {
        return userService.signup(user);
    }

    @PostMapping("/signing")
    public LoginResponse signing(@RequestParam String username, @RequestParam String password) {
        return userService.signing(username, password);
    }

    @DeleteMapping("/user/delete")
    public String delete(@AuthenticationPrincipal User user){
        return userService.delete(user);
    }
}
