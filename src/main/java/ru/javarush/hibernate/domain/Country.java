package ru.javarush.hibernate.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.javarush.hibernate.enums.Continent;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(schema = "world", name = "country")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String code;

    @Column(name = "code_2")
    private String alternativeCountryCode;

    private String name;

    @Enumerated(EnumType.ORDINAL)
    private Continent continent;

    private String region;

    @Column(name = "surface_area")
    private BigDecimal surfaceArea;

    @Column(name = "indep_year")
    private Short indepYear;

    private Integer population;

    @Column(name = "life_expectancy")
    private BigDecimal lifeExpectancy;

    private BigDecimal gnp;

    @Column(name = "gnpo_id")
    private BigDecimal qnpoId;

    @Column(name = "local_name")
    private String localName;

    @Column(name = "government_form")
    private String governmentForm;

    @Column(name = "head_of_state")
    private String headOfState;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "capital")
    private City city;

    @OneToMany
    @JoinColumn(name = "country_id")
    private Set<CountryLanguage> languages;
}
