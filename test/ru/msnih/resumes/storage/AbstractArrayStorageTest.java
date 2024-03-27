package ru.msnih.resumes.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.msnih.resumes.exception.AlreadyExistStorageException;
import ru.msnih.resumes.exception.NotExistStorageException;
import ru.msnih.resumes.exception.StorageException;
import ru.msnih.resumes.model.Resume;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractArrayStorageTest extends StorageTest {

    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }


    @Test
    void overflowTest() {
        int storageLimit = AbstractArrayStorage.STORAGE_LIMIT;
        assertDoesNotThrow(() -> {
            for (int i = 4; i < storageLimit; i++) {
                storage.save(new Resume("uuid" + i + 1));
            }
        });
        assertSize(storageLimit);
        StorageException e = assertThrows(StorageException.class, () -> storage.save(new Resume("O V E R F L O W")));
        assertEquals("Cannot save resume due to storage is full", e.getMessage());
    }
}