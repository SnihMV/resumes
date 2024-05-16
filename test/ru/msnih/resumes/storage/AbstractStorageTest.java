package ru.msnih.resumes.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.msnih.resumes.exception.AlreadyExistStorageException;
import ru.msnih.resumes.exception.NotExistStorageException;
import ru.msnih.resumes.model.*;
import ru.msnih.resumes.util.Config;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static ru.msnih.resumes.TestData.*;

public abstract class AbstractStorageTest {

    protected final Storage storage;

    protected static final String STORAGE_DIR_PATH = Config.getInstance().getStorageDirPath();

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(R1);
        storage.save(R2);
        storage.save(R3);
        storage.save(R4);
    }

    @Test
    void get() {
        assertGet(R1);
        assertGet(R2);
        assertGet(R3);
        assertGet(R4);
        assertNotEquals(R2, storage.get(UUID_1));
        assertThrows(NotExistStorageException.class, () -> storage.get("uuid5"));
        assertSize(4);
    }

    @Test
    void save() {
        assertDoesNotThrow(() -> storage.save(new Resume("Mike")));
        assertSize(5);
        assertThrows(AlreadyExistStorageException.class, () -> storage.save(new Resume(UUID_3, DUMMY)));
        assertSize(5);
    }

    @Test
    void update() {
        assertEquals(R2, storage.get(R2.getUuid()));
        Resume updatedResume = new Resume(R2.getUuid(), "Nick");
        assertDoesNotThrow(() -> storage.update(updatedResume));
        assertThrows(NotExistStorageException.class, () -> storage.update(new Resume("newName")));
        assertNotEquals(R2, storage.get(R2.getUuid()));
        assertEquals(R4, storage.get(R4.getUuid()));
        Resume newResume = new Resume(R4.getUuid(), R4.getFullName());
        newResume.addSection(SectionType.OBJECTIVE, new TextSection("clever"));
        assertDoesNotThrow(() -> storage.update(newResume));
        assertNotEquals(R4, storage.get(R4.getUuid()));

    }

    @Test
    void delete() {
        assertDoesNotThrow(() -> storage.delete(UUID_2));
        assertSize(3);
        assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_2));
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
    void getAllSorted() {
        assertIterableEquals(Arrays.asList(R1, R3, R4, R2), storage.getAllSorted());
    }

    protected void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    protected void assertSize(int i) {
        assertEquals(i, storage.size());
    }
}