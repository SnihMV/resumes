package ru.msnih.resumes.storage.serialization;

import ru.msnih.resumes.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(outputStream)) {
            dos.writeUTF(resume.getUuid());
            boolean hasFullName = resume.getFullName() != null;
            dos.writeBoolean(hasFullName);
            if (hasFullName)
                dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> contact : contacts.entrySet()) {
                dos.writeUTF(contact.getKey().name());
                dos.writeUTF(contact.getValue());
            }
            Map<SectionType, Section> sections = resume.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                switch (entry.getKey()) {
                    case PERSONAL, OBJECTIVE -> dos.writeUTF(entry.getValue().toString());
                    case ACHIEVEMENT, QUALIFICATION -> {
                        List<String> list = ((ListSection) entry.getValue()).getList();
                        dos.writeInt(list.size());
                        for (String item : list) {
                            dos.writeUTF(item);
                        }
                    }
                    case EDUCATION, EXPERIENCE -> {
                        List<Organization> organizations = ((OrganizationSection) entry.getValue()).getOrganizations();
                        dos.writeInt(organizations.size());
                        for (Organization org : organizations) {
                            dos.writeUTF(org.getLink().getName());
                            String url = org.getLink().getUrl();
                            dos.writeBoolean(url != null);
                            if (url != null) {
                                dos.writeUTF(url);
                            }
                            Set<Organization.Position> positions = org.getPositions();
                            dos.writeInt(positions.size());
                            for (Organization.Position position : positions) {
                                dos.writeUTF(position.getTitle());
                                String description = position.getDescription();
                                dos.writeBoolean(description != null);
                                if (description != null) {
                                    dos.writeUTF(description);
                                }
                                dos.writeUTF(position.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                                LocalDate endDate = position.getEndDate();
                                dos.writeBoolean(endDate != null);
                                if (endDate != null) {
                                    dos.writeUTF(endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (DataInputStream dis = new DataInputStream(inputStream)) {
            String uuid = dis.readUTF();
            String fullName = dis.readBoolean() ? dis.readUTF() : null;
            Resume resume = new Resume(uuid, fullName);
            int contactsCount = dis.readInt();
            for (int i = 0; i < contactsCount; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            int sectionsCount = dis.readInt();
            for (int i = 0; i < sectionsCount; i++) {
                String type = dis.readUTF();
                Section section = null;
                switch (type) {
                    case "PERSONAL", "OBJECTIVE" -> {
                        section = new TextSection(dis.readUTF());
                    }
                    case "ACHIEVEMENT", "QUALIFICATION" -> {
                        ListSection listSection = new ListSection();
                        int listSize = dis.readInt();
                        for (int j = 0; j < listSize; j++) {
                            listSection.addItem(dis.readUTF());
                        }
                        section = listSection;
                    }
                    case "EDUCATION", "EXPERIENCE" -> {
                        OrganizationSection organizationSection = new OrganizationSection();
                        int organizationCount = dis.readInt();
                        for (int j = 0; j < organizationCount; j++) {
                            String name = dis.readUTF();
                            String url = null;
                            if (dis.readBoolean()) {
                                url = dis.readUTF();
                            }
                            Organization organization = new Organization(name, url);
                            int positionsCount = dis.readInt();
                            for (int k = 0; k < positionsCount; k++) {
                                String title = dis.readUTF();
                                String desciption = null;
                                if (dis.readBoolean()) {
                                    desciption = dis.readUTF();
                                }
                                LocalDate startDate = LocalDate.parse(dis.readUTF());
                                LocalDate endDate = null;
                                if (dis.readBoolean()) {
                                    endDate = LocalDate.parse(dis.readUTF());
                                }
                                organization.addPosition(new Organization.Position(title,desciption,startDate,endDate));
                            }
                            organizationSection.addOrganization(organization);
                        }
                        section = organizationSection;
                    }
                }
                resume.addSection(SectionType.valueOf(type), section);
            }
            return resume;
        }
    }
}
