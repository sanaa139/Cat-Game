package entity;

import tiles.Vector;

public class HitBox {
    private Vector leftWallLine, rightWallLine, upperWallLine, lowerWallLine;
    public HitBox(){}

    public Vector getLeftWallLine() {
        return leftWallLine;
    }

    public void setLeftWallLine(Vector leftWallLine) {
        this.leftWallLine = leftWallLine;
    }

    public Vector getRightWallLine() {
        return rightWallLine;
    }

    public void setRightWallLine(Vector rightWallLine) {
        this.rightWallLine = rightWallLine;
    }

    public Vector getUpperWallLine() {
        return upperWallLine;
    }

    public void setUpperWallLine(Vector upperWallLine) {
        this.upperWallLine = upperWallLine;
    }

    public Vector getLowerWallLine() {
        return lowerWallLine;
    }

    public void setLowerWallLine(Vector lowerWallLine) {
        this.lowerWallLine = lowerWallLine;
    }
}
