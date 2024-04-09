package ru.msnih.resumes.util;

import com.google.gson.*;
import ru.msnih.resumes.exception.StorageException;
import ru.msnih.resumes.model.Section;

import java.lang.reflect.Type;

public class GsonSectionAdapter implements JsonSerializer<Section>, JsonDeserializer<Section> {

    private static final String CLASSNAME = "CLASSNAME";
    private static final String INSTANCE = "INSTANCE";
    @Override
    public JsonElement serialize(Section section, Type type, JsonSerializationContext context) {
        JsonObject res = new JsonObject();
        JsonElement element = context.serialize(section);
        res.addProperty(CLASSNAME, section.getClass().getName());
        res.add(INSTANCE, element);
        return res;
    }

    @Override
    public Section deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = element.getAsJsonObject();
        JsonPrimitive value = object.getAsJsonPrimitive(CLASSNAME);
        String className = value.getAsString();
        try {
            Class klass = Class.forName(className);
            return context.deserialize(object.get(INSTANCE), klass);
        } catch (ClassNotFoundException e) {
            throw new StorageException("Section JSON Deserialization error!", e);
        }
    }
}
