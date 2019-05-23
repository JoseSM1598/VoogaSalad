package messenger.external;

public class ActionEvent extends Event {

    private String name;
    private String attackType;

    public ActionEvent(String actionName, String type){

        name = actionName;
        attackType = type;
    }

    @Override
    public String getName() {
        return "Action Event: "+name;
    }

    public String getType(){
        return attackType;
    }
}
