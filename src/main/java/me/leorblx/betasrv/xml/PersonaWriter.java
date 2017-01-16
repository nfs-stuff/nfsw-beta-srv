package me.leorblx.betasrv.xml;

import com.googlecode.htmlcompressor.compressor.XmlCompressor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class PersonaWriter
{
    public Long getCash(String personaId)
    {
        try {
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = docBuilder.parse("www/nfsw/Engine.svc/DriverPersona/GetPersonaInfo_" + personaId + ".xml");

            return Long.parseLong(doc.getElementsByTagName("cash").item(0).getTextContent());
        } catch (ParserConfigurationException | IOException | SAXException pce) {
            pce.printStackTrace();
        }
        
        return 0L;
    }

    public void setCash(String cash, String personaId)
    {
        try {
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = docBuilder.parse("www/nfsw/Engine.svc/DriverPersona/GetPersonaInfo_" + personaId + ".xml");

            doc.getElementsByTagName("cash").item(0).setTextContent(cash);

//            doc.getElementsByTagName("DefaultOwnedCarIndex").item(0).setTextContent(carId);
//            Node OwnedCar = doc.getElementsByTagName("OwnedCarTrans").item(Integer.valueOf(carId).intValue());
//            DOMImplementationLS lsImpl = (DOMImplementationLS) OwnedCar.getOwnerDocument().getImplementation().getFeature("LS", "3.0");
//            LSSerializer serializer = lsImpl.createLSSerializer();
//            serializer.getDomConfig().setParameter("xml-declaration", Boolean.valueOf(false));
//            String StringOwnedCar = serializer.writeToString(OwnedCar);
//            WriteTempCar(StringOwnedCar, personaId);
//
//            writeXML(doc, "www/nfsw/Engine.svc/personas/" + personaId + "/carslots.xml");

            writeXML(doc, "www/nfsw/Engine.svc/DriverPersona/GetPersonaInfo_" + personaId + ".xml");

            int idx = (personaId.equals("100") ? 0 : (personaId.equals("200") ? 1 : 2));

            Document permanentSession = docBuilder.parse("www/nfsw/Engine.svc/User/GetPermanentSession.xml");

            Node personas = permanentSession.getElementsByTagName("personas").item(0);
            Node persona = personas.getChildNodes().item(idx);

            ((Element) persona).getElementsByTagName("cash").item(0).setTextContent(cash);

            writeXML(permanentSession, "www/nfsw/Engine.svc/User/GetPermanentSession.xml");
        } catch (ParserConfigurationException | IOException | SAXException pce) {
            pce.printStackTrace();
        }
    }

    public int countInstances(String dataString, String toCount, String toFind)
    {
        int maxIndex = dataString.indexOf(toFind);
        int currentIndex = 0;
        int occurrences = 0;
        while (currentIndex < maxIndex) {
            currentIndex = dataString.indexOf(toCount, currentIndex) + toCount.length();
            if ((currentIndex >= maxIndex) || (currentIndex == -1 + toCount.length())) {
                break;
            }
            occurrences++;
        }
        return occurrences;
    }

    public void writeXML(Document doc, String location)
    {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty("omit-xml-declaration", "yes");
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            DOMSource source = new DOMSource(doc);
            transformer.transform(source, result);

            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(location).getAbsoluteFile()));
            bw.write(new XmlCompressor().compress(sw.toString()));
            bw.close();
        } catch (TransformerFactoryConfigurationError | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }
}

