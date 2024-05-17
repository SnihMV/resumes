package ru.msnih.resumes.storage;

import ru.msnih.resumes.storage.serialization.ObjectStreamSerializer;

class ObjectFileStorageTest extends AbstractStorageTest {
    public ObjectFileStorageTest() {
        super(new FileStorageStrategy(STORAGE_DIR.toFile(), new ObjectStreamSerializer()));
    }
}