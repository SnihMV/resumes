package ru.msnih.resumes.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.msnih.resumes.exception.AlreadyExistStorageException;
import ru.msnih.resumes.exception.NotExistStorageException;
import ru.msnih.resumes.model.*;
import ru.msnih.resumes.util.Config;

import java.time.Month;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractStorageTest {

    protected final Storage storage;

    protected static final String STORAGE_DIR_PATH = Config.getInstance().getStorageDirPath();

    private static final String UUID_1 = UUID.randomUUID().toString();
    private static final String UUID_2 = UUID.randomUUID().toString();
    private static final String UUID_3 = UUID.randomUUID().toString();
    private static final String UUID_4 = UUID.randomUUID().toString();
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

        R3.addContact(ContactType.SKYPE, "R3-skype");
        R3.addContact(ContactType.PHONE, "89114895623");
        R3.addContact(ContactType.EMAIL, "r3@mail.ru");
        R3.addContact(ContactType.GITHUB, "github.com/r3");
        R3.addSection(SectionType.OBJECTIVE, new TextSection("Objective section R3"));
        R3.addSection(SectionType.EXPERIENCE, new OrganizationSection(
                new Organization("yandex", "ya.ru",
                        new Organization.Position("CEO", "CEO position description", 2024, Month.MARCH),
                        new Organization.Position("developer", null, 2024, Month.MARCH, 2032, Month.APRIL)),
                new Organization("Waverma", null,
                        new Organization.Position("Waiter", null, 1998, Month.DECEMBER),
                        new Organization.Position("Waiter", null, 1999, Month.JANUARY),
                        new Organization.Position("Waiter", null, 1999, Month.JANUARY, 2032, Month.APRIL))));
        R3.addSection(SectionType.PERSONAL, new TextSection("Personal section R3"));
        R3.addSection(SectionType.ACHIEVEMENT, new ListSection());
        R3.addSection(SectionType.QUALIFICATION, new ListSection("Java", "SQL", "Spring"));
        R3.addSection(SectionType.EDUCATION, new OrganizationSection(
                new Organization("SPBGTU", "politech.ru",
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
        Resume resume = storage.get(R2.getUuid());
        resume.setFullName("Mike");
        resume.addContact(ContactType.PHONE, "8924895651");
        resume.addSection(SectionType.QUALIFICATION, new ListSection("asd", "qwe", "rty"));
        assertDoesNotThrow(() -> storage.update(resume));
        assertNotEquals(R2, storage.get(R2.getUuid()));
        assertThrows(NotExistStorageException.class, () -> storage.update(new Resume(DUMMY)));
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

    protected void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    protected void assertSize(int i) {
        assertEquals(i, storage.size());
    }
}