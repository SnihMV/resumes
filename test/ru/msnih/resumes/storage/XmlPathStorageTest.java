package ru.msnih.resumes.storage;

import ru.msnih.resumes.storage.serialization.XmlStreamSerializer;

import java.nio.file.Path;

class XmlPathStorageTest extends AbstractStorageTest {

    public XmlPathStorageTest() {
        super(new PathStorageStrategy(Path.of(STORAGE_DIR), new XmlStreamSerializer()));
    }
}