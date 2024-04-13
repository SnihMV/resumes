package ru.msnih.resumes.model;

import java.util.*;

public class OrganizationSection extends Section {
    private final List<Organization> organizations = new ArrayList<>();

    public OrganizationSection() {
        this(Collections.emptyList());
    }

    public OrganizationSection(Organization... organizations) {
        this(Arrays.asList(organizations));
    }

    public OrganizationSection(Collection<Organization> organizations) {
        Objects.requireNonNull(organizations, "Organization list cannot be null");
        this.organizations.addAll(organizations);
    }

    public List<Organization> getOrganizations(){
        return organizations;
    }

    public void addOrganization(Organization organization) {
        organizations.add(organization);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationSection that = (OrganizationSection) o;
        return Objects.equals(organizations, that.organizations);
    }

    @Override
    public int hashCode() {
        return organizations != null ? organizations.hashCode() : 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Organization org : organizations) {
            sb.append(org).append("\n");
        }
        return sb.toString();
    }
}
