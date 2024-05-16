package ru.msnih.resumes.util;

import org.junit.jupiter.api.Test;
import ru.msnih.resumes.model.Resume;
import ru.msnih.resumes.model.Section;
import ru.msnih.resumes.model.SectionType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.msnih.resumes.TestData.R1;

class JsonParserTest  {

    @Test
    void testResume() {
        String json = JsonParser.write(R1, Resume.class);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        assertEquals(R1, resume);
    }

    @Test
    void testSection() {
        Section s1 = R1.getSection(SectionType.QUALIFICATION);
        String json = JsonParser.write(s1, Section.class);
        System.out.println(json);
        Section s2 = JsonParser.read(json, Section.class);
        assertEquals(s1, s2);
    }
}