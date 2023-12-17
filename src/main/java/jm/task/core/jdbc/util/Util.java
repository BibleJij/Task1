package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

    public Connection getConnectionJDBC() {
        try {
            Properties properties = loadProperties();
            Class.forName(properties.getProperty("driver"));
            return DriverManager.getConnection(
                    properties.getProperty("url"),
                    properties.getProperty("user"),
                    properties.getProperty("password"));

        } catch (ClassNotFoundException | SQLException | IOException e) {
            throw new RuntimeException("Ошибка при получении соединения с базой данных", e);
        }
    }

    private Properties loadProperties() throws IOException {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("database.properties")){
            if (input == null) {
                throw new RuntimeException("Файл database.properties не найден");
            }
            properties.load(input);
        }
        return properties;
    }

    private static SessionFactory sessionFactory;

    public static void createSessionFactory() {
        sessionFactory = new Configuration()
                .configure()
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
    }
    public static SessionFactory getSessionFactory() {
        if(sessionFactory == null) {
            createSessionFactory();
        }
        return sessionFactory;
    }
}
