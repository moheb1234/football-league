package com.example.footballleague.repository;

import com.example.footballleague.model.League;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotEmpty;
import java.util.Optional;

public interface LeagueRepository extends JpaRepository<League,Long> {
    Optional<League> findByNameAndUserId(String name, long userId);
}
