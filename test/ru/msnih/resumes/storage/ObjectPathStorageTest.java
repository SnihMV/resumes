package ru.msnih.resumes.storage;

import ru.msnih.resumes.storage.serialization.ObjectStreamSerializer;

class ObjectPathStorageTest extends AbstractStorageTest {

    public ObjectPathStorageTest() {
        super(new PathStorageStrategy(STORAGE_DIR, new ObjectStreamSerializer()));
    }
}