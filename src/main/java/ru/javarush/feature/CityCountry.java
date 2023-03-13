package ru.javarush.feature;


import lombok.Getter;
import lombok.Setter;
import ru.javarush.hibernate.enums.Continent;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
public class CityCountry {
    private Integer cityId;
    private String cityName;
    private String cityDistrict;
    private Integer cityPopulation;
    private String countryCode;
    private String alternativeCountryCode;
    private String countryName;
    private Continent continent;
    private String countryRegion;
    private BigDecimal countrySurfaceArea;
    private Integer countryPopulation;
    private Set<Language> languages;
}
