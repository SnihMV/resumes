package ru.msnih.resumes.util;

import ru.msnih.resumes.storage.SqlStorage;
import ru.msnih.resumes.storage.Storage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Config {
    private static final Config INSTANCE = new Config();
    private static final String PROPS_PATH = "C:\\workspace\\resumes\\Resumes\\config\\config.properties";
    private final Path storageDir;
    private final Storage storage;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Config() {
        try (InputStream input = Files.newInputStream(Path.of(PROPS_PATH))) {
            final Properties props = new Properties();
            props.load(input);
            String storageDirPath = props.getProperty("storage.dir");
            storageDir = createStorageDir(storageDirPath);
            storage = new SqlStorage(props.getProperty("db.url"),props.getProperty("db.user"),props.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid property file: " + PROPS_PATH);
        }
    }

    public Path getStorageDir() {
        return storageDir;
    }

    public Storage getStorage() {
        return storage;
    }
    public static Config getInstance() {
        return INSTANCE;
    }

    private Path createStorageDir(String storageDirPath) {
        Path storageDir = Path.of(storageDirPath);
        if (!Files.exists(storageDir)) {
            try {
                Files.createDirectory(storageDir);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (!Files.isDirectory(storageDir)) {
            throw new IllegalStateException("File "+ storageDirPath +" is not a directory");
        }
        return storageDir;
    }
}
