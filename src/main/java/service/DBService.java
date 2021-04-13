package service;

import exception.DBException;
import model.User;
import dao.Dao;
import dao.UserDao;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class DBService {
    public enum DBType {
        MYSQL,
        SQLITE,
        H2
    }

    private final Connection connection;
    private final Dao<User> userDao;

    public DBService(final DBType dbType) {
        switch (dbType) {
            case MYSQL:
                this.connection = getMysqlConnection();
                break;
            case SQLITE:
                this.connection = getSqliteConnection();
                break;
            case H2:
                this.connection = getH2Connection();
                break;
            default:
                this.connection = null;
        }

        this.userDao = new UserDao(this.connection);
    }

    public void createTable() throws DBException {
        try {
            this.userDao.createTable();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void saveUser(final String name) throws DBException {
        try {
            this.userDao.save(new User(0,name));
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public List<User> getAllUsers() throws DBException {
        try {
            List<User> users = this.userDao.getAll();
            return users;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void cleanUp() throws DBException {
        try {
            this.userDao.dropTable();
        } catch(SQLException e) {
            throw new DBException(e);
        }
    }

    private static  Connection getMysqlConnection() {
        try {
            final Driver driver = (Driver)(Class.forName("com.mysql.cj.jdbc.Driver").newInstance());
            DriverManager.registerDriver(driver);

            StringBuilder url = new StringBuilder();
            url.append("jdbc:mysql://");
            url.append("localhost:");
            url.append("3306/");
            url.append("db_test?");
            url.append("user=root&");
            url.append("password=rootroot");

            System.out.println("MYSQL. Url: " + url.toString());
            final Connection connection = DriverManager.getConnection(url.toString());
            return connection;

        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static Connection getSqliteConnection() {
        try {
            final String url = "jdbc:sqlite:./sqlitedb.db";
            final Connection connection = DriverManager.getConnection(url);
            return connection;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    private static Connection getH2Connection() {
        try {
            final String url = "jdbc:h2:~/h2db";
            final String name = "root";
            final String pass = "rootroot";
            final Connection connection = DriverManager.getConnection(url, name, pass);

            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
