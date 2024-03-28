package ru.msnih.resumes.storage;

import ru.msnih.resumes.model.Resume;
import static java.util.Arrays.copyOf;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SortedArrayStorage extends AbstractArrayStorage {


    @Override
    protected Integer getSearchKey(String uuid) {
        return Arrays.binarySearch(storage, 0, size, new Resume(uuid));
    }

    @Override
    protected void insertElement(Resume resume, int index) {
        int insertionPoint = -index - 1;
        System.arraycopy(storage, insertionPoint, storage, insertionPoint + 1, size - insertionPoint);
        storage[insertionPoint] = resume;
    }

    @Override
    protected void fillDeletedElement(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index + 1);
    }

    @Override
    public List<Resume> getAllSorted() {
        return Arrays.asList(copyOf(storage,size));
    }
}
