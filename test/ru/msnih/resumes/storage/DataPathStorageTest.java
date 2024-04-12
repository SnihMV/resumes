package ru.msnih.resumes.storage;

import ru.msnih.resumes.storage.serialization.DataStreamSerializer;
import ru.msnih.resumes.storage.serialization.ObjectStreamSerializer;

import java.nio.file.Path;

class DataPathStorageTest extends AbstractStorageTest {

    public DataPathStorageTest() {
        super(new PathStorageStrategy(Path.of(STORAGE_DIR), new DataStreamSerializer()));
    }
}