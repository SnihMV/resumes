package ru.msnih.resumes.storage;

import ru.msnih.resumes.storage.serialization.ObjectStreamSerializer;

import java.nio.file.Path;

class ObjectPathStorageTest extends AbstractStorageTest {

    public ObjectPathStorageTest() {
        super(new PathStorage(Path.of(STORAGE_DIR), new ObjectStreamSerializer()));
    }
}