package com.github.Frenadol.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XMLManager {

    /**
     * Write an object to an XML file.
     * @param <T>       The type of the object.
     * @param object    The object to write.
     * @param filename  The name of the file to write to.
     * @return True if writing was successful, false otherwise.
     */
    public static <T> boolean writeXML(T c, String filename) {
        boolean result = false;
        JAXBContext context;
        try {
            context = JAXBContext.newInstance(c.getClass());
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            m.marshal(c, new File(filename));
            result = true;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * Read an object from an XML file.
     * @param <T>       The type of the object.
     * @param object    The object to read into.
     * @param filename  The name of the file to read from.
     * @return The object read from the XML file.
     */
    public static <T> T readXML(T c, String filename) {
        T result = c;
        JAXBContext context;

        try {
            context = JAXBContext.newInstance(c.getClass());
            Unmarshaller um = context.createUnmarshaller();
            result = (T) um.unmarshal(new File(filename));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return result;
    }
}
