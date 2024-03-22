package ru.msnih.resumes.storage;

import ru.msnih.resumes.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        System.out.println("There is no resume " + uuid + " in the storage");
        return null;
    }

    public void save(Resume resume) {
        String uuid = resume.getUuid();
        int index = getIndex(uuid);
        if (index >= 0) {
            System.out.println("Resume " + uuid + " already exists");
        } else if (size == storage.length) {
            System.out.println("Cannot save resume " + uuid + " due to storage is full");
        } else {
            insertElement(resume, index);
            size++;
        }
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
        } else System.out.println("There is no such a resume to be updated");
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Resume with uuid=" + uuid + " does not exist in the storage");
        } else {
            fillDeletedElement(index);
            size--;
        }
    }

    @Override
    public int size() {
        return size;
    }

    public void clear() {
        size = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    protected abstract void insertElement(Resume resume, int index);

    protected abstract void fillDeletedElement(int index);

    protected abstract int getIndex(String uuid);
}
