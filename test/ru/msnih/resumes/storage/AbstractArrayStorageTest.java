package ru.msnih.resumes.storage;

import org.junit.jupiter.api.Test;
import ru.msnih.resumes.exception.StorageException;
import ru.msnih.resumes.model.Resume;

import static org.junit.jupiter.api.Assertions.*;
import static ru.msnih.resumes.storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }


    @Test
    void overflowTest() {
        assertDoesNotThrow(() -> {
            for (int i = 4; i < STORAGE_LIMIT; i++) {
                storage.save(new Resume("uuid" + i + 1));
            }
        });
        assertSize(STORAGE_LIMIT);
        StorageException e = assertThrows(StorageException.class, () -> storage.save(new Resume("O V E R F L O W")));
        assertEquals("Cannot save resume due to storage is full", e.getMessage());
    }
}