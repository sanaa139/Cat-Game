package entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class LoadImagesManager {
    private Image left1, left2, left3, left4, left_inactive, left_inactive2, right1, right2, right3, right4, right_inactive, right_inactive2;
    LoadImagesManager(String entity){
        try{
            left1 = ImageIO.read(getClass().getResourceAsStream("/" + entity + "/left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/" + entity + "/left2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/" + entity + "/left3.png"));
            left4 = ImageIO.read(getClass().getResourceAsStream("/" + entity + "/left4.png"));
            left_inactive = ImageIO.read(getClass().getResourceAsStream("/" + entity + "/left_inactive.png"));
            left_inactive2 = ImageIO.read(getClass().getResourceAsStream("/" + entity + "/left_inactive2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/" + entity + "/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/" + entity + "/right2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/" + entity + "/right3.png"));
            right4 = ImageIO.read(getClass().getResourceAsStream("/" + entity + "/right4.png"));
            right_inactive = ImageIO.read(getClass().getResourceAsStream("/" + entity + "/right_inactive.png"));
            right_inactive2 = ImageIO.read(getClass().getResourceAsStream("/" + entity + "/right_inactive2.png"));
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

    public Image getLeftInactive2(){
        return left_inactive2;
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
    public Image getRightInactive2(){
        return right_inactive2;
    }

}
