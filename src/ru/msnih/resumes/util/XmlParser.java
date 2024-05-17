package ru.msnih.resumes.util;

import ru.msnih.resumes.exception.StorageException;
import ru.msnih.resumes.model.Resume;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.Reader;
import java.io.Writer;

public class XmlParser {

    private final Marshaller marshaller;
    private final Unmarshaller unmarshaller;

    public XmlParser(Class... classesToBound) {
        try {
            JAXBContext ctx = JAXBContext.newInstance(classesToBound);
            marshaller = ctx.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            unmarshaller = ctx.createUnmarshaller();
        } catch (JAXBException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Resume unmarshall(Reader reader){
        try {
            return (Resume) unmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            throw new StorageException("XML unmarshalling error!",e);
        }
    }

    public void marshall(Object object, Writer writer) {
        try {
            marshaller.marshal(object,writer);
        } catch (JAXBException e) {
            throw new StorageException("XML marshalling error!", e);
        }
    }
}
