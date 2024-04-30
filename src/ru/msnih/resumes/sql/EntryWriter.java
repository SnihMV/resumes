package ru.msnih.resumes.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface EntryWriter {
    void write(PreparedStatement ps) throws SQLException;
}
