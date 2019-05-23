package xml;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class parses an xml file for the information wanted by a user.
 * There is a validation check for a VOOGASalad tag in the top-level nodes of the xml file.
 * @author ak457
 */
public class XMLParser implements Parser {

    private Document xmlDocument;

    public XMLParser(File file) {
        try {
            xmlDocument = createDocument(file);
            if(xmlDocument == null) {
                throw new IOException("Invalid file");
            }
            boolean verified = determineFileValidity();
            if(!verified) {
                throw new IOException("No VOOGASalad tag");
            }
        } catch (IOException e) {
            System.out.println("An error during initialization");
        }
    }

    public HashMap<String, ArrayList<String>> parseFileForElement(String element) {
        HashMap<String, ArrayList<String>> attributeMap = new HashMap<>();
        NodeList elemNodes = xmlDocument.getElementsByTagName(element);
        for(int i = 0; i < elemNodes.getLength(); i++) {
            Element elem = (Element) elemNodes.item(i);
            NamedNodeMap elemAttributes = elem.getAttributes();
            for (int j = 0; j < elemAttributes.getLength(); j++) {
                Node attributeNode = elemAttributes.item(j);
                if(attributeNode instanceof Attr) {
                Attr attribute = (Attr) attributeNode;
                attributeMap.putIfAbsent(attribute.getName(), new ArrayList<>());
                attributeMap.get(attribute.getName()).add(attribute.getValue());
                }
            }
        }
        return attributeMap;
    }

    public ArrayList<String> parseFileForAttribute(String element, String attributeTag) {
        HashMap<String, ArrayList<String>> attributeMap = parseFileForElement(element);
        if(attributeMap.containsKey(attributeTag)) {
            return attributeMap.get(attributeTag);
        } else {
            return new ArrayList<>();
        }
    }

    public HashMap<String, String> parseFileForValueMap(String element) {
        HashMap<String, ArrayList<String>> attributeMap = parseFileForElement(element);
        HashMap<String, String> valueMap = new HashMap<>();
        for(String attribute : attributeMap.keySet()) {
            for(String value : attributeMap.get(attribute)) {
                valueMap.put(value, attribute);
            }
        }
        return valueMap;
    }

    private boolean determineFileValidity() {
        boolean verified = false;
        NodeList docNodes = xmlDocument.getChildNodes();
        for(int i = 0; i < docNodes.getLength(); i++) {
            if(docNodes.item(i) instanceof Element) {
                Element elem = (Element) docNodes.item(i);
                if(elem.getTagName().equals("VOOGASalad")) {
                    verified = true;
                }
            }
        }
        return verified;
    }

    public Document createDocument(File file) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbf.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            return doc;
        }
        catch (ParserConfigurationException | SAXException | IOException | IllegalArgumentException e) {
            System.out.println("An error occurred in creating the document.");
            return null;
        }
    }
}