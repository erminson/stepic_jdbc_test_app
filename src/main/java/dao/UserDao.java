package dao;


import model.User;
import executor.Executor;

import java.sql.SQLException;
import java.sql.Connection;
import java.util.List;
import java.util.ArrayList;

public class UserDao implements Dao<User> {
    final Executor executor;

    public UserDao(final Connection connection) {
        this.executor = new Executor(connection);
    }

    @Override
    public void createTable() throws SQLException {
        this.executor.execUpdate(
                "CREATE TABLE IF NOT EXISTS users (" +
                        "id BIGINT AUTO_INCREMENT, " +
                        "name VARCHAR(256), " +
                        "PRIMARY KEY (id)" +
                        ")"
        );
    }

    @Override
    public void dropTable() throws SQLException {
        this.executor.execUpdate(
                "DROP TABLE users"
        );
    }

    @Override
    public void save(User user) throws SQLException {
        final String name = user.getName();
        this.executor.execUpdate(
                "INSERT INTO users (name) VALUES ('" +
                        name +
                        "')"
        );
    }

    @Override
    public User get(long id) throws SQLException {
        final User user = this.executor.execQuery(
                "SELECT * FROM users WHERE id=" + id,
                resultSet -> {
                    resultSet.next();
                    final long idUser = resultSet.getLong(1);
                    final String nameUser = resultSet.getString(2);
                    return new User(idUser, nameUser);
                }
        );

        return user;
    }

    @Override
    public List<User> getAll() throws SQLException {
        List<User> users = this.executor.execQuery(
                "SELECT * FROM users",
                resultSet -> {
                    List<User> allUsers = new ArrayList<>();
                    while (resultSet.next()) {
                        final long id = resultSet.getLong(1);
                        final String name = resultSet.getString(2);
                        final User user = new User(id, name);
                        allUsers.add(user);
                    }

                    return allUsers;
                }
        );

        return users;
    }
}
