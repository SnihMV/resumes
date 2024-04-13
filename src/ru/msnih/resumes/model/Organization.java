package ru.msnih.resumes.model;

import ru.msnih.resumes.util.XmlLocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static ru.msnih.resumes.util.DateUtil.of;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {

    private Link link;
    private final Set<Position> positions = new TreeSet<>();

    public Organization() {
    }

    public Organization(String name, String url) {
        this(name, url, Collections.emptySet());
    }

    public Organization(String name, String url, Position... positions) {
        this(name, url, Arrays.asList(positions));
    }

    public Organization(String name, String url, Collection<Position> positions) {
        this.link = new Link(name, url);
        this.positions.addAll(positions);
    }

    public Link getLink() {
        return link;
    }

    public void addPosition(Position position) {
        positions.add(position);
    }

    public Set<Position> getPositions() {
        return positions;
    }

    public void setPositions(Collection<Position> positions) {
        this.positions.addAll(positions);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Organization that = (Organization) object;
        return Objects.equals(link, that.link) && Objects.equals(positions, that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link, positions);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(link + "\n");
        for (Position p : positions) {
            sb.append(p + "\n");
            sb.append("------------------------\n");
        }
        return sb.toString();
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Position implements Comparable<Position>, Serializable {

        private String title;
        private String description;
        @XmlJavaTypeAdapter(XmlLocalDateAdapter.class)
        private LocalDate startDate;
        @XmlJavaTypeAdapter(XmlLocalDateAdapter.class)
        private LocalDate endDate;

        public Position() {
        }

        public Position(String title, String description, int startYear, Month startMonth) {
            this(title, description, of(startYear, startMonth), null);
        }

        public Position(String name, String description, int startYear, Month startMonth, int endYear, Month endMonth) {
            this(name, description, of(startYear, startMonth), of(endYear, endMonth));
        }

        public Position(String title, String description, LocalDate startDate, LocalDate endDate) {
            setTitle(title);
            setDescription(description);
            setStartDate(startDate);
            setEndDate(endDate);
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) return true;
            if (object == null || getClass() != object.getClass()) return false;
            Position position = (Position) object;
            return Objects.equals(title, position.title) &&
                    Objects.equals(description, position.description) &&
                    Objects.equals(startDate, position.startDate) &&
                    Objects.equals(endDate, position.endDate);
        }

        @Override
        public int hashCode() {
            return Objects.hash(title, description, startDate, endDate);
        }

        @Override
        public String toString() {
            return startDate + " - " + (endDate != null ? endDate : "н.в.") + " | " + title
                    + (description != null ? "\n" + description : "");
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            Objects.requireNonNull(title, "Position title cannot be null");
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public void setStartDate(LocalDate startDate) {
            Objects.requireNonNull(startDate, "Position start date cannot be null");
            this.startDate = startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public void setEndDate(LocalDate endDate) {
            this.endDate = endDate;
        }

        @Override
        public int compareTo(Position that) {
            return Comparator.comparing(Position::getStartDate)
                    .thenComparing(Position::getEndDate, Comparator.nullsLast(LocalDate::compareTo))
                    .thenComparing(Position::getTitle, String::compareToIgnoreCase).compare(this, that);
        }
    }
}
