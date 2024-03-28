package ru.msnih.resumes.storage;

import ru.msnih.resumes.exception.AlreadyExistStorageException;
import ru.msnih.resumes.exception.NotExistStorageException;
import ru.msnih.resumes.model.Resume;

public abstract class AbstractStorage<K> implements Storage {

    protected abstract Resume doGet(K searchKey);

    protected abstract void doSave(Resume resume, K searchKey);

    protected abstract void doUpdate(Resume resume, K searchKey);

    protected abstract void doDelete(K searchKey);

    protected abstract K getSearchKey(String uuid);

    protected abstract boolean isExist(K searchKey);

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

    private K getExistedKey(String uuid) {
        K searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }
    private K getNotExistedKey(String uuid) {
        K searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new AlreadyExistStorageException(uuid);
        }
        return searchKey;
    }

}
