package ru.msnih.resumes.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable {

    private final String uuid;
    private String fullName;
    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

    public Resume() {
        this(null);
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

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public Map<SectionType, Section> getSections() {
        return sections;
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
        this.fullName = fullName;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Resume resume = (Resume) object;
        return Objects.equals(uuid, resume.uuid)
                && Objects.equals(fullName, resume.fullName)
                && Objects.equals(contacts, resume.contacts)
                && Objects.equals(sections, resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Name: \"" + fullName + "\" | UUID: \"" + uuid + "\"\n");
        if (!contacts.isEmpty()) {
            sb.append("Contacts:\n");
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                sb.append(entry.getKey().getTitle()).append(":").append(entry.getValue()).append("\n");
            }
        }
        if (!sections.isEmpty()) {
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                sb.append(entry.getKey().getTitle()).append(":").append(entry.getValue()).append("\n");
            }
        }
        sb.append("-------------------------------------------------");
        return sb.toString();
    }

    @Override
    public int compareTo(Resume that) {
        return Comparator.comparing(Resume::getFullName, Comparator.nullsLast(String::compareToIgnoreCase))
                .thenComparing(Resume::getUuid).compare(this, that);
    }
}
