package ru.msnih.resumes.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface LineProcessor {
    void process(ResultSet resultSet) throws SQLException;
}
