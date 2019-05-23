package xml;

import org.w3c.dom.Document;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Parser interface is intended for any file parser.
 * The methods encapsulated are used to extract the necessary information using the parser.
 * @author ak457
 */
public interface Parser {
    /**
     * Parse the file for the information contained within a certain element
     * @param element represents some category or object that file encapsulates
     * @return a HashMap containing the wanted information.
     * The keys should be the name of the properties the file stores.
     * The values should be the information the properties store.
     */
    HashMap parseFileForElement(String element);
    /**
     * Parse the file for the information contained within a certain attribute
     * @param element represents the element the attribute falls under
     * @param attribute represents the attribute to parse for
     * @return an ArrayList containing the wanted information.
     */
    ArrayList parseFileForAttribute(String element, String attribute);
    /**
     * Parse the file for the information contained within a certain element, but with the values as the keys
     * @param element represents some category or object that file encapsulates
     * @return a HashMap containing the wanted information.
     * The keys of the HashMap will be all of the values that the properties store
     * The values of the HashMap should be the name of the properties the file stores.
     */
    HashMap parseFileForValueMap(String element);
    /**
     * Create a new Document to work from when parsing a file
     * @return A Document that encapsulates all of the file information
     */
    Document createDocument(File file);
}
