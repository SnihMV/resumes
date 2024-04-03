package ru.msnih.resumes;


import ru.msnih.resumes.exception.StorageException;
import ru.msnih.resumes.model.*;
import ru.msnih.resumes.storage.ArrayStorage;
import ru.msnih.resumes.storage.Storage;

import java.time.LocalDate;
import java.time.Month;
import java.util.Set;
import java.util.TreeSet;

public class MainTestArrayStorage {
    static final Storage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1", "name1");
        Resume r2 = new Resume("uuid2", "name2");
        Resume r3 = new Resume("uuid3", "name3");
        Resume r4 = new Resume("uuid4", "name4");

        ARRAY_STORAGE.save(r3);
        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r4);


        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());
        try {
            System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));
        } catch (StorageException e) {
            System.out.println(e.getMessage());
        }

        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());


        Resume R1 = new Resume("Mike Snih");
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
        System.out.println(R1);

    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
    }
}
