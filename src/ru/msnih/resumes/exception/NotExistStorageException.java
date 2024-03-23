package ru.msnih.resumes.exception;

public class NotExistStorageException extends StorageException{
    public NotExistStorageException(String uuid) {
        super("Resume with id=\"" + uuid + "\" does not exist in the storage", uuid);
    }
}
