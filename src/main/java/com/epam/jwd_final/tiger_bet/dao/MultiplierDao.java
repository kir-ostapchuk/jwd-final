package com.epam.jwd_final.tiger_bet.dao;

import com.epam.jwd_final.tiger_bet.domain.Multiplier;
import com.epam.jwd_final.tiger_bet.domain.Result;
import com.epam.jwd_final.tiger_bet.mapper.ModelMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MultiplierDao extends AbstractDao<Multiplier> {

    private static final String SAVE_MULTIPLIER_SQL =
            "insert into `multiplier` (match_id, result_type_id, coefficient) values (?, ?, ?)";

    public Multiplier createMultiplier(int matchId, Result result, BigDecimal coefficient) {
        return new Multiplier(matchId, result, coefficient);
    }

    public boolean saveMultiplier(Multiplier multiplier) {
        List<Object> params = new ArrayList<>();
        params.add(multiplier.getMatchId());
        params.add(multiplier.getResult().getId());
        params.add(multiplier.getCoefficient());
        return queryUpdate(SAVE_MULTIPLIER_SQL, params);
    }

    @Override
    protected ModelMapper<Multiplier> retrieveModelMapper() {
        throw new UnsupportedOperationException();
    }
}