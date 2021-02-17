package com.menezes.santanarankbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Match {
    @Id
    private Long id;
    private String date;
    private String status;
    private String type;
    private String typeName;

    @ManyToOne
    private Team home;

    @ManyToOne
    private Team away;

    private Integer homeGoals;
    private Integer awayGoals;


}