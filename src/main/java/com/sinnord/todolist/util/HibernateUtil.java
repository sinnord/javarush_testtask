package com.sinnord.todolist.util;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Sinnord on 19.11.2016.
 */
@Repository
public class HibernateUtil {

    @Autowired
    private SessionFactory sessionFactory;

    public <T> Serializable create(final T entity) {
        return sessionFactory.getCurrentSession().save(entity);
    }

    public <T> T update(final T entity) {
        sessionFactory.getCurrentSession().update(entity);
        return entity;
    }

    public <T> void delete(final T entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }

    public <T> void delete(Serializable id, Class<T> entityClass) {
        T entity = fetchById(id, entityClass);
        delete(entity);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> fetchAll(Class<T> entityClass) {
        return sessionFactory.getCurrentSession().createQuery(" FROM "+entityClass.getName()).list();
    }

    public <T> int getSizeWithFilter(Class<T> entityClass, String filter) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM " + entityClass.getName() + (filter.isEmpty() ? "" : filter));
        return query.list().size();
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> fetchPageWithFilter(Class<T> entityClass, String filter, int offset, int maxSize) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM " + entityClass.getName() + (filter.isEmpty() ? "" : filter));
        query.setFirstResult(offset);
        query.setMaxResults(maxSize);
        return query.list();
    }

    @SuppressWarnings("rawtypes")
    public <T> List fetchAll(String query) {
        return sessionFactory.getCurrentSession().createSQLQuery(query).list();
    }

    @SuppressWarnings("unchecked")
    public <T> T fetchById(Serializable id, Class<T> entityClass) {
        return (T)sessionFactory.getCurrentSession().get(entityClass, id);
    }
}
