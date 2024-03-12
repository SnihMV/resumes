package ru.msnih.resumes.storage;


import ru.msnih.resumes.model.Resume;

import java.util.Arrays;

public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size;

    public void clear() {
        size = 0;
    }

    public void save(Resume r) {
        if (size == storage.length) {
            System.out.println("Can not be saved because of storage is full");
            return;
        }
        if (getIndex(r.getUuid()) < 0) {
            storage[size++] = r;
        } else System.out.println("This resume already exists in the storage");
    }

    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            storage[index] = r;
        } else System.out.println("There is no such resume in the storge");
    }

    public Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            storage[index] = storage[size - 1];
            size--;
        } else
            System.out.println("Resume with uuid=" + uuid + " does not exist in the storage");
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
