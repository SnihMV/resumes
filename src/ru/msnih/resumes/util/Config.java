package ru.msnih.resumes.util;

import ru.msnih.resumes.storage.Storage;
import ru.msnih.resumes.storage.SqlStorage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Config {
    private static final Config INSTANCE = new Config();
    private static final String PROPS_PATH = "C:\\workspace\\resumes\\Resumes\\config\\config.properties";
    private final String storageDirPath;
    private final Storage storage;

    private Config() {
        try (InputStream input = Files.newInputStream(Path.of(PROPS_PATH))) {
            final Properties props = new Properties();
            props.load(input);
            storageDirPath = props.getProperty("storage.dir");
            storage = new SqlStorage(props.getProperty("db.url"),props.getProperty("db.user"),props.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid property file: " + PROPS_PATH);
        }
    }

    public String getStorageDirPath() {
        return storageDirPath;
    }

    public Storage getStorage() {
        return storage;
    }
    public static Config getInstance() {
        return INSTANCE;
    }
}
