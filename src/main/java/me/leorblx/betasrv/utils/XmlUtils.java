package me.leorblx.betasrv.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.StringReader;
import java.io.StringWriter;

public class XmlUtils
{
    public static <T> String marshal(T obj)
    {
        StringWriter stringWriter = new StringWriter();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
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
