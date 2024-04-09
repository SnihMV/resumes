package ru.msnih.resumes.storage.serialization;

import ru.msnih.resumes.exception.StorageException;
import ru.msnih.resumes.model.Resume;

import java.io.*;

public class ObjectStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (ObjectOutput output = new ObjectOutputStream(outputStream)) {
            output.writeObject(resume);
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (ObjectInput input = new ObjectInputStream(inputStream)) {
            return (Resume) input.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Deserialization error", e);
        }
    }
}
