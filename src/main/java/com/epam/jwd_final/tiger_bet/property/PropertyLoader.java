package com.epam.jwd_final.tiger_bet.property;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertyLoader {

    private static final Logger LOGGER = LogManager.getLogger(PropertyLoader.class);

    private static final Properties properties = new Properties();

    private static final String DATABASE_PROPERTY_FILE_NAME = "database.properties";
    private static final String CONNECTION_POOL_PROPERTY_FILE_NAME = "connection_pool.properties";

    private static final String DB_URL_PROPERTY = "url";
    private static final String DB_USER_PROPERTY = "user";
    private static final String DB_PASSWORD_PROPERTY = "password";

    private static final String POOL_MAX_SIZE_PROPERTY = "maxPoolSize";
    private static final String POOL_INITIAL_SIZE_PROPERTY = "initialPoolSize";
    private static final String POOL_EXTRA_CONNECTIONS_AMOUNT_PROPERTY = "extraConnectionAmount";
    private static final String POOL_LOAD_FACTOR_PROPERTY = "loadFactor";
    private static final String POOL_SHRINK_FACTOR_PROPERTY = "shrinkFactor";
    private static final String POOL_CONNECTION_TIME_OUT_PROPERTY = "connectionTimeOut";

    private PropertyLoader() {
    }

    public DatabaseProperty loadDatabaseProperties() {
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(DATABASE_PROPERTY_FILE_NAME)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read database property file");
        }
        return new DatabaseProperty(
                properties.getProperty(DB_URL_PROPERTY),
                properties.getProperty(DB_USER_PROPERTY),
                properties.getProperty(DB_PASSWORD_PROPERTY)
        );
    }

    public ConnectionPoolProperty loadConnectionPoolProperties() {
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(CONNECTION_POOL_PROPERTY_FILE_NAME)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read connection pool property file");
        }
        return new ConnectionPoolProperty(
                Integer.parseInt(properties.getProperty(POOL_MAX_SIZE_PROPERTY)),
                Integer.parseInt(properties.getProperty(POOL_INITIAL_SIZE_PROPERTY)),
                Integer.parseInt(properties.getProperty(POOL_EXTRA_CONNECTIONS_AMOUNT_PROPERTY)),
                Double.parseDouble(properties.getProperty(POOL_LOAD_FACTOR_PROPERTY)),
                Double.parseDouble(properties.getProperty(POOL_SHRINK_FACTOR_PROPERTY)),
                Integer.parseInt(properties.getProperty(POOL_CONNECTION_TIME_OUT_PROPERTY))
        );
    }

    private static class PropertyLoaderSingletonHolder {
        private final static PropertyLoader instance = new PropertyLoader();
    }

    public static PropertyLoader getInstance() {
        return PropertyLoaderSingletonHolder.instance;
    }
}