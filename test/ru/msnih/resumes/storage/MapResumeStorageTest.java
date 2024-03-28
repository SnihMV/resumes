package ru.msnih.resumes.storage;

import static org.junit.jupiter.api.Assertions.*;

class MapResumeStorageTest extends AbstractStorageTest {
    public MapResumeStorageTest() {
        super(new MapResumeStorage());
    }
}