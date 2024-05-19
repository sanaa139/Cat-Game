package entity;

import tiles.Vector;

public class HitBox {
    private Vector leftWallLine, rightWallLine, upperWallLine, lowerWallLine;
    public HitBox(){}

    public HitBox(Vector leftWallLine, Vector rightWallLine, Vector upperWallLine, Vector lowerWallLine){
        this.leftWallLine = leftWallLine;
        this.rightWallLine = rightWallLine;
        this.upperWallLine = upperWallLine;
        this.lowerWallLine = lowerWallLine;
    }

    public Vector getLeftWallLine(){
        return leftWallLine;
    }
    public Vector getRightWallLine(){
        return rightWallLine;
    }
    public Vector getUpperWallLine(){
        return upperWallLine;
    }
    public Vector getLowerWallLine(){
        return lowerWallLine;
    }

    public void setLeftWallLine(Vector leftWallLine){
        this.leftWallLine = leftWallLine;
    }

    public void setRightWallLine(Vector rightWallLine){
        this.rightWallLine = rightWallLine;
    }

    public void setUpperWallLine(Vector upperWallLine){
        this.upperWallLine = upperWallLine;
    }

    public void setLowerWallLine(Vector lowerWallLine){
        this.lowerWallLine = lowerWallLine;
    }
}
