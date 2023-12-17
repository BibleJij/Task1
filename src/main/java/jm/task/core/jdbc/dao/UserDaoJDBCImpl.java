package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final Util util;
    public UserDaoJDBCImpl() {
        this.util = new Util();
    }

    public void createUsersTable() {
        String create = "CREATE TABLE IF NOT EXISTS users.User (" +
                "id BIGSERIAL PRIMARY KEY, " +
                "name VARCHAR(255), " +
                "lastName VARCHAR(255), " +
                "age SMALLINT)";
        try (Connection connection = util.getConnectionJDBC()){
            Statement statement = connection.createStatement();
            statement.execute(create);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String drop = "DROP TABLE IF EXISTS users.User";
        try (Connection connection = util.getConnectionJDBC()){
            Statement statement = connection.createStatement();
            statement.execute(drop);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String insert = "INSERT INTO users.User (name, lastName, age) VALUES (?, ?, ?)";
        try(Connection connection = util.getConnectionJDBC();
            PreparedStatement preparedStatement = connection.prepareStatement(insert)){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String delete = "DELETE FROM users.User WHERE id = ?";
        try (Connection connection = util.getConnectionJDBC();
             PreparedStatement preparedStatement = connection.prepareStatement(delete)) {
            preparedStatement.setLong(1, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String select = "SELECT * FROM users.User";
        try (Connection connection = util.getConnectionJDBC();
             PreparedStatement preparedStatement = connection.prepareStatement(select);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age")
                );
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public void cleanUsersTable() {
        String delete = "DELETE * FROM users.user";
        try (Connection connection = util.getConnectionJDBC()){
            Statement statement = connection.createStatement();
            statement.execute(delete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
