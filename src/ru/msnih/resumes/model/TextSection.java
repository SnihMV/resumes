package ru.msnih.resumes.model;

import java.util.Objects;

public class TextSection extends Section {

    private String content;

    public TextSection(String content) {
        setContent(content);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        Objects.requireNonNull(content, "Section content cannot be null");
        this.content = content;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
        return Objects.equals(content, that.content);
    }

    public int hashCode() {
        return content != null ? content.hashCode() : 0;
    }

     public String toString(){
        return content;
     }
}
