package ru.msnih.resumes.util;

import com.google.gson.*;
import ru.msnih.resumes.model.Organization;

import java.lang.reflect.Type;

public class JsonOrganizationAdapter implements JsonDeserializer<Organization>, JsonSerializer<Organization> {
    @Override
    public JsonElement serialize(Organization organization, Type type, JsonSerializationContext jsonSerializationContext) {
        return null;
    }

    @Override
    public Organization deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return null;
    }
}
