package it.eforhum.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import it.eforhum.entity.TodoItem;
import it.eforhum.util.HibernateUtil;

public class TodoSession {
    
    public void save(TodoItem todoItem) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(todoItem);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    
    public void update(TodoItem todoItem) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(todoItem);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            TodoItem todoItem = session.get(TodoItem.class, id);
            if (todoItem != null) {
                session.delete(todoItem);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    
    public TodoItem findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(TodoItem.class, id);
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<TodoItem> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<TodoItem> query = session.createQuery("FROM TodoItem", TodoItem.class);
            return query.list();
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<TodoItem> findByCompleted(boolean completed) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<TodoItem> query = session.createQuery("FROM TodoItem WHERE completed = :completed", TodoItem.class);
            query.setParameter("completed", completed);
            return query.list();
        }
    }
}