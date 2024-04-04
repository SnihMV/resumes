package ru.msnih.resumes.model;

import java.io.Serializable;
import java.util.*;

public class Resume implements Comparable<Resume>, Serializable {

    private static final Comparator<String> NULL_SAFE_STRING_COMPARATOR;
    private static final Comparator<Resume> RESUME_COMPARATOR;

    private final String uuid;
    private String fullName;
    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

    static {
        NULL_SAFE_STRING_COMPARATOR = Comparator.nullsLast(String::compareToIgnoreCase);
        RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName, NULL_SAFE_STRING_COMPARATOR)
                .thenComparing(Resume::getUuid, NULL_SAFE_STRING_COMPARATOR);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "Resume id cannot be null");
        this.uuid = uuid;
        setFullName(fullName);
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void addContact(ContactType type, String contact) {
        contacts.put(type, contact);
    }

    public String getContact(ContactType type) {
        return contacts.get(type);
    }

    public void addSection(SectionType type, Section section) {
        sections.put(type, section);
    }

    public Section getSection(SectionType type) {
        return sections.get(type);
    }

    public void setFullName(String fullName) {
        Objects.requireNonNull(fullName, "FullName cannot be null");
        this.fullName = fullName;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume that = (Resume) o;
        return Objects.equals(uuid, that.uuid);
    }

    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Name: \"" + fullName + "\" | UUID: \"" + uuid + "\"\n");
        if (!contacts.isEmpty()) {
            sb.append("Contacts:\n");
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                sb.append(entry.getKey().getTitle() + ":" + entry.getValue()+"\n");
            }
        }
        if (!sections.isEmpty()) {
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                sb.append(entry.getKey().getTitle() + ":" + entry.getValue() + "\n");
            }
        }
        sb.append("-------------------------------------------------");
        return sb.toString();
    }


    @Override
    public int compareTo(Resume that) {
        return RESUME_COMPARATOR.compare(this, that);
    }
}
