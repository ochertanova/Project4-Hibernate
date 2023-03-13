package ru.javarush.hibernate.service;
import org.hibernate.Session;
import ru.javarush.hibernate.constants.Constants;
import ru.javarush.hibernate.domain.City;
import ru.javarush.hibernate.domain.Country;
import ru.javarush.hibernate.domain.CountryLanguage;
import ru.javarush.hibernate.repository.InitRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ServiceForMySQL {
    private InitRepository repository;

    public ServiceForMySQL(InitRepository repository) {
        this.repository = repository;
    }

    public List<City> fetchData() {
        try (Session session = repository.getSessionFactory().getCurrentSession()) {
            List<Country> countries = new ArrayList<>();
            List<City> cities = new ArrayList<>();

            session.beginTransaction();
            countries = repository.getCountryDAO().getAll();

            int totalAmount = repository.getCityDAO().getTotalCount();
            for (int i = 0; i < totalAmount; i += Constants.DEFAULT_STEP_GET_CITY) {
                cities.addAll(repository.getCityDAO().getItems(i, Constants.DEFAULT_STEP_GET_CITY));
            }
            session.getTransaction().commit();
            return cities;
        }
    }
    public void testMysqlData(List<Integer> ids) {
        try (Session session = repository.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            for (Integer id : ids) {
                City city = repository.getCityDAO().getById(id);
                Set<CountryLanguage> languages = city.getCountry().getLanguages();
            }
            session.getTransaction().commit();
        }
    }
}
