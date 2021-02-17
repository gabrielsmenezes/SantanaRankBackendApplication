package com.menezes.santanarankbackend.services;

import com.menezes.santanarankbackend.domain.Match;
import com.menezes.santanarankbackend.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchService {

    @Autowired
    MatchRepository matchRepository;

    public Match save (Match match) {
        return matchRepository.save(match);
    }
}
