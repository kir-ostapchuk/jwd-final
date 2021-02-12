package com.epam.jwd_final.tiger_bet.mapper.impl;

import com.epam.jwd_final.tiger_bet.domain.Role;
import com.epam.jwd_final.tiger_bet.domain.User;
import com.epam.jwd_final.tiger_bet.mapper.Mapper;
import com.epam.jwd_final.tiger_bet.service.impl.UserService;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public enum UserMapper implements Mapper<ResultSet, User> {

    INSTANCE;

    @Override
    public User mapFrom(ResultSet rs) throws SQLException {
        final int id = rs.getInt("id");
        final String name = rs.getString("name");
        final String email = rs.getString("email");
        final String password = rs.getString("password");
        final BigDecimal balance = rs.getBigDecimal("balance");
        final int roleId = rs.getInt("role");
        return UserService.INSTANCE.createUser(name, email, password, balance, Role.resolveRoleById(roleId));
    }
}
