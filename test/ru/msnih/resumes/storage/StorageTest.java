package ru.msnih.resumes.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.msnih.resumes.exception.AlreadyExistStorageException;
import ru.msnih.resumes.exception.NotExistStorageException;
import ru.msnih.resumes.model.Resume;

import static org.junit.jupiter.api.Assertions.*;

public abstract class StorageTest {

    protected final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    public static final Resume RESUME_1 = new Resume(UUID_1);
    public static final Resume RESUME_2 = new Resume(UUID_2);
    public static final Resume RESUME_3 = new Resume(UUID_3);
    public static final Resume RESUME_4 = new Resume(UUID_4);

    public StorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.clear();
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

    protected void assertSize(int i) {
        assertEquals(i, storage.size());
    }
}