package ru.javarush.hibernate.dao;

import org.hibernate.SessionFactory;
import ru.javarush.hibernate.domain.Country;

import java.util.List;

public class CountryDAO extends GenericDAO<Country> {

    public CountryDAO(SessionFactory sessionFactory) {
        super(Country.class, sessionFactory);
    }

    @Override
    public List<Country> getAll() {
        return getCurrentSession().createQuery("select c from Country c join fetch c.languages", Country.class).list();
    }
}
