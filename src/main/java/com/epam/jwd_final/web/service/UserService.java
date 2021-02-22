package com.epam.jwd_final.web.service;

import com.epam.jwd_final.web.domain.User;
import com.epam.jwd_final.web.domain.UserDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<List<UserDto>> findAll();

    Optional<UserDto> login(String name, String password);

    boolean save(User user);

    boolean signup(String name, String password);

    void updateRole(String userName);

    void rollbackRole(String userName);

    int findUserIdByUserName(String userName);

    void topUpBalance(String userName, BigDecimal amount);

    void withdrawFromBalance(String userName, BigDecimal amount);

    BigDecimal findBalanceById(int id);

    BigDecimal calculateExpectedWin(String name, int multiplierId);

    boolean isUserWinner(String userName, int matchId);
}
