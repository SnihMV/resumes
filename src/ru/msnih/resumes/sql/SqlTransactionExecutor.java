package ru.msnih.resumes.sql;

import java.sql.Connection;
import java.sql.SQLException;

public interface SqlTransactionExecutor<T> {
    T execute(Connection connection) throws SQLException;
}
