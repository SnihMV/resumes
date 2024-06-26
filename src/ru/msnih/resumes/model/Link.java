package ru.msnih.resumes.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.Objects;
@XmlAccessorType(XmlAccessType.FIELD)
public class Link implements Serializable {
    private String name;
    private String url;

    public Link() {
    }

    public Link(String name) {
        this(name, null);
    }

    public Link(String name, String url) {
        Objects.requireNonNull(name, "Organization name cannot be null");
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return Objects.equals(name, link.name) && Objects.equals(url, link.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url);
    }

    @Override
    public String toString() {
        return name + " (" + (url != null ? url : "no URL") + ")";
    }
}
