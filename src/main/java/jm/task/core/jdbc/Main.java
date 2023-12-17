package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

public class Main {
    public static void main(String[] args) {
        UserDaoJDBCImpl userDaoJDBC = new UserDaoJDBCImpl();
        userDaoJDBC.createUsersTable();
        userDaoJDBC.saveUser("Pasha", "Tehnic", (byte) 36);
        userDaoJDBC.saveUser("Anton", "Ulyanov", (byte) 22);
        userDaoJDBC.removeUserById(1);
        System.out.println(userDaoJDBC.getAllUsers());
        userDaoJDBC.dropUsersTable();

    }
}
