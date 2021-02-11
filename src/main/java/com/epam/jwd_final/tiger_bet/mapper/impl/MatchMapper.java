package com.epam.jwd_final.tiger_bet.mapper.impl;

import com.epam.jwd_final.tiger_bet.domain.Match;
import com.epam.jwd_final.tiger_bet.domain.Sport;
import com.epam.jwd_final.tiger_bet.mapper.Mapper;
import com.epam.jwd_final.tiger_bet.service.impl.MatchService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class MatchMapper implements Mapper<Match> {

    @Override
    public Match mapFrom(ResultSet rs) throws SQLException {
        final int id = rs.getInt("id");
        final Sport sport = Sport.resolveSportById(rs.getInt("sport_type_id"));
        final LocalDateTime start = rs.getTimestamp("start").toLocalDateTime();
        final int firstTeamId = rs.getInt("first_team_id");
        final int secondTeamId = rs.getInt("second_team_id");
        return MatchService.INSTANCE.createEntity(id, sport, start, firstTeamId, secondTeamId);
    }
}