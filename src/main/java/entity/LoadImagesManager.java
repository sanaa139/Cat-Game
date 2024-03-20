package entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class LoadImagesManager {
    private Image left1, left2, left_inactive, right1, right2, right_inactive;
    LoadImagesManager(String entity){
        try{
            left1 = ImageIO.read(getClass().getResourceAsStream("/" + entity + "/left1.jpg"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/" + entity + "/left2.jpg"));
            left_inactive = ImageIO.read(getClass().getResourceAsStream("/" + entity + "/left_inactive.jpg"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/" + entity + "/right1.jpg"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/" + entity + "/right2.jpg"));
            right_inactive = ImageIO.read(getClass().getResourceAsStream("/" + entity + "/right_inactive.jpg"));
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

    public Image getLeftInactive(){
        return left_inactive;
    }

    public Image getRight1(){
        return right1;
    }

    public Image getRight2(){
        return right2;
    }

    public Image getRightInactive(){
        return right_inactive;
    }

}
