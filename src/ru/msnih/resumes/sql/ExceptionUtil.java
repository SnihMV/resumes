package ru.msnih.resumes.sql;

import ru.msnih.resumes.exception.AlreadyExistStorageException;
import ru.msnih.resumes.exception.StorageException;

import java.sql.SQLException;

public class ExceptionUtil {
    private ExceptionUtil(){}

    public static StorageException convertException(SQLException e) {
        if (e.getSQLState().equals("23505")) {
            return new AlreadyExistStorageException(e.getMessage());
        }
        return new StorageException(e.getMessage(), e);
    }
}
