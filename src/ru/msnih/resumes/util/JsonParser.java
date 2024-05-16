package ru.msnih.resumes.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.msnih.resumes.model.Section;

import java.io.Reader;
import java.io.Writer;
import java.time.LocalDate;

public class JsonParser {
    private static Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Section.class, new GsonSectionAdapter())
            .registerTypeAdapter(LocalDate.class, new GsonLocalDateAdapter())
            .create();

    public static <T> void write(T object, Writer writer) {
        GSON.toJson(object,writer);
    }

    public static <T> String write (T object, Class<T> clazz){
        return GSON.toJson(object, clazz);
    }

    public static <T> T read(Reader reader, Class<T> klass) {
        return GSON.fromJson(reader, klass);
    }

    public static <T> T read(String content, Class<T> clazz){
        return GSON.fromJson(content, clazz);
    }

}
