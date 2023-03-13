package ru.javarush.hibernate.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import ru.javarush.hibernate.dao.CityDAO;
import ru.javarush.hibernate.dao.CountryDAO;
import ru.javarush.hibernate.domain.City;
import ru.javarush.hibernate.domain.Country;
import ru.javarush.hibernate.domain.CountryLanguage;

import java.util.Properties;

import static java.util.Objects.nonNull;

@Getter
public class InitRepository {
    private final CountryDAO countryDAO;
    private final CityDAO cityDAO;
    private final SessionFactory sessionFactory;
    private final ObjectMapper mapper;
    private final RedisClient redisClient;

    public InitRepository() {
        sessionFactory = prepareRenationalDb();

        countryDAO = new CountryDAO(sessionFactory);
        cityDAO = new CityDAO(sessionFactory);

        redisClient = prepareRedisClient();
        mapper = new ObjectMapper();
    }

    private SessionFactory prepareRenationalDb() {
        final SessionFactory sessionFactory;

        Properties properties = new Properties();
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        properties.put(Environment.DRIVER, "com.p6spy.engine.spy.P6SpyDriver");
        properties.put(Environment.URL, "jdbc:p6spy:mysql://localhost:3306/world");
        properties.put(Environment.USER, "root");
        properties.put(Environment.PASS, "2Rakets5");
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        properties.put(Environment.HBM2DDL_AUTO, "validate");
        properties.put(Environment.STATEMENT_BATCH_SIZE, "100");

        sessionFactory = new Configuration()
                .addAnnotatedClass(Country.class)
                .addAnnotatedClass(City.class)
                .addAnnotatedClass(CountryLanguage.class)
                .addProperties(properties)
                .buildSessionFactory();

        return sessionFactory;
    }

    private RedisClient prepareRedisClient() {
        RedisClient redisClient = RedisClient.create(RedisURI.create("localhost", 6379));
        try (StatefulRedisConnection<String, String> connection = redisClient.connect()) {
            System.out.println("\nConnected to Redis\n");
        }
        return redisClient;
    }

    public void shutdown() {
        if (nonNull(sessionFactory)) {
            sessionFactory.close();
        }
        if (nonNull(redisClient)) {
            redisClient.shutdown();
        }
    }
}
