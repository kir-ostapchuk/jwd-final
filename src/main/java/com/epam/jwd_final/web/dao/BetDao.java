package com.epam.jwd_final.web.dao;

import com.epam.jwd_final.web.domain.AbstractEntity;
import com.epam.jwd_final.web.domain.Bet;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.domain.Status;
import com.epam.jwd_final.web.mapper.ModelMapper;
import com.epam.jwd_final.web.mapper.impl.BetModelMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BetDao extends AbstractDao<Bet> {

    private static final String FIND_BY_USER_ID_SQL =
            "select id, user_id, multiplier_id, bet_money from bet where user_id = ?";

    private static final String SAVE_SQL =
            "insert into bet (user_id, multiplier_id, bet_money) values (?, ?, ?)";

    private static final String DELETE_BY_ID_SQL =
            "delete from bet where id = ?";

    private static final String FIND_ONE_BY_USER_ID_BY_MULTIPLIER_ID_SQL =
            "select id, user_id, multiplier_id, bet_money from bet where user_id = ? and multiplier_id = ?";

    private static final String FIND_ONE_BY_ID_SQL =
            "select id, user_id, multiplier_id, bet_money from bet where id = ?";

//    public Optional<List<Bet>> findAllByUserId(int userId) {
//        return querySelect(FIND_BY_USER_ID_SQL, Collections.singletonList(userId));
//    }

    public void save(Bet bet) {
        queryUpdate(
                SAVE_SQL,
                Arrays.asList(bet.getUserId(), bet.getMultiplierId(), bet.getBetMoney())
        );
    }

    public void deleteById(int id) { // TODO: replace id with Bet bet
        queryUpdate(
                DELETE_BY_ID_SQL,
                Collections.singletonList(id)
        );
    }

    public Optional<Bet> findOneById(int id) {
        return querySelectForSingleResult(
                FIND_ONE_BY_ID_SQL,
                Collections.singletonList(id)
        );
    }

    public Optional<Bet> findOneByUserIdByMultiplierId(int userId, int multiplierId) {
        return querySelectForSingleResult(
                FIND_ONE_BY_USER_ID_BY_MULTIPLIER_ID_SQL,
                Arrays.asList(userId, multiplierId)
        );
    }

    public Optional<List<Bet>> findAllByUserId(int userId) {
        return querySelect(
                FIND_BY_USER_ID_SQL,
                Collections.singletonList(userId)
        );
    }

    @Override
    protected ModelMapper<Bet> retrieveModelMapper() {
        return new BetModelMapper();
    }
}
