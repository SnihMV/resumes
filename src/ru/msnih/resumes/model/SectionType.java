package ru.msnih.resumes.model;

public enum SectionType {
    OBJECTIVE("Objective"),
    PERSONAL("Personal"),
    ACHIEVEMENTS("Achievements"),
    QUALIFICATIONS("Qualifications"),
    EXPERIENCE("Experience"),
    EDUCATION("Education");

    private final String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle(){
        return title;
    }
}
