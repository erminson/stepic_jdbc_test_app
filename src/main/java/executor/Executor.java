package executor;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class Executor {
    private final Connection connection;

    public Executor(final Connection connection) {
        this.connection = connection;
    }

    public void execUpdate(final String query) throws SQLException {
        final Statement statement = this.connection.createStatement();
        statement.execute(query);
        statement.close();
    }

    public <T> T execQuery(final String query,
                           ResultHandler<T> resultHandler) throws SQLException {
        final Statement statement = this.connection.createStatement();
        final ResultSet resultSet = statement.executeQuery(query);
        final T result = resultHandler.handle(resultSet);
        resultSet.close();
        statement.close();

        return result;
    }
}
