package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        String create = "CREATE TABLE IF NOT EXISTS users.User (" +
                "id BIGSERIAL PRIMARY KEY, " +
                "name VARCHAR(255), " +
                "lastName VARCHAR(255), " +
                "age SMALLINT)";
        try (Session session =  Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery(create).executeUpdate();
            session.beginTransaction().commit();
        }
    }

    @Override
    public void dropUsersTable() {
        String drop = "DROP TABLE IF EXISTS users.User";
        try (Session session = Util.getSessionFactory().openSession()){
            session.beginTransaction();
            session.createSQLQuery(drop).executeUpdate();
            session.beginTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try (Session session = Util.getSessionFactory().openSession()){
            session.beginTransaction();
            session.save(user);
            session.beginTransaction().commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            User user = session.get(User.class, id);
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session currentSession = Util.getSessionFactory().openSession()){
            return currentSession.createQuery("FROM users.user", User.class).getResultList();
        }
    }

    @Override
    public void cleanUsersTable() {
        String delete = "DELETE FROM users.user";
        try (Session session = Util.getSessionFactory().openSession()){
            session.beginTransaction();
            session.createSQLQuery(delete).executeUpdate();
            session.beginTransaction().commit();
        }
    }
}
