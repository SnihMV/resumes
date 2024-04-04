package ru.msnih.resumes.storage;

import ru.msnih.resumes.exception.StorageException;
import ru.msnih.resumes.model.Resume;
import ru.msnih.resumes.storage.serialization.StreamSerializer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {

    private final Path directory;
    private final StreamSerializer streamSerializer;

    public PathStorage(Path directory, StreamSerializer streamSerializer) {
        Objects.requireNonNull(directory, "Storage directory cannot be null");
        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException("Input storage " + directory + " is not directory");
        }
        if (!Files.isReadable(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException("Storage directory " + directory + " is not readable/writable");
        }
        this.directory = directory;
        this.streamSerializer = streamSerializer;
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return streamSerializer.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path read error", null, e);
        }
    }

    @Override
    protected void doSave(Resume resume, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Could not create file with path " + path, null, e);
        }
        doUpdate(resume, path);
    }

    @Override
    protected void doUpdate(Resume resume, Path path) {
        try {
            streamSerializer.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path write error", resume.getUuid(), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Delete by path error", path.toString(), e);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected List<Resume> getCopyAll() {
            return getPathStream().map(this::doGet).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        getPathStream().forEach(this::doDelete);
    }

    @Override
    public int size() {
        return (int) getPathStream().count();
    }

    private Stream<Path> getPathStream() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Path storage reading error", e);
        }
    }
}
