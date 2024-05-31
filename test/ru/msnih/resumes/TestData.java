package ru.msnih.resumes;

import ru.msnih.resumes.model.*;

import java.time.Month;
import java.util.UUID;

public class TestData {
    public static final String UUID_1 = UUID.randomUUID().toString();
    public static final String UUID_2 = UUID.randomUUID().toString();
    public static final String UUID_3 = UUID.randomUUID().toString();
    public static final String UUID_4 = UUID.randomUUID().toString();
    public static final String DUMMY = "dummy";

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
        R1.addSection(SectionType.ACHIEVEMENTS, new ListSection("W", "Number 1"));
        R1.addSection(SectionType.QUALIFICATIONS, new ListSection("Java", "SQL", "Spring"));
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
        R3.addSection(SectionType.QUALIFICATIONS, new ListSection("Java", "SQL", "Spring"));
        R3.addSection(SectionType.EDUCATION, new OrganizationSection(
                new Organization("SPBGTU", "politech.ru",
                        new Organization.Position("student", "learning", 2004, Month.SEPTEMBER, 2009, Month.JULY),
                        new Organization.Position("aspirant", null, 2009, Month.AUGUST, 2012, Month.JUNE))));

    }
}
