package com.menezes.santanarankbackend.resorces;

import com.menezes.santanarankbackend.domain.Match;
import com.menezes.santanarankbackend.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/matches")
public class MatchResource {

    @Autowired
    MatchService matchService;

    @GetMapping
    public List<Match> getAll(){
        return matchService.getAll();
    }

}
