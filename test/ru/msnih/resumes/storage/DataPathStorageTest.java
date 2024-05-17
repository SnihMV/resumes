package ru.msnih.resumes.storage;

import ru.msnih.resumes.storage.serialization.DataStreamSerializer;

class DataPathStorageTest extends AbstractStorageTest {

    public DataPathStorageTest() {
        super(new PathStorageStrategy(STORAGE_DIR, new DataStreamSerializer()));
    }
}