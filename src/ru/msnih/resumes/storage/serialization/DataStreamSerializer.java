package ru.msnih.resumes.storage.serialization;

import ru.msnih.resumes.exception.StorageException;
import ru.msnih.resumes.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(outputStream)) {
            dos.writeUTF(resume.getUuid());
            writeNullable(resume.getFullName(), String::toString, dos);
            writeCollection(dos, resume.getContacts().entrySet(), contact -> {
                dos.writeUTF(contact.getKey().name());
                dos.writeUTF(contact.getValue());
            });
            writeCollection(dos, resume.getSections().entrySet(), section -> {
                dos.writeUTF(section.getKey().name());
                switch (section.getKey()) {
                    case PERSONAL, OBJECTIVE -> dos.writeUTF(((TextSection) section.getValue()).getContent());
                    case ACHIEVEMENT, QUALIFICATION ->
                            writeCollection(dos, ((ListSection) section.getValue()).getList(), dos::writeUTF);
                    case EDUCATION, EXPERIENCE ->
                            writeCollection(dos, ((OrganizationSection) section.getValue()).getOrganizations(), organization -> {
                                dos.writeUTF(organization.getLink().getName());
                                writeNullable(organization.getLink().getUrl(), String::toString, dos);
                                writeCollection(dos, organization.getPositions(), position -> {
                                    dos.writeUTF(position.getTitle());
                                    writeNullable(position.getDescription(), String::toString, dos);
                                    dos.writeUTF(position.getStartDate().toString());
                                    writeNullable(position.getEndDate(), LocalDate::toString, dos);
                                });
                            });
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (DataInputStream dis = new DataInputStream(inputStream)) {
            Resume resume = new Resume(dis.readUTF(), readNullable(dis, String::toString));
            processCollection(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            processCollection(dis, () -> {
                SectionType type = SectionType.valueOf(dis.readUTF());
                resume.addSection(type, readSection(dis, type));
            });
            return resume;
        }
    }

    private Section readSection(DataInputStream dis, SectionType type) throws IOException {
        switch (type) {
            case PERSONAL, OBJECTIVE -> {
                return new TextSection(dis.readUTF());
            }
            case ACHIEVEMENT, QUALIFICATION -> {
                return new ListSection(readCollection(dis, dis::readUTF));
            }
            case EDUCATION, EXPERIENCE -> {
                return new OrganizationSection(readCollection(dis, () ->
                        new Organization(dis.readUTF(), readNullable(dis, String::toString), readCollection(dis, () ->
                                new Organization.Position(dis.readUTF(), readNullable(dis, String::toString),
                                        LocalDate.parse(dis.readUTF()), readNullable(dis, LocalDate::parse))
                        ))
                ));
            }
        }
        throw new StorageException("Data serialization error");
    }

    private <T> void writeCollection(DataOutputStream dos, Collection<T> collection, ElementWriter<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            writer.write(t);
        }
    }

    private void processCollection(DataInputStream dis, ElementProcessor processor) throws IOException {
        int collectionSize = dis.readInt();
        for (int i = 0; i < collectionSize; i++) {
            processor.process();
        }
    }

    private <T> Collection<T> readCollection(DataInputStream dis, ElementReader<T> reader) throws IOException {
        int collectionSize = dis.readInt();
        List<T> list = new ArrayList<>(collectionSize);
        for (int i = 0; i < collectionSize; i++) {
            list.add(reader.read());
        }
        return list;
    }

    private <T> void writeNullable(T value, Function<T, String> function, DataOutputStream dos) throws IOException {
        dos.writeBoolean(value != null);
        if (value != null)
            dos.writeUTF(function.apply(value));
    }

    private <R> R readNullable(DataInputStream dis, Function<String, R> function) throws IOException {
        R res;
        if (dis.readBoolean()) {
            res = function.apply(dis.readUTF());
        } else res = null;
        return res;
    }

    private interface ElementWriter<T> {
        void write(T t) throws IOException;
    }

    private interface ElementReader<T> {
        T read() throws IOException;
    }

    private interface ElementProcessor {
        void process() throws IOException;
    }
}
