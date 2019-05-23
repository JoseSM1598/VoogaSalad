package xml;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import renderer.external.RenderSystem;

/**
 * Generates a new xml data file from some data being saved through the game editor
 * @author ak457
 */

public class XMLSaveBuilder implements Saver {
    private Document saveDocument;

    /**
     * Constructor that will automatically generate a save file
     * @param structure A HashMap where the keys are the tags being created in the save file, and the values are lists of the attributes each tag will have.
     * @param data A HashMap where the keys are the name of the attributes, and the values are the lists of the values each attribute will store.
     * @param file The file that will be created
     */
    public XMLSaveBuilder(HashMap<String, ArrayList<String>> structure, HashMap<String, ArrayList<String>> data, File file) {
        try {
            boolean isValid = determineMapParameterValidity(structure, data);
            if(!isValid) {
                throw new IOException("Invalid map parameters");
            }
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbf.newDocumentBuilder();
            saveDocument = dBuilder.newDocument();
            if (file != null) {
                Element root = saveDocument.createElement("VOOGASalad");
                saveDocument.appendChild(root);
                Attr authorAttribute = saveDocument.createAttribute("author");
                authorAttribute.setValue("yeet");
                root.setAttributeNode(authorAttribute);
                createElementList(root, structure, data);
                generateFile(file);
            } else {
                throw new IOException("Cannot determine new file");
            }
        } catch (ParserConfigurationException | IOException e) {
            System.out.println("Cannot initialize save file");
            e.printStackTrace();
        }
    }

    private void createElementList(Element root, HashMap<String, ArrayList<String>> structure, HashMap<String, ArrayList<String>> data) {
        for(String elementTag : structure.keySet()) {
            ArrayList<String> attributeList = structure.get(elementTag);
            int size = 0;
            for(String s : attributeList) {
                if(data.get(s).size() > size) {
                    size = data.get(s).size();
                }
            }
            for(int i = 0; i < size; i++) {
                Element tag = saveDocument.createElement(elementTag);
                root.appendChild(tag);
                for(String attributeTag : structure.get(elementTag)) {
                    Attr tagAttribute = saveDocument.createAttribute(attributeTag);
                    if(data.containsKey(attributeTag)) {
                        tagAttribute.setValue(data.get(attributeTag).get(i));
                    }
                    tag.setAttributeNode(tagAttribute);
                }
            }
        }
    }

    public void generateFile(File file) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(saveDocument);
            StreamResult streamResult = new StreamResult(file);
            transformer.transform(domSource, streamResult);
        } catch (TransformerConfigurationException e) {
            System.out.println("Cannot create save file");
        } catch (TransformerException f) {
            System.out.println("Cannot create save file");
        }
    }

    private boolean determineMapParameterValidity(HashMap<String, ArrayList<String>> structure, HashMap<String, ArrayList<String>> data) {
        int attributes = 0;
        for(String s: structure.keySet()) {
            attributes += structure.get(s).size();
        }
        return (attributes == data.keySet().size());
    }
}