package com.pixarninja.pilgrims_crossing;

import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.RectF;
public class DarkPattern extends SpriteProp{


    public DarkPattern(SpriteView spriteView, Resources res, double percentOfScreen, int width, int height, int xRes, int yRes,
                       double xDelta, double yDelta, int xInit, int yInit, int xFrameCount, int yFrameCount, int frameCount,
                       double xDimension, double yDimension, double spriteScale,
                       double left, double top, double right, double bottom, String method, SpriteController controller, String ID) {

        super(spriteView, res, percentOfScreen, width, height, xRes, yRes, xDelta, yDelta, xInit, yInit, xFrameCount, yFrameCount, frameCount, xDimension, yDimension, spriteScale, left, top, right, bottom, method, controller, ID);

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

        refreshCharacter(ID);

    }

    @Override
    public void refreshCharacter(String ID) {

        int xSpriteRes;
        int ySpriteRes;

        /* setup sprite via parsing */
        ID = parseID(ID);

        switch (ID) {
            case "red":
                render.setID(ID);
                render.setXDimension(xDimension);
                render.setYDimension(yDimension);
                render.setLeft(left);
                render.setTop(top);
                render.setRight(right);
                render.setBottom(bottom);
                render.setXFrameCount(1);
                render.setYFrameCount(1);
                render.setFrameCount(1);
                render.setMethod("loop");
                xSpriteRes = 2 * xRes / render.getXFrameCount();
                ySpriteRes = 2 * yRes / render.getYFrameCount();
                render.setSpriteSheet(decodeSampledBitmapFromResource(res, R.mipmap.dark_pattern_red, (int) (xSpriteRes * spriteScale), (int) (ySpriteRes * spriteScale)));
                render.setFrameWidth(render.getSpriteSheet().getWidth() / render.getXFrameCount());
                render.setFrameHeight(render.getSpriteSheet().getHeight() / render.getYFrameCount());
                render.setFrameScale(spriteScale * height * percentOfScreen / render.getFrameHeight());
                render.setSpriteWidth((int) (render.getFrameWidth() * render.getFrameScale()));
                render.setSpriteHeight((int) (render.getFrameHeight() * render.getFrameScale()));
                render.setWhereToDraw(new RectF((float) controller.getXPos(), (float) controller.getYPos(), (float) controller.getXPos() + render.getSpriteWidth(), (float) controller.getYPos() + render.getSpriteHeight()));
                break;
            case "green":
                render.setID(ID);
                render.setXDimension(xDimension);
                render.setYDimension(yDimension);
                render.setLeft(left);
                render.setTop(top);
                render.setRight(right);
                render.setBottom(bottom);
                render.setXFrameCount(1);
                render.setYFrameCount(1);
                render.setFrameCount(1);
                render.setMethod("loop");
                xSpriteRes = 2 * xRes / render.getXFrameCount();
                ySpriteRes = 2 * yRes / render.getYFrameCount();
                render.setSpriteSheet(decodeSampledBitmapFromResource(res, R.mipmap.dark_pattern_green, (int) (xSpriteRes * spriteScale), (int) (ySpriteRes * spriteScale)));
                render.setFrameWidth(render.getSpriteSheet().getWidth() / render.getXFrameCount());
                render.setFrameHeight(render.getSpriteSheet().getHeight() / render.getYFrameCount());
                render.setFrameScale(spriteScale * height * percentOfScreen / render.getFrameHeight());
                render.setSpriteWidth((int) (render.getFrameWidth() * render.getFrameScale()));
                render.setSpriteHeight((int) (render.getFrameHeight() * render.getFrameScale()));
                render.setWhereToDraw(new RectF((float) controller.getXPos(), (float) controller.getYPos(), (float) controller.getXPos() + render.getSpriteWidth(), (float) controller.getYPos() + render.getSpriteHeight()));
                break;
            case "blue":
                render.setID(ID);
                render.setXDimension(xDimension);
                render.setYDimension(yDimension);
                render.setLeft(left);
                render.setTop(top);
                render.setRight(right);
                render.setBottom(bottom);
                render.setXFrameCount(1);
                render.setYFrameCount(1);
                render.setFrameCount(1);
                render.setMethod("loop");
                xSpriteRes = 2 * xRes / render.getXFrameCount();
                ySpriteRes = 2 * yRes / render.getYFrameCount();
                render.setSpriteSheet(decodeSampledBitmapFromResource(res, R.mipmap.dark_pattern_blue, (int) (xSpriteRes * spriteScale), (int) (ySpriteRes * spriteScale)));
                render.setFrameWidth(render.getSpriteSheet().getWidth() / render.getXFrameCount());
                render.setFrameHeight(render.getSpriteSheet().getHeight() / render.getYFrameCount());
                render.setFrameScale(spriteScale * height * percentOfScreen / render.getFrameHeight());
                render.setSpriteWidth((int) (render.getFrameWidth() * render.getFrameScale()));
                render.setSpriteHeight((int) (render.getFrameHeight() * render.getFrameScale()));
                render.setWhereToDraw(new RectF((float) controller.getXPos(), (float) controller.getYPos(), (float) controller.getXPos() + render.getSpriteWidth(), (float) controller.getYPos() + render.getSpriteHeight()));
                break;
            case "skip":
                break;
            case "init":
            default:
                render = new Sprite();
                refreshCharacter("red");
                ID = "red";
                render.setXCurrentFrame(0);
                render.setYCurrentFrame(0);
                render.setCurrentFrame(0);
                render.setFrameToDraw(new Rect(0, 0, render.getFrameWidth(), render.getFrameHeight()));
                render.setWhereToDraw(new RectF((float)controller.getXPos(), (float)controller.getYPos(), (float)controller.getXPos() + render.getSpriteWidth(), (float)controller.getYPos() + render.getSpriteHeight()));
        }
        controller.setEntity(this);
        controller.setTransition(ID);
        updateBoundingBox();
    }

    @Override
    public void updateView() {

        controller.setXPos(controller.getXPos() + controller.getXDelta());
        controller.setYPos(controller.getYPos() + controller.getYDelta());

        /* loopable backgrounds */
        if(controller.getXDelta() < 0) {
            if(controller.getYDelta() < 0) {
                if ((controller.getXPos() <= (controller.getXInit() - (render.getSpriteWidth() / 10))) || (controller.getYPos() <= (controller.getYInit() - (render.getSpriteHeight() / 10)))) {
                    controller.setXPos(controller.getXInit());
                    controller.setYPos(controller.getYInit());
                }
            }
            else {
                if ((controller.getXPos() <= (controller.getXInit() - (render.getSpriteWidth() / 10))) || (controller.getYPos() >= (controller.getYInit() + (render.getSpriteHeight() / 10)))) {
                    controller.setXPos(controller.getXInit());
                    controller.setYPos(controller.getYInit());
                }
            }
        }
        else {
            if(controller.getYDelta() < 0) {
                if ((controller.getXPos() >= (controller.getXInit() + (render.getSpriteWidth() / 10))) || (controller.getYPos() <= (controller.getYInit() - (render.getSpriteHeight() / 10)))) {
                    controller.setXPos(controller.getXInit());
                    controller.setYPos(controller.getYInit());
                }
            }
            else {
                if ((controller.getXPos() >= (controller.getXInit() + (render.getSpriteWidth() / 10))) || (controller.getYPos() >= (controller.getYInit() + (render.getSpriteHeight() / 10)))) {
                    controller.setXPos(controller.getXInit());
                    controller.setYPos(controller.getYInit());
                }
            }
        }
        render.setWhereToDraw(new RectF((float) controller.getXPos(), (float) controller.getYPos(), (float) controller.getXPos() + render.getSpriteWidth(), (float) controller.getYPos() + render.getSpriteHeight()));
        getCurrentFrame();
        updateBoundingBox();

    }

}
