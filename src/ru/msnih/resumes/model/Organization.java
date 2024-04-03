package ru.msnih.resumes.model;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static ru.msnih.resumes.util.DateUtil.of;

public class Organization {

    private static final Comparator<Position> POSITION_COMPARATOR;
    private final Link link;
    private final Collection<Position> positions = new TreeSet<>(POSITION_COMPARATOR);

    static {
        POSITION_COMPARATOR = Comparator.comparing(Position::getStartDate)
                .thenComparing(Position::getEndDate, Comparator.nullsLast(LocalDate::compareTo))
                .thenComparing(Position::getTitle, String::compareToIgnoreCase);
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


    public void addPosition(Position position) {
        positions.add(position);
    }

    public Collection<Position> getPositions() {
        return positions;
    }

    public void setPositions(Collection<Position> positions) {
        this.positions.addAll(positions);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return link.getName().equals(that.link.getName());
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

    public static class Position {

        private String title;
        private String description;
        private LocalDate startDate;
        private LocalDate endDate;

        public Position(String title, String description, int startYear, Month startMonth) {
            this(title, description, of(startYear,startMonth), null);
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
    }
}
