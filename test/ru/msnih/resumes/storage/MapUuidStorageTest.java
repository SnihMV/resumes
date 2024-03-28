package ru.msnih.resumes.storage;

import static org.junit.jupiter.api.Assertions.*;

class MapUuidStorageTest extends AbstractStorageTest {
    public MapUuidStorageTest() {
        super(new MapUuidStorage());
    }
}