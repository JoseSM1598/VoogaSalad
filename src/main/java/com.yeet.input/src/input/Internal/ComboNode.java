package input.Internal;

import java.util.ArrayList;
import java.util.List;

public class ComboNode implements Node {

    List<Node> myChildren;
    //Keeping a list of the string values to make it easier to look up values
    List<String> myChildrenStrings;
    String tag;

    public ComboNode(String letter){
        tag = letter;
        myChildren = new ArrayList<>();
        myChildrenStrings = new ArrayList<>();
    }

    @Override
    public String getKey(){
        return tag;
    }

    @Override
    public void addChild(Node n){
        myChildren.add(n);
        myChildrenStrings.add(n.getKey());
    }

    @Override
    public List<Node> getChildren(){
        return myChildren;
    }

    @Override
    public boolean hasChild(String s){
        return myChildrenStrings.contains(s);
    }

    @Override
    public Node getChild(String s){
        int index = myChildrenStrings.indexOf(s); //get index from string arraylist
        return myChildren.get(index);
    }

    @Override
    public boolean isAtEnd(){
        for(Node child:myChildren){
            if(child instanceof LeafNode){
                return true;
            }
        }
        return false;
    }
}
