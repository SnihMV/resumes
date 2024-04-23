package ru.msnih.resumes.storage;

import ru.msnih.resumes.storage.serialization.ObjectStreamSerializer;

import java.io.File;

class ObjectFileStorageTest extends AbstractStorageTest {
    public ObjectFileStorageTest() {
        super(new FileStorageStrategy(new File(STORAGE_DIR_PATH), new ObjectStreamSerializer()));
    }
}