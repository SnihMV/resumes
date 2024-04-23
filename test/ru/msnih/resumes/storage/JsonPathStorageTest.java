package ru.msnih.resumes.storage;

import ru.msnih.resumes.storage.serialization.JsonStreamSerializer;

import java.nio.file.Path;

class JsonPathStorageTest extends AbstractStorageTest {

    public JsonPathStorageTest() {
        super(new PathStorageStrategy(Path.of(STORAGE_DIR_PATH), new JsonStreamSerializer()));
    }
}