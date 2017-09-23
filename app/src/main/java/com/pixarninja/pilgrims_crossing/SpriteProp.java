package com.pixarninja.pilgrims_crossing;

import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.LinkedHashMap;

public class SpriteProp extends SpriteEntity {

    int propID;

    /* for extending the class */
    public SpriteProp() {}

    public SpriteProp(SpriteView spriteView, Resources res, double percentOfScreen, int width, int height, int xRes, int yRes, int propID,
                      double xDelta, double yDelta, double xInit, double yInit, int xFrameCount, int yFrameCount, int frameCount,
                      double xDimension, double yDimension, double spriteScale,
                      double left, double top, double right, double bottom, String method, String direction, SpriteController controller, String ID, String transition) {

        if(controller == null) {
            this.controller = new SpriteController();
        }
        else {
            this.controller = controller;
        }
        this.spriteView = spriteView;
        this.res = res;
        this.percentOfScreen = percentOfScreen;
        this.width = width;
        this.height = height;
        this.xRes = xRes;
        this.yRes = yRes;
        this.propID = propID;
        this.controller.setXDelta(xDelta);
        this.controller.setYDelta(yDelta);
        this.controller.setXInit(xInit);
        this.controller.setYInit(yInit);
        this.controller.setXPos(xInit);
        this.controller.setYPos(yInit);
        this.xFrameCount = xFrameCount;
        this.yFrameCount = yFrameCount;
        this.frameCount = frameCount;
        this.xDimension = xDimension;
        this.yDimension = yDimension;
        this.spriteScale = spriteScale;
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.method = method;
        this.direction = direction;

        refreshEntity(ID);

    }

    @Override
    public void refreshEntity(String ID) {

        int xSpriteRes;
        int ySpriteRes;

        switch (ID) {
            case "skip":
                break;
            case "init":
            default:
                render = new Sprite();
                render.setID(ID);
                render.setXDimension(xDimension);
                render.setYDimension(yDimension);
                render.setLeft(left);
                render.setTop(top);
                render.setRight(right);
                render.setBottom(bottom);
                render.setXFrameCount(xFrameCount);
                render.setYFrameCount(yFrameCount);
                render.setFrameCount(frameCount);
                render.setMethod(method);
                render.setDirection(direction);
                xSpriteRes = xRes / render.getXFrameCount();
                ySpriteRes = yRes / render.getYFrameCount();
                render.setSpriteSheet(decodeSampledBitmapFromResource(res, propID, (int) (xSpriteRes * spriteScale), (int) (ySpriteRes * spriteScale)));
                render.setFrameWidth(render.getSpriteSheet().getWidth() / render.getXFrameCount());
                render.setFrameHeight(render.getSpriteSheet().getHeight() / render.getYFrameCount());
                render.setFrameScale(spriteScale * height * percentOfScreen / render.getFrameHeight());
                render.setSpriteWidth((int) (render.getFrameWidth() * render.getFrameScale()));
                render.setSpriteHeight((int) (render.getFrameHeight() * render.getFrameScale()));
                render.setXCurrentFrame(0);
                render.setYCurrentFrame(0);
                render.setCurrentFrame(0);
                render.setFrameToDraw(new Rect(0, 0, render.getFrameWidth(), render.getFrameHeight()));
                render.setWhereToDraw(new RectF((float) controller.getXPos(), (float) controller.getYPos(), (float) controller.getXPos() + render.getSpriteWidth(), (float) controller.getYPos() + render.getSpriteHeight()));
        }
        controller.setEntity(this);
        controller.setTransition(ID);
        updateBoundingBox();
    }

}