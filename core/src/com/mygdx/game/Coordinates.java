package com.mygdx.game;

public class Coordinates {
    private float xPosition;
    private float yPosition;

    public float getxPosition() {
        return xPosition;
    }

    public void setxPosition(float xPosition) {
        this.xPosition = xPosition;
    }

    public float getyPosition() {
        return yPosition;
    }

    public void setyPosition(float yPosition) {
        this.yPosition = yPosition;
    }

    public Coordinates() {
        this.xPosition = 10;
        this.yPosition = 10;
    }
}
