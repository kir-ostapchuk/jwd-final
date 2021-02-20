package com.epam.jwd_final.web.dao;

import com.epam.jwd_final.web.domain.AbstractEntity;
import com.epam.jwd_final.web.domain.Bet;
import com.epam.jwd_final.web.mapper.ModelMapper;
import com.epam.jwd_final.web.mapper.impl.BetModelMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BetDao extends AbstractDao<Bet> {

    private static final String FIND_BY_USER_ID_SQL =
            "select id, user_id, multiplier_id, bet_money from bet where user_id = ?";

    private static final String SAVE_BET = "insert into bet (user_id, multiplier_id, bet_money) values (?, ?, ?)";

    private static final String DELETE_BET_BY_ID_SQL = "delete from bet where id = ?";

    private static final String FIND_BET_BY_USER_ID_SQL = "select id, user_id, multiplier_id, bet_money from bet where user_id = ?";

    private final UserDao userDao;
    private final MultiplierDao multiplierDao;

    public BetDao(UserDao userDao, MultiplierDao multiplierDao) {
        this.userDao = userDao;
        this.multiplierDao = multiplierDao;
    }

    public Optional<List<Bet>> findAllBetsByUserName(String name) {
        final Optional<Integer> userId = userDao.findByName(name).map(AbstractEntity::getId);
        if (!userId.isPresent()) {
            return Optional.empty();
        }
        return querySelect(FIND_BY_USER_ID_SQL, Collections.singletonList(userId.get()));
    }

    public Bet createBet(int userId, int multiplierId, BigDecimal betMoney) {
        return new Bet(userId, multiplierId, betMoney);
    }

    public boolean saveBet(Bet bet) {
        List<Object> params = new ArrayList<>();
        params.add(bet.getUserId());
        params.add(bet.getMultiplierId());
        params.add(bet.getBetMoney());
        return queryUpdate(SAVE_BET, params);
    }

    public void deleteBet(int id) {
        queryUpdate(DELETE_BET_BY_ID_SQL, Collections.singletonList(id));
    }

    public BigDecimal calculateExpectedWin(String name) {
        final int userId = userDao.findUserIdByUserName(name);
        final Bet bet = querySelectForSingleResult(FIND_BET_BY_USER_ID_SQL, Collections.singletonList(userId)).orElseThrow(IllegalArgumentException::new);
        final BigDecimal betMoney = bet.getBetMoney();
        return multiplierDao.findCoefficientById(bet.getMultiplierId()).multiply(betMoney);
    }

    @Override
    protected ModelMapper<Bet> retrieveModelMapper() {
        return new BetModelMapper();
    }
}