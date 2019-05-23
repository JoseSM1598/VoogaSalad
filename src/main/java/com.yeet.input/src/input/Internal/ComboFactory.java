package input.Internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ComboFactory {

    private List<Map<String, String>> myCombos;
    //private Node Tree;
    private int counter;
    public ComboFactory(List<Map<String, String>> combos){
        myCombos = combos;
        //Tree = new ComboNode("START");
        counter = 0;

    }


    /**
     * Creates a tree of all possible combos
     * Idea: The keys of the
     * @return
     */
    public List<Node> createTree(){
        List<Node> TreeList = new ArrayList<>();
        for( var playerCombos: myCombos){
            Node Tree = new ComboNode("START");
            for(String combo : playerCombos.keySet()){
                var comboList = new ArrayList<>(Arrays.asList(combo.split("")));
                addChildLoop(Tree, comboList, combo);
            }
            //Add our player specific combo tree to our TreeList, and raise the counter by 1
            TreeList.add(Tree);
            counter+=1;
        }
        return TreeList;
    }


    private void addChildLoop(Node root, List<String> comboList, String combo){
        if(comboList.size() == 0){
            root.addChild(new LeafNode(myCombos.get(counter).get(combo)));
            //System.out.println(root.getChildren().get(0).getKey());
            return;
        }
        Node newChild;
        String let = comboList.remove(0);

        if(!root.hasChild(let)){ // so long as that child doesn't already exist
            newChild = new ComboNode(let);
            root.addChild(newChild);
        }
        else{
            newChild = root.getChild(let);
        }
        //System.out.println(newChild.getKey());
        addChildLoop(newChild, comboList, combo);
    }
}
