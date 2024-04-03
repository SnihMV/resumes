package ru.msnih.resumes.storage;

import ru.msnih.resumes.exception.StorageException;
import ru.msnih.resumes.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {

    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    protected int size;

    @Override
    protected abstract Integer getSearchKey(String uuid);

    protected abstract void insertElement(Resume resume, int index);

    protected abstract void fillDeletedElement(int index);


    @Override
    protected Resume doGet(Integer index) {
        return storage[index];
    }

    @Override
    protected void doSave(Resume resume, Integer index) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Cannot save resume due to storage is full", resume.getUuid());
        }
        insertElement(resume, index);
        size++;
    }

    @Override
    protected void doUpdate(Resume resume, Integer index) {
        storage[index] = resume;
    }

    @Override
    protected void doDelete(Integer index) {
        fillDeletedElement(index);
        size--;
    }

    @Override
    protected boolean isExist(Integer index) {
        return index >= 0;
    }

    @Override
    public int size() {
        return size;
    }

    public void clear() {
        size = 0;
    }

    @Override
    protected List<Resume> getCopyAll() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }
}
