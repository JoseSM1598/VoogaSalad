package editor;

import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimationInfo {
    private int currentFrame;
    private int totalFrames;
    private String name;
    private double attackPower;
    private Map<Integer, Rectangle> hitBoxes;
    private Map<Integer, Rectangle> hurtBoxes;
    private List<String> input;

    public AnimationInfo(int total, List<String> inputString, double power, String name) {
        input = inputString;
        currentFrame = -1;
        totalFrames = total;
        hitBoxes = new HashMap<>();
        hurtBoxes = new HashMap<>();
        for (int i = 1; i <= totalFrames; i++){
            hitBoxes.putIfAbsent(i, new Rectangle());
            hurtBoxes.putIfAbsent(i, new Rectangle());
        }
        attackPower = power;
        this.name = name;
    }

    public void setCurrentFrame(int f){
        if (f > totalFrames){
            System.out.println("Frame set out of range");
            return;
        }
        currentFrame = f;
    }
    public int getCurrentFrame(){return currentFrame;}
    public void setHitBox(Rectangle r){
        hitBoxes.put(currentFrame, r);
    }
    public void setHurtBox(Rectangle r){
        hurtBoxes.put(currentFrame, r);
    }
    public Rectangle getHitBox(){
        return hitBoxes.get(currentFrame);
    }
    public Rectangle getHurtBox(){
        return hurtBoxes.get(currentFrame);
    }
    public void advance(int add){
        currentFrame = Math.floorMod((currentFrame + add),totalFrames);
        if (currentFrame == 0){
            currentFrame = totalFrames;
        }
    }
    public String toString(){
        if (currentFrame == -1 || totalFrames == -1){
            return "Animation Not Set";
        }
        String ret = "Frame "+currentFrame+"/"+totalFrames + "\ninput: ";
        return ret + getInputAsString();

    }
    public String getInputAsString(){
        String ret = "";
        for (int i = 0 ; i < input.size(); i++){
            if (i < input.size() - 1){
                ret += input.get(i)+"-";
            }
            else{
                ret += input.get(i);
            }
        }
        return ret;
    }
    public void setInput(List<String> in){ input = in;}
    public List<String> getInput(){return input;}

    public double getAttackPower(){return attackPower;}
    public int getTotalFrames(){return totalFrames;}

    public void setName(String n){name = n;}
    public String getName(){return name;}
}