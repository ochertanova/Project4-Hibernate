package ru.javarush.hibernate.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.javarush.hibernate.domain.City;

public class CityDAO extends GenericDAO<City> {

    public CityDAO(SessionFactory sessionFactory) {
        super(City.class, sessionFactory);
    }

    public int getTotalCount() {
        Query<Long> query = getCurrentSession().createQuery("select count(c) from Country c", Long.class);
        return Math.toIntExact(query.uniqueResult());
    }

    public City getById(Integer id) {
        Query<City> query = getCurrentSession().createQuery("select c from City c join fetch c.country where c.id = :ID", City.class);
        query.setParameter("ID", id);
        return query.getSingleResult();
    }
}
