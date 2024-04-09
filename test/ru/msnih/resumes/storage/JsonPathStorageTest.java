package ru.msnih.resumes.storage;

import ru.msnih.resumes.storage.serialization.JsonStreamSerializer;
import ru.msnih.resumes.storage.serialization.XmlStreamSerializer;

import java.nio.file.Path;

class JsonPathStorageTest extends AbstractStorageTest {

    public JsonPathStorageTest() {
        super(new PathStorageStrategy(Path.of(STORAGE_DIR), new JsonStreamSerializer()));
    }
}