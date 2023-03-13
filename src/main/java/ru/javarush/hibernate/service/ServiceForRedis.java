package ru.javarush.hibernate.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisStringCommands;
import ru.javarush.hibernate.domain.City;
import ru.javarush.hibernate.domain.Country;
import ru.javarush.hibernate.domain.CountryLanguage;
import ru.javarush.hibernate.repository.InitRepository;
import ru.javarush.hibernate.feature.CityCountry;
import ru.javarush.hibernate.feature.Language;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ServiceForRedis {
    private InitRepository repository;

    public ServiceForRedis(InitRepository repository) {
        this.repository = repository;
    }

    public void pushToRedis(List<CityCountry> data) {
        try (StatefulRedisConnection<String, String> connection = repository.getRedisClient().connect()) {
            RedisStringCommands<String, String> sync = connection.sync();
            for (CityCountry cityCountry : data) {
                try {
                    sync.set(String.valueOf(cityCountry.getCityId()), repository.getMapper().writeValueAsString(cityCountry));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<CityCountry> transformDate(List<City> cities) {
        return cities.stream().map(city -> {
            CityCountry cityCountry = new CityCountry();
            cityCountry.setCityId(city.getId());
            cityCountry.setCityName(city.getName());
            cityCountry.setCityDistrict(city.getDistrict());
            cityCountry.setCityPopulation(city.getPopulation());

            Country country = city.getCountry();
            cityCountry.setCountryCode(country.getCode());
            cityCountry.setAlternativeCountryCode(country.getAlternativeCountryCode());
            cityCountry.setCountryName(country.getName());
            cityCountry.setCountryPopulation(country.getPopulation());
            cityCountry.setCountryRegion(country.getRegion());
            cityCountry.setCountrySurfaceArea(country.getSurfaceArea());

            Set<CountryLanguage> countryLanguages = country.getLanguages();
            Set<Language> languages = countryLanguages.stream().map(countryLanguage -> {
                Language language = new Language();
                language.setLanguage(countryLanguage.getLanguage());
                language.setIsOfficial(countryLanguage.getIsOfficial());
                language.setPercentage(countryLanguage.getPercentage());
                return language;
            }).collect(Collectors.toSet());
            cityCountry.setLanguages(languages);

            return cityCountry;
        }).collect(Collectors.toList());
    }

    public void testRedisData(List<Integer> ids) {
        try (StatefulRedisConnection<String, String> connection = repository.getRedisClient().connect()) {
            RedisStringCommands<String, String> sync = connection.sync();
            for (Integer id : ids) {
                String value = sync.get(String.valueOf(id));
                try {
                    repository.getMapper().readValue(value, CityCountry.class);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
