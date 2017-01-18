package me.leorblx.betasrv.utils;

import me.leorblx.betasrv.event.jaxb.NamespaceMapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class XmlUtils
{
    public static String getFromFile(String file)
    {
        try {
            return new String(
                    Files.readAllBytes(Paths.get(file)),
                    StandardCharsets.UTF_8
            );
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static <T> String marshal(T obj)
    {
        StringWriter stringWriter = new StringWriter();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            jaxbMarshaller.setProperty("com.sun.xml.internal.bind.namespacePrefixMapper", new NamespaceMapper());
            jaxbMarshaller.marshal(obj, stringWriter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringWriter.toString();
    }

    public static <T> T unmarshal(String xml, Class<T> tClass)
    {
        T objTmp = null;
        try {
            StringReader reader = new StringReader(xml);
            JAXBContext jaxbContext = JAXBContext.newInstance(tClass);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            XMLStreamReader xsr = XMLInputFactory.newFactory().createXMLStreamReader(reader);
            XMLReaderWithoutNamespace xr = new XMLReaderWithoutNamespace(xsr);
            objTmp = (T) jaxbUnmarshaller.unmarshal(xr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objTmp;
    }
}
