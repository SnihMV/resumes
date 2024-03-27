package ru.msnih.resumes.storage;

import ru.msnih.resumes.exception.AlreadyExistStorageException;
import ru.msnih.resumes.exception.NotExistStorageException;
import ru.msnih.resumes.exception.StorageException;
import ru.msnih.resumes.model.Resume;

import java.util.Arrays;

public abstract class AbstractStorage implements Storage {

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doSave(Resume resume, Object searchKey);

    protected abstract void doUpdate(Resume resume, Object searchKey);

    protected abstract void doDelete(Object searchKey);

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean isExist(Object searchKey);

    public Resume get(String uuid) {
        return doGet(getExistedKey(uuid));
    }

    public void save(Resume resume) {
        doSave(resume, getNotExistedKey(resume.getUuid()));
    }

    public void update(Resume resume) {
        doUpdate(resume, getExistedKey(resume.getUuid()));
    }

    public void delete(String uuid) {
        doDelete(getExistedKey(uuid));
    }

    private Object getExistedKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }
    private Object getNotExistedKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new AlreadyExistStorageException(uuid);
        }
        return searchKey;
    }

}
