package com.epam.jwd_final.web.service;

import com.epam.jwd_final.web.domain.User;
import com.epam.jwd_final.web.domain.UserDto;
import com.epam.jwd_final.web.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<List<UserDto>> findAll() throws ServiceException;

    Optional<UserDto> login(String name, String password) throws ServiceException;

    boolean save(User user) throws ServiceException;

    boolean signup(String name, String password) throws ServiceException;

    void updateRole(String userName) throws ServiceException;

    void rollbackRole(String userName) throws ServiceException;

    int findUserIdByUserName(String userName) throws ServiceException;

    void topUpBalance(String userName, BigDecimal amount) throws ServiceException;

    void withdrawFromBalance(String userName, BigDecimal amount) throws ServiceException;

    BigDecimal findBalanceById(int id) throws ServiceException;

    BigDecimal calculateExpectedWin(String name, int multiplierId) throws ServiceException;

    boolean isUserWinner(String userName, int matchId) throws ServiceException;
}
