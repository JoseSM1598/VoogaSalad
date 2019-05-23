package input.Internal;

import java.util.List;

public class LeafNode implements Node {

    private String comboName;

    public LeafNode(String combo){
        comboName = combo;
    }


    public String geCombo(){
        return comboName;
    }

    @Override
    public String getKey() {
        return comboName;
    }

    @Override
    public void addChild(Node node) {

    }

    @Override
    public List<Node> getChildren() {
        return null;
    }

    @Override
    public boolean hasChild(String s) {
        return false;
    }

    @Override
    public Node getChild(String s) {
        return null;
    }

    @Override
    public boolean isAtEnd() {
        return false;
    }
}
