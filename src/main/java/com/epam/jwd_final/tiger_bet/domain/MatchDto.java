package com.epam.jwd_final.tiger_bet.domain;

import java.time.LocalDateTime;

public class MatchDto {

    private final Integer id;
    private final Sport sportType;
    private final LocalDateTime start;
    private final String firstTeam;
    private final String secondTeam;
    private final Status status;
    private final Result resultType;

    public MatchDto(Integer id,
                    Sport sportType,
                    LocalDateTime start,
                    String firstTeam,
                    String secondTeam,
                    Status status,
                    Result resultType) {
        this.id = id;
        this.sportType = sportType;
        this.start = start;
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.status = status;
        this.resultType = resultType;
    }

    public Integer getId() {
        return id;
    }

    public Sport getSportType() {
        return sportType;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public String getFirstTeam() {
        return firstTeam;
    }

    public String getSecondTeam() {
        return secondTeam;
    }

    public Status getStatus() {
        return status;
    }

    public Result getResultType() {
        return resultType;
    }
}
