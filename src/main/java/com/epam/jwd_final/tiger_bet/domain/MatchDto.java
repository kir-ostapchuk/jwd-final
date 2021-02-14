package com.epam.jwd_final.tiger_bet.domain;

import java.time.LocalDateTime;

public class MatchDto {

    private final Sport sportType;
    private final LocalDateTime start;
    private final String firstTeam;
    private final String secondTeam;
    private final Status status;

    public MatchDto(Sport sportType, LocalDateTime start, String firstTeam, String secondTeam, Status status) {
        this.sportType = sportType;
        this.start = start;
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.status = status;
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
}