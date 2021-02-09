package com.epam.jwd_final.tiger_bet.connection;

import com.epam.jwd_final.tiger_bet.context.ApplicationContext;
import com.epam.jwd_final.tiger_bet.properties.DatabaseProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Timer;
import java.util.TimerTask;

import static com.epam.jwd_final.tiger_bet.connection.ConnectionPool.EXTRA_CONNECTIONS_AMOUNT;
import static com.epam.jwd_final.tiger_bet.connection.ConnectionPool.INITIAL_POOL_SIZE;
import static com.epam.jwd_final.tiger_bet.connection.ConnectionPool.LOAD_FACTOR;
import static com.epam.jwd_final.tiger_bet.connection.ConnectionPool.MAX_POOL_SIZE;
import static com.epam.jwd_final.tiger_bet.connection.ConnectionPool.SHRINK_FACTOR;
import static com.epam.jwd_final.tiger_bet.connection.ConnectionPool.TIME_OUT;

public enum ConnectionPoolManager {

    INSTANCE;

    private static final DatabaseProperties databaseProperties =
            ApplicationContext.getDatabaseProperties();

    void createListener() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ConnectionPool.getInstance().getIsShrinkable().set(ConnectionPoolManager.INSTANCE.isShrinkable());
                if (ConnectionPool.getInstance().getIsShrinkable().get()) {
                    ConnectionPoolManager.INSTANCE.shrinkPool();
                }
            }
        }, TIME_OUT, TIME_OUT);
    }

    Deque<ProxyConnection> createConnections(int extraConnectionsAmount) {
        Deque<ProxyConnection> newConnections = new ArrayDeque<>(extraConnectionsAmount);
        for (int i = 0; i < extraConnectionsAmount; i++) {
            try {
                Connection connection = DriverManager.getConnection(
                        databaseProperties.getUrl(),
                        databaseProperties.getUser(),
                        databaseProperties.getPassword());
                newConnections.add(new ProxyConnection(connection));
            } catch (SQLException e) {
                e.printStackTrace(); // todo: log
            }
        }
        return newConnections;
    }

    boolean isExpandable() {
        return ConnectionPool.getInstance().getAllConnectionsAmount() * LOAD_FACTOR ==
                ConnectionPool.getInstance().getUnavailableConnectionsAmount()
                && ConnectionPool.getInstance().getAllConnectionsAmount() < MAX_POOL_SIZE;
    }

    Deque<ProxyConnection> expandPool() {
        return createConnections(EXTRA_CONNECTIONS_AMOUNT);
        // todo: log
    }

    boolean isShrinkable() {
        return ConnectionPool.getInstance().getAllConnectionsAmount() * SHRINK_FACTOR <=
                ConnectionPool.getInstance().getAvailableConnectionsAmount()
                && INITIAL_POOL_SIZE < ConnectionPool.getInstance().getAllConnectionsAmount();
    }

    void shrinkPool() {
        // todo: log
        int shrinkSize = (int) Math.min(EXTRA_CONNECTIONS_AMOUNT, ConnectionPool.getInstance().getAllConnectionsAmount() * SHRINK_FACTOR);
        for (int i = 0; i < shrinkSize && ConnectionPool.getInstance().getAllConnectionsAmount() > INITIAL_POOL_SIZE; i++) {
            ProxyConnection proxyConnection = ConnectionPool.getInstance().getAvailableConnections().pollFirst();
            if (proxyConnection != null) {
                proxyConnection.closeConnection();
                // todo: log
            }
        }
    }
}