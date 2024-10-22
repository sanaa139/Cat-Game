package entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class LoadImagesManager {
    private Image left1, left2, left3, left4, left_inactive, right1, right2, right3, right4, right_inactive;
    LoadImagesManager(String entityName){
        try{
            left1 = ImageIO.read(getClass().getResourceAsStream("/entity/" + entityName + "/left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/entity/" + entityName + "/left2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/entity/" + entityName + "/left3.png"));
            left4 = ImageIO.read(getClass().getResourceAsStream("/entity/" + entityName + "/left4.png"));
            left_inactive = ImageIO.read(getClass().getResourceAsStream("/entity/" + entityName + "/left_inactive.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/entity/" + entityName + "/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/entity/" + entityName + "/right2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/entity/" + entityName + "/right3.png"));
            right4 = ImageIO.read(getClass().getResourceAsStream("/entity/" + entityName + "/right4.png"));
            right_inactive = ImageIO.read(getClass().getResourceAsStream("/entity/" + entityName + "/right_inactive.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public Image getLeft1(){
        return left1;
    }

    public Image getLeft2(){
        return left2;
    }
    public Image getLeft3(){
        return left3;
    }
    public Image getLeft4(){
        return left4;
    }

    public Image getLeftInactive(){
        return left_inactive;
    }

    public Image getRight1(){
        return right1;
    }

    public Image getRight2(){
        return right2;
    }
    public Image getRight3(){
        return right3;
    }
    public Image getRight4(){
        return right4;
    }

    public Image getRightInactive(){
        return right_inactive;
    }
}
