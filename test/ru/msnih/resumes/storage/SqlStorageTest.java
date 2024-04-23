package ru.msnih.resumes.storage;

import ru.msnih.resumes.util.Config;

class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(Config.getInstance().getStorage());
    }
}