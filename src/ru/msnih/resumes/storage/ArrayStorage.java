package ru.msnih.resumes.storage;


import ru.msnih.resumes.model.Resume;

import java.util.Arrays;
import java.util.List;

public class ArrayStorage extends AbstractArrayStorage {


    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void insertElement(Resume resume, int index) {
        storage[size] = resume;
    }

    @Override
    protected void fillDeletedElement(int index) {
        storage[index] = storage[size - 1];
    }

    @Override
    public List<Resume> getAllSorted() {
        Resume[] copied = Arrays.copyOf(storage,size);
        Arrays.sort(copied);
        return Arrays.asList(copied);
    }
}
