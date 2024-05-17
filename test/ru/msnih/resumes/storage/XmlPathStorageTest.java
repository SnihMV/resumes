package ru.msnih.resumes.storage;

import ru.msnih.resumes.storage.serialization.XmlStreamSerializer;

class XmlPathStorageTest extends AbstractStorageTest {

    public XmlPathStorageTest() {
        super(new PathStorageStrategy(STORAGE_DIR, new XmlStreamSerializer()));
    }
}