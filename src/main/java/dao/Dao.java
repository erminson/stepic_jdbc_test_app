package dao;

import java.util.List;
import java.sql.SQLException;

public interface Dao<T> {
    void createTable() throws SQLException;
    void dropTable() throws SQLException;

    void save(final T t) throws SQLException;
    T get(long id) throws SQLException;
    List<T> getAll() throws SQLException;
}
