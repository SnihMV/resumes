package ru.msnih.resumes.exception;

public class AlreadyExistStorageException extends StorageException {
    public AlreadyExistStorageException(String uuid) {
        super("Resume with id=\"" + uuid + "\" already exists in the storage", uuid);
    }
}
