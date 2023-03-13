package ru.javarush.hibernate;

import ru.javarush.hibernate.domain.City;
import ru.javarush.hibernate.repository.InitRepository;
import ru.javarush.hibernate.service.ServiceForMySQL;
import ru.javarush.hibernate.service.ServiceForRedis;
import ru.javarush.feature.CityCountry;

import java.util.List;

public class Main {

    public static void main(String[] agr) {
        InitRepository initRepository = new InitRepository();
        ServiceForMySQL serviceForMySQL = new ServiceForMySQL(initRepository);
        ServiceForRedis serviceForRedis = new ServiceForRedis(initRepository);

        List<City> cities = serviceForMySQL.fetchData();
        List<CityCountry> cityCountries = serviceForRedis.transformDate(cities);
        serviceForRedis.pushToRedis(cityCountries);

        //закрываем текущую сессию, чтоб точно делать запрос к БД, а не вытянуть данные из кэша
        initRepository.getSessionFactory().getCurrentSession().close();

        //выбираем случайных 10 id городов
        //так как мы не делали обработку невалидных ситуаций, используем существующие в БД id
        List<Integer> ids = List.of(1, 2, 5, 3, 10, 20, 11, 14, 4, 19);

        long startRedis = System.currentTimeMillis();
        serviceForRedis.testRedisData(ids);
        long stopRedis = System.currentTimeMillis();

        long startMysql = System.currentTimeMillis();
        serviceForMySQL.testMysqlData(ids);
        long stopMysql = System.currentTimeMillis();

        System.out.printf("%s:\t%d ms\n", "Redis", (stopRedis - startRedis));
        System.out.printf("%s:\t%d ms\n", "MySQL", (stopMysql - startMysql));

        initRepository.shutdown();
    }
}
