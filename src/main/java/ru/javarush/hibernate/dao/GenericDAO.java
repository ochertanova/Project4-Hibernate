package ru.javarush.hibernate.dao;

import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public abstract class GenericDAO<T> {
    private final Class<T> clazz;
    private final SessionFactory sessionFactory;

    public GenericDAO(final Class<T> clazz, SessionFactory sessionFactory) {
        this.clazz = clazz;
        this.sessionFactory = sessionFactory;
    }

    public List<T> getItems(int offset, int count) {
        Query query = getCurrentSession().createQuery("from " + clazz.getName(), clazz);
        query.setFirstResult(offset);
        query.setMaxResults(count);
        return query.getResultList();
    }

    public List<T> getAll() {
        return getCurrentSession().createQuery("from " + clazz.getName(), clazz).list();
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
