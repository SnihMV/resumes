package ru.msnih.resumes.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.msnih.resumes.exception.AlreadyExistStorageException;
import ru.msnih.resumes.exception.NotExistStorageException;
import ru.msnih.resumes.model.*;

import java.time.Month;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractStorageTest {

    protected final Storage storage;

    protected static final String STORAGE_DIR = "C:\\workspace\\resumes\\Resumes\\storage";

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String DUMMY = "dummy";

    public static final Resume R1;
    public static final Resume R2;
    public static final Resume R3;
    public static final Resume R4;

    static {
        R1 = new Resume(UUID_1, "Name1");
        R2 = new Resume(UUID_2, "Name4");
        R3 = new Resume(UUID_3, "Name2");
        R4 = new Resume(UUID_4, "Name3");

        R1.addContact(ContactType.PHONE, "89219894856");
        R1.addContact(ContactType.EMAIL, "SnihMV@gmail.com");
        R1.addContact(ContactType.HOMEPAGE, "www.sneech.com");
        R1.addSection(SectionType.OBJECTIVE, new TextSection("Objective section R1"));
        R1.addSection(SectionType.PERSONAL, new TextSection("Personal section R1"));
        R1.addSection(SectionType.ACHIEVEMENT, new ListSection());
        R1.addSection(SectionType.QUALIFICATION, new ListSection("Java", "SQL", "Spring"));
        R1.addSection(SectionType.EXPERIENCE, new OrganizationSection(
                new Organization("Kremlin", "www.kremlin.ru",
                        new Organization.Position("President", null, 2024, Month.MARCH),
                        new Organization.Position("President", null, 2024, Month.MARCH, 2032, Month.APRIL)),
                new Organization("Waverma", null,
                        new Organization.Position("Waiter", null, 1998, Month.DECEMBER),
                        new Organization.Position("Waiter", null, 1999, Month.JANUARY),
                        new Organization.Position("Waiter", null, 1999, Month.JANUARY, 2032, Month.APRIL))));
        R1.addSection(SectionType.EDUCATION, new OrganizationSection(
                new Organization("BGTU", null,
                        new Organization.Position("student", "learning", 2004, Month.SEPTEMBER, 2009, Month.JULY),
                        new Organization.Position("aspirant", null, 2009, Month.AUGUST, 2012, Month.JUNE))));
    }

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
        assertEquals(new Resume(UUID_1, DUMMY), storage.get(UUID_1));
        assertNotEquals(new Resume(UUID_2, DUMMY), storage.get(UUID_1));
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
        Resume resume = storage.get("uuid2");
        storage.update(new Resume(UUID_2, DUMMY));
        assertNotSame(resume, storage.get("uuid2"));
        assertEquals(resume, storage.get("uuid2"));
        assertSize(4);
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

    protected void assertSize(int i) {
        assertEquals(i, storage.size());
    }
}