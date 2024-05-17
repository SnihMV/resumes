package ru.msnih.resumes.storage;

import ru.msnih.resumes.storage.serialization.JsonStreamSerializer;

class JsonPathStorageTest extends AbstractStorageTest {

    public JsonPathStorageTest() {
        super(new PathStorageStrategy(STORAGE_DIR, new JsonStreamSerializer()));
    }
}