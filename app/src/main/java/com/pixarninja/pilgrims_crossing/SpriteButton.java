package com.pixarninja.pilgrims_crossing;

import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.RectF;
import java.util.LinkedHashMap;

public class SpriteButton extends SpriteEntity {

    private int onID;
    private int pokedID;
    private int offID;

    public SpriteButton(SpriteView spriteView, Resources res, double percentOfScreen, int width, int height, int xRes, int yRes, int onID, int pokedID, int offID,
                        double xDelta, double yDelta, double xInit, double yInit, int xFrameCount, int yFrameCount, int frameCount,
                        double xDimension, double yDimension, double spriteScale,
                        double left, double top, double right, double bottom, String method, SpriteController controller, String ID) {

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
        this.onID = onID;
        this.pokedID = pokedID;
        this.offID = offID;
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
            case "off":
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
                render.setMethod("loop");
                xSpriteRes = 2 * xRes / render.getXFrameCount();
                ySpriteRes = 2 * yRes / render.getYFrameCount();
                render.setSpriteSheet(decodeSampledBitmapFromResource(res, offID, (int) (xSpriteRes * spriteScale), (int) (ySpriteRes * spriteScale)));
                render.setFrameWidth(render.getSpriteSheet().getWidth() / render.getXFrameCount());
                render.setFrameHeight(render.getSpriteSheet().getHeight() / render.getYFrameCount());
                render.setFrameScale(spriteScale * height * percentOfScreen / render.getFrameHeight());
                render.setSpriteWidth((int) (render.getFrameWidth() * render.getFrameScale()));
                render.setSpriteHeight((int) (render.getFrameHeight() * render.getFrameScale()));
                render.setWhereToDraw(new RectF((float) controller.getXPos(), (float) controller.getYPos(), (float) controller.getXPos() + render.getSpriteWidth(), (float) controller.getYPos() + render.getSpriteHeight()));
                break;
            case "poked":
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
                render.setMethod("once");
                xSpriteRes = 2 * xRes / render.getXFrameCount();
                ySpriteRes = 2 * yRes / render.getYFrameCount();
                render.setSpriteSheet(decodeSampledBitmapFromResource(res, pokedID, (int) (xSpriteRes * spriteScale), (int) (ySpriteRes * spriteScale)));
                render.setFrameWidth(render.getSpriteSheet().getWidth() / render.getXFrameCount());
                render.setFrameHeight(render.getSpriteSheet().getHeight() / render.getYFrameCount());
                render.setFrameScale(spriteScale * height * percentOfScreen / render.getFrameHeight());
                render.setSpriteWidth((int) (render.getFrameWidth() * render.getFrameScale()));
                render.setSpriteHeight((int) (render.getFrameHeight() * render.getFrameScale()));
                render.setWhereToDraw(new RectF((float) controller.getXPos(), (float) controller.getYPos(), (float) controller.getXPos() + render.getSpriteWidth(), (float) controller.getYPos() + render.getSpriteHeight()));
                break;
            case "on":
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
                render.setMethod("loop");
                xSpriteRes = 2 * xRes / render.getXFrameCount();
                ySpriteRes = 2 * yRes / render.getYFrameCount();
                render.setSpriteSheet(decodeSampledBitmapFromResource(res, onID, (int) (xSpriteRes * spriteScale), (int) (ySpriteRes * spriteScale)));
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
                refreshCharacter("off");
                ID = "off";
                controller.setXPos(controller.getXInit() - render.getSpriteWidth() / 2);
                controller.setYPos(controller.getYInit() - render.getSpriteHeight() / 2);
                render.setFrameToDraw(new Rect(0, 0, render.getFrameWidth(), render.getFrameHeight()));
                render.setWhereToDraw(new RectF((float)controller.getXPos(), (float)controller.getYPos(), (float)controller.getXPos() + render.getSpriteWidth(), (float)controller.getYPos() + render.getSpriteHeight()));
        }
        controller.setEntity(this);
        controller.setTransition(ID);
        updateBoundingBox();
    }

    @Override
    public void onTouchEvent(SpriteView spriteView, LinkedHashMap.Entry<String, SpriteController> entry, LinkedHashMap<String, SpriteController> controllerMap, boolean move, boolean jump, float xTouchedPos, float yTouchedPos) {

        String transition;
        String ID;

        if(move || jump) {

            RectF boundingBox = this.render.getBoundingBox();

            if (xTouchedPos >= boundingBox.left && xTouchedPos <= boundingBox.right) {
                /* center of the sprite */
                if (yTouchedPos >= boundingBox.top && yTouchedPos <= boundingBox.bottom) {

                    /* change the sprite if needed */
                    if(entry.getKey().equals("SprintLeftButtonController")) {

                        SpriteController controller;

                        /* set box */
                        controller = controllerMap.get("BoxController");
                        SpriteCharacter oldBox = (SpriteCharacter) controller.getEntity();
                        transition = controller.getTransition();
                        ID = "inherit " + transition;
                        SpriteCharacter newBox = new BoxGreen(spriteView, oldBox.res, oldBox.percentOfScreen, oldBox.xRes, oldBox.yRes, width, height, controller, ID);
                        newBox.setCount(oldBox.getCount());
                        newBox.setDelta(oldBox.getDelta());
                        controller.setEntity(newBox);

                        /* move character */
                        if(controller.getXPos() > 0) {
                            controller.setXDelta(-30);
                        }
                        else {
                            controller.setXDelta(0);
                        }

                        controllerMap.put("BoxController", controller);

                    }
                    else if(entry.getKey().equals("RunLeftButtonController")) {

                        SpriteController controller;

                        /* set box */
                        controller = controllerMap.get("BoxController");
                        SpriteCharacter oldBox = (SpriteCharacter) controller.getEntity();
                        transition = controller.getTransition();
                        ID = "inherit " + transition;
                        SpriteCharacter newBox = new BoxBlue(spriteView, oldBox.res, oldBox.percentOfScreen, oldBox.xRes, oldBox.yRes, width, height, controller, ID);
                        newBox.setCount(oldBox.getCount());
                        newBox.setDelta(oldBox.getDelta());
                        controller.setEntity(newBox);

                        /* move character */
                        if(controller.getXPos() > 0) {
                            controller.setXDelta(-15);
                        }
                        else {
                            controller.setXDelta(0);
                        }

                        controllerMap.put("BoxController", controller);

                    }
                    else if(entry.getKey().equals("RunRightButtonController")) {

                        SpriteController controller;

                        /* set box */
                        controller = controllerMap.get("BoxController");
                        SpriteCharacter oldBox = (SpriteCharacter) controller.getEntity();
                        transition = controller.getTransition();
                        ID = "inherit " + transition;
                        SpriteCharacter newBox = new BoxBlue(spriteView, oldBox.res, oldBox.percentOfScreen, oldBox.xRes, oldBox.yRes, width, height, controller, ID);
                        newBox.setCount(oldBox.getCount());
                        newBox.setDelta(oldBox.getDelta());
                        controller.setEntity(newBox);

                        /* move character */
                        if(controller.getXPos() < width - (controller.getEntity().getSprite().getSpriteWidth())) {
                            controller.setXDelta(15);
                        }
                        else {
                            controller.setXDelta(0);
                        }

                        controllerMap.put("BoxController", controller);

                    }
                    else if(entry.getKey().equals("SprintRightButtonController")) {

                        SpriteController controller;

                        /* set box */
                        controller = controllerMap.get("BoxController");
                        SpriteCharacter oldBox = (SpriteCharacter) controller.getEntity();
                        transition = controller.getTransition();
                        ID = "inherit " + transition;
                        SpriteCharacter newBox = new BoxGreen(spriteView, oldBox.res, oldBox.percentOfScreen, oldBox.xRes, oldBox.yRes, width, height, controller, ID);
                        newBox.setCount(oldBox.getCount());
                        newBox.setDelta(oldBox.getDelta());
                        controller.setEntity(newBox);

                        /* move character */
                        if(controller.getXPos() <  width - (newBox.getSprite().getSpriteWidth())) {
                            controller.setXDelta(30);
                        }
                        else {
                            controller.setXDelta(0);
                        }

                        controllerMap.put("BoxController", controller);

                    }
                    else if(entry.getKey().equals("JumpButtonController")) {
                        if(jump) {

                            SpriteController controller;

                            /* set box */
                            controller = controllerMap.get("BoxController");
                            SpriteCharacter oldBox = (SpriteCharacter) controller.getEntity();
                            transition = controller.getTransition();
                            ID = "inherit " + transition;
                            SpriteCharacter newBox = new BoxGreen(spriteView, oldBox.res, oldBox.percentOfScreen, oldBox.xRes, oldBox.yRes, width, height, controller, ID);
                            newBox.setCount(oldBox.getCount());
                            newBox.setDelta(oldBox.getDelta());
                            controller.setEntity(newBox);

                            /* start jump */
                            if (controller.getYDelta() == 0) {
                                controller.setYDelta(30);
                            }

                            controllerMap.put("BoxController", controller);

                        }
                    }
                }
            }
            /* outside the range of the slider */
            else if (xTouchedPos >= 3.75 * width / 6 || xTouchedPos <= 0.25 * width / 6 || yTouchedPos >= 8.5 * height / 10 || yTouchedPos <= 7.5 * height / 10) {

                SpriteController controller;

                /* set box */
                controller = controllerMap.get("BoxController");
                SpriteCharacter oldBox = (SpriteCharacter) controller.getEntity();
                transition = controller.getTransition();
                ID = "inherit " + transition;
                SpriteCharacter newBox = new BoxRed(spriteView, oldBox.res, oldBox.percentOfScreen, oldBox.xRes, oldBox.yRes, width, height, controller, ID);
                newBox.setCount(oldBox.getCount());
                newBox.setDelta(oldBox.getDelta());
                controller.setEntity(newBox);

                /* move character */
                controller.setXDelta(0);

                controllerMap.put("BoxController", controller);

            }
        }
        else {
            SpriteController controller;

            /* set box */
            controller = controllerMap.get("BoxController");
            SpriteCharacter oldBox = (SpriteCharacter) controller.getEntity();
            transition = controller.getTransition();
            ID = "inherit " + transition;
            SpriteCharacter newBox = new BoxRed(spriteView, oldBox.res, oldBox.percentOfScreen, oldBox.xRes, oldBox.yRes, width, height, controller, ID);
            newBox.setCount(oldBox.getCount());
            newBox.setDelta(oldBox.getDelta());
            controller.setEntity(newBox);

            /* move character */
            controller.setXDelta(0);

            controllerMap.put("BoxController", controller);
        }
    }

}
