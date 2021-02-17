package com.menezes.santanarankbackend.services;

import com.menezes.santanarankbackend.domain.Team;
import com.menezes.santanarankbackend.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService {

    @Autowired
    TeamRepository teamRepository;

    public Team save(Team team) {
        return teamRepository.save(team);
    }
}
