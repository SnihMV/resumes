package ru.msnih.resumes.model;

public enum ContactType {
    PHONE("Телефон"),
    EMAIL("Электронная почта"),
    SKYPE("Skype"),
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    STACKOVERFLOW("Профиль Stackoverflow"),
    HOMEPAGE("Домашняя страница");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
