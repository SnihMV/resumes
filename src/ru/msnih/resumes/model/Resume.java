package ru.msnih.resumes.model;

import java.util.Comparator;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.UUID;

public class Resume implements Comparable<Resume> {

    private static final Comparator<Resume> RESUME_COMPARATOR;
    private static final Comparator<String> NULL_SAFE_STRING_COMPARATOR;

    private final String uuid;
    private String fullName;

    static {
        NULL_SAFE_STRING_COMPARATOR = Comparator.nullsLast(String::compareToIgnoreCase);
        RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName, NULL_SAFE_STRING_COMPARATOR)
                .thenComparing(Resume::getUuid, NULL_SAFE_STRING_COMPARATOR);
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "Resume id cannot be null");
        this.uuid = uuid;
        setFullName(fullName);
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
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
        return "UUID: \"" + uuid + "\" Name: \"" + fullName + "\"";
    }

    @Override
    public int compareTo(Resume that) {
        return RESUME_COMPARATOR.compare(this, that);
    }
}
