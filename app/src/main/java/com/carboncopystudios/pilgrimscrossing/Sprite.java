package com.carboncopystudios.pilgrimscrossing;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.RectF;

public class Sprite {

    private double xDelta;
    private double yDelta;
    private boolean isMoving;
    private Bitmap spriteSheet;
    private double xPos;
    private double yPos;
    private int xFrameCount;
    private int yFrameCount;
    private int frameCount;
    private int frameWidth;
    private int frameHeight;
    private double frameScale;
    private double xDimension;
    private double yDimension;
    private int spriteWidth;
    private int spriteHeight;
    private int xCurrentFrame;
    private int yCurrentFrame;
    private int currentFrame;
    private Rect frameToDraw;
    private RectF whereToDraw;
    double left;
    double top;
    double right;
    double bottom;
    private RectF boundingBox;
    private boolean loop;
    private boolean flipped;


    public Sprite() {}

    public Sprite(Sprite reference) {

        xDelta = reference.getXDelta();
        yDelta = reference.getYDelta();
        xPos = reference.getXPos();
        yPos = reference.getYPos();
        spriteSheet = reference.getSpriteSheet();
        xFrameCount = reference.getXFrameCount();
        yFrameCount = reference.getYFrameCount();
        frameCount = reference.getFrameCount();
        Bitmap spriteSheet = reference.getSpriteSheet();
        frameWidth = spriteSheet.getWidth() / xFrameCount;
        frameHeight = spriteSheet.getHeight() / yFrameCount;
        frameScale = reference.getFrameScale();
        xDimension = reference.getXDimension();
        yDimension = reference.getYDimension();
        spriteWidth = (int)(reference.getFrameWidth() * frameScale);
        spriteHeight = (int)(reference.getFrameHeight() * frameScale);
        xCurrentFrame = 0;
        yCurrentFrame = 0;
        currentFrame = 0;
        frameToDraw = new Rect(0, 0, reference.getFrameWidth(), reference.getFrameHeight());
        whereToDraw = new RectF((float)reference.getXPos(), (float)reference.getYPos(), (float)reference.getXPos() + reference.getSpriteWidth(), (float)reference.getYPos() + reference.getSpriteHeight());
        this.left = reference.getLeft();
        this.top = reference.getTop();
        this.right = reference.getRight();
        this.bottom = reference.getBottom();
        boundingBox = reference.getBoundingBox();
        loop = reference.getLoop();
        flipped = reference.getFlipped();

    }

    public Sprite(double xDelta, double yDelta, boolean isMoving, Bitmap spriteSheet, double xPos, double yPos, int xFrameCount, int yFrameCount, int frameCount, double frameScale, double xDimension, double yDimension, double left, double top, double right, double bottom, boolean loop, boolean flipped) {

        this.xDelta = xDelta;
        this.yDelta = yDelta;
        this.isMoving = isMoving;
        this.xPos = xPos;
        this.yPos = yPos;
        this.spriteSheet = spriteSheet;
        this.xFrameCount = xFrameCount;
        this.yFrameCount = yFrameCount;
        this.frameCount = frameCount;
        Bitmap reference = spriteSheet;
        this.frameWidth = reference.getWidth() / xFrameCount;
        this.frameHeight = reference.getHeight() / yFrameCount;
        this.spriteWidth = (int)(frameWidth * frameScale);
        this.spriteHeight = (int)(frameHeight * frameScale);
        this.frameScale = frameScale;
        this.xDimension = xDimension;
        this.yDimension = yDimension;
        this.xCurrentFrame = 0;
        this.yCurrentFrame = 0;
        this.currentFrame = 0;
        this.frameToDraw = new Rect(0, 0, frameWidth, frameHeight);
        this.whereToDraw = new RectF((float)xPos, (float)yPos, (float)xPos + spriteWidth, (float)yPos + spriteHeight);
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.boundingBox = new RectF();
        this.loop = loop;
        this.flipped = flipped;

    }

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

    public boolean getIsMoving() {
        return this.isMoving;
    }
    public void setIsMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

    public Bitmap getSpriteSheet() {
        return this.spriteSheet;
    }
    public void setSpriteSheet(Bitmap spriteSheet) {
        this.spriteSheet = spriteSheet;
    }

    public double getXPos() {
        return this.xPos;
    }
    public void setXPos(double xPos) {
        this.xPos = xPos;
    }

    public double getYPos() {
        return this.yPos;
    }
    public void setYPos(double yPos) {
        this.yPos = yPos;
    }

    public int getXFrameCount() {
        return this.xFrameCount;
    }
    public void setXFrameCount(int xFrameCount) {
        this.xFrameCount = xFrameCount;
    }

    public int getYFrameCount() {
        return this.yFrameCount;
    }
    public void setYFrameCount(int yFrameCount) {
        this.yFrameCount = yFrameCount;
    }

    public int getFrameCount() {
        return this.frameCount;
    }
    public void setFrameCount(int frameCount) {
        this.frameCount = frameCount;
    }

    public int getFrameWidth() {
        return this.frameWidth;
    }
    public void setFrameWidth(int frameWidth) {
        this.frameWidth = frameWidth;
    }

    public int getFrameHeight() {
        return this.frameHeight;
    }
    public void setFrameHeight(int frameHeight) {
        this.frameHeight = frameHeight;
    }

    public double getFrameScale() {
        return this.frameScale;
    }
    public void setFrameScale(double frameScale) {
        this.frameScale = frameScale;
    }

    public double getXDimension() {
        return this.xDimension;
    }
    public void setxDimension(double xDimension) {
        this.xDimension = xDimension;
    }

    public double getYDimension() {
        return this.yDimension;
    }
    public void setyDimension(double yDimension) {
        this.yDimension = yDimension;
    }

    public int getSpriteWidth() {
        return this.spriteWidth;
    }
    public void setSpriteWidth(int spriteWidth) {
        this.spriteWidth = spriteWidth;
    }

    public int getSpriteHeight() {
        return this.spriteHeight;
    }
    public void setSpriteHeight(int spriteHeight) {
        this.spriteHeight = spriteHeight;
    }

    public int getXCurrentFrame() {
        return this.xCurrentFrame;
    }
    public void setXCurrentFrame(int xCurrentFrame) {
        this.xCurrentFrame = xCurrentFrame;
    }

    public int getYCurrentFrame() {
        return this.yCurrentFrame;
    }
    public void setYCurrentFrame(int yCurrentFrame) {
        this.yCurrentFrame = yCurrentFrame;
    }

    public int getCurrentFrame() {
        return this.currentFrame;
    }
    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public Rect getFrameToDraw() {
        return this.frameToDraw;
    }
    public void setFrameToDraw(Rect frameToDraw) {
        this.frameToDraw = frameToDraw;
    }

    public RectF getWhereToDraw() {
        return this.whereToDraw;
    }
    public void setWhereToDraw(RectF whereToDraw) {
        this.whereToDraw = whereToDraw;
    }

    public double getLeft() { return this.left; }
    public void setLeft(double left) { this.left = left; }

    public double getTop() { return this.top; }
    public void setTop(double top) { this.top = top; }

    public double getRight() { return this.right; }
    public void setRight(double right) { this.right = right; }

    public double getBottom() { return this.bottom; }
    public void setBottom(double bottom) { this.bottom = bottom; }

    public RectF getBoundingBox() {
        return this.boundingBox;
    }
    public void setBoundingBox(RectF boundingBox) {
        this.boundingBox = boundingBox;
    }

    public boolean getLoop() {
        return this.loop;
    }
    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public boolean getFlipped() {
        return this.flipped;
    }
    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

}
