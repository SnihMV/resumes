package ru.msnih.resumes.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListSection extends Section {

    private final List<String> list;

    public ListSection() {
        this.list = new ArrayList<>();
    }

    public ListSection(String... list) {
        this(Arrays.asList(list));
    }

    public ListSection(List<String> list) {
        Objects.requireNonNull(list, "Section list cannot be null");
        this.list = list;
    }

    public void addItem(String item) {
        list.add(item);
    }

    public List<String> getList() {
        return list;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return list.equals(that.list);
    }

    public int hashCode() {
        return list.hashCode();
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s).append("\n");
        }
        return sb.toString();
    }

}
