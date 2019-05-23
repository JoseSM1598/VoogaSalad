package main;
/**
 * This class stores constant values.
 * To use, declare a MainConstant in the desired class (e.g. private MainConstant myMC;)
 * Then, where the value is needed, call the desired enum and use the getValue() method (e.g. myMC.STAGEWIDTH.getValue())
 * @author ak457
 */
public enum MainConstant {
    STAGEWIDTH(1280),
    STAGEHEIGHT(800);

    private double myValue;

    MainConstant(double value) {
        myValue = value;
    }

    public double getValue() {
        return myValue;
    }
}
