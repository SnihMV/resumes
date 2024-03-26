package ru.msnih.resumes.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.msnih.resumes.exception.AlreadyExistStorageException;
import ru.msnih.resumes.exception.NotExistStorageException;
import ru.msnih.resumes.exception.StorageException;
import ru.msnih.resumes.model.Resume;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;
    private static final String UUID_1 = "uuid1";
    public static final Resume RESUME_1 = new Resume(UUID_1);
    private static final String UUID_2 = "uuid2";
    public static final Resume RESUME_2 = new Resume(UUID_2);
    private static final String UUID_3 = "uuid3";
    public static final Resume RESUME_3 = new Resume(UUID_3);
    private static final String UUID_4 = "uuid4";
    public static final Resume RESUME_4 = new Resume(UUID_4);

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
        storage.save(RESUME_4);
    }

    @Test
    void get() {
        assertEquals(new Resume(UUID_1), storage.get(UUID_1));
        assertNotEquals(new Resume(UUID_2), storage.get(UUID_1));
        assertThrows(NotExistStorageException.class, () -> storage.get("uuid5"));
        assertSize(4);
    }

    @Test
    void save() {
        assertDoesNotThrow(() -> storage.save(new Resume("uuid5")));
        assertSize(5);
        assertThrows(AlreadyExistStorageException.class, () -> storage.save(new Resume("uuid1")));
        assertSize(5);

    }

    @Test
    void update() {
        Resume resume = storage.get("uuid2");
        storage.update(new Resume("uuid2"));
        assertFalse(resume == storage.get("uuid2"));
    }

    @Test
    void delete() {
        assertDoesNotThrow(() -> storage.delete(UUID_1));
        assertSize(3);
        assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_1));
        assertSize(3);
    }

    @Test
    void size() {
        assertSize(4);
    }

    @Test
    void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    void getAll() {
        assertArrayEquals(new Resume[]{RESUME_1, RESUME_2, RESUME_3, RESUME_4}, storage.getAll());
        assertSize(4);
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

    private void assertSize(int expected) {
        assertEquals(expected, storage.size());
    }
}