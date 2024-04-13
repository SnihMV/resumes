package ru.msnih.resumes.model;

import java.util.*;

public class ListSection extends Section {

    private final List<String> list = new ArrayList<>();

    public ListSection() {
        this(Collections.emptyList());
    }

    public ListSection(String... list) {
        this(Arrays.asList(list));
    }

    public ListSection(Collection<String> collection) {
        Objects.requireNonNull(collection, "Section list cannot be null");
        list.addAll(collection);
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
