package editor;

/**
 * This class stores constant values.
 * To use, declare an EditorConstant in the desired class (e.g. private EditorConstant myEC;)
 * Then, where the value is needed, call the desired enum and use the getValue() method (e.g. myEC.BACKBUTTONXPOSITION.getValue())
 * @author ak457
 */
public enum EditorConstant {
    BACKBUTTONXPOSITION(1000);

    private double myValue;

    EditorConstant(double value) {
        myValue = value;
    }

    public double getValue() {
        return myValue;
    }
}
