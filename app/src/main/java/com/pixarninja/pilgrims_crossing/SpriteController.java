package com.pixarninja.pilgrims_crossing;

public class SpriteController {

    private SpriteEntity entity;
    private String transition;
    private double xInit;
    private double yInit;
    private double xPos;
    private double yPos;
    private volatile double xDelta;
    private volatile double yDelta;
    private int frameLengthInMilliseconds = 35;
    private long lastFrameChangeTime;
    private boolean reacting;
    private boolean begin;
    private boolean end;


    public SpriteEntity getEntity() { return this.entity; }
    public void setEntity(SpriteEntity entity) { this.entity = entity; }

    public String getTransition() { return this.transition; }
    public void setTransition(String transition) { this.transition = transition; }

    public double getXInit() { return this.xInit; }
    public void setXInit(double xInit) { this.xInit = xInit; }

    public double getYInit() { return this.yInit; }
    public void setYInit(double yInit) { this.yInit = yInit; }

    public double getXPos() { return this.xPos; }
    public void setXPos(double xPos) { this.xPos = xPos; }

    public double getYPos() { return this.yPos; }
    public void setYPos(double yPos) { this.yPos = yPos; }

    public double getXDelta() {
        return this.xDelta;
    }
    public void setXDelta(double xDelta) {
        this.xDelta = xDelta;
    }

    public double getYDelta() {
        return this.yDelta;
    }
    public void setYDelta(double yDelta) {
        this.yDelta = yDelta;
    }

    public int getFrameRate() { return this.frameLengthInMilliseconds; }
    public void setFrameRate(int frameLengthInMilliseconds) { this.frameLengthInMilliseconds = frameLengthInMilliseconds; }

    public long getLastFrameChangeTime() { return this.lastFrameChangeTime; }
    public void setLastFrameChangeTime(long lastFrameChangeTime) { this.lastFrameChangeTime = lastFrameChangeTime; }

    public boolean getReacting() { return this.reacting; }
    public void setReacting(boolean reacting) { this.reacting = reacting; }

    public boolean getBegin() { return this.begin; }
    public void setBegin(boolean begin) { this.begin = begin; }

    public boolean getEnd() { return this.end; }
    public void setEnd(boolean end) { this.end = end; }

    public void makeTransition(String ID) {
        this.entity.refreshCharacter(ID);
    }

    public void printController() {

        System.out.println("Info of controller :");
        System.out.println(" - entity: " + entity);
        System.out.println(" - x initial position: " + xInit);
        System.out.println(" - y initial position: " + yInit);
        System.out.println(" - x position: " + xPos);
        System.out.println(" - y position: " + yPos);
        System.out.println(" - x delta: " + xDelta);
        System.out.println(" - y delta: " + yDelta);
        System.out.println(" - frame rate: " + frameLengthInMilliseconds);
        System.out.println(" - last frame change time: " + lastFrameChangeTime);
        System.out.println(" - reacting: " + reacting);

    }

}