package input.Internal;

import java.util.List;

/**
 * interface for Node classes. Implemented by comboNode
 * @author Jose San Martin
 */
public interface Node {

    String getKey();

    void addChild(Node node);

    List<Node> getChildren();

    boolean hasChild(String s);

    Node getChild(String s);

    boolean isAtEnd();

}
