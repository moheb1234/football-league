package com.example.footballleague.service;

import com.example.footballleague.dto.LoginResponse;
import com.example.footballleague.model.User;
import com.example.footballleague.repository.UserRepository;
import com.example.footballleague.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.management.InstanceNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Service
@Validated
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final JwtUtils jwtUtils;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->  new InstanceNotFoundException("no user founded"));
    }

    public LoginResponse signing(@NotEmpty String username , @NotEmpty String password){
        User user = (User) loadUserByUsername(username);
        if(!user.getPassword().equals(password)){
            throw new IllegalArgumentException("password is wrong");
        }
        String token = jwtUtils.generateJwtToken(user);
        return new LoginResponse(token,user.getUsername(),user.getFirstname(),user.getLastname());
    }

    public String signup(@Valid User user){
        checkingUniqueFields(user);
        userRepository.save(user);
        return "signup is successful";
    }

    private void checkingUniqueFields(User user){
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new DuplicateKeyException("this username is already exist");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateKeyException("this email is already exist");
        }
    }

    public String delete(User user){
        userRepository.delete(user);
        return user.getUsername()+" deleted";
    }
}
