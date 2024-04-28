package ru.msnih.resumes.exception;

import java.sql.SQLException;

public class StorageException extends RuntimeException {
    private final String uuid;

    public StorageException(String message) {
        this(message, null, null);
    }

    public StorageException(String message, String uuid) {
        this(message, uuid, null);
    }

    public StorageException(String message, Exception e) {
        this(message, null, e);
    }

    public StorageException(Exception e) {
        this(null, null, e);
    }

    public StorageException(String message, String uuid, Exception e) {
        super(message, e);
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }
}
