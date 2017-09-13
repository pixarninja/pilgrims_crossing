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
                        double left, double top, double right, double bottom, String method, SpriteController controller, String transition) {

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

        refreshEntity(transition);

    }

    @Override
    public void refreshEntity(String transition) {

        int xSpriteRes;
        int ySpriteRes;

        /* setup sprite via parsing */
        transition = parseID(transition);

        switch (transition) {
            case "off":
                render.setID(transition);
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
                render.setID(transition);
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
                render.setID(transition);
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
                refreshEntity("off");
                transition = "off";
                controller.setXPos(controller.getXInit() - render.getSpriteWidth() / 2);
                controller.setYPos(controller.getYInit() - render.getSpriteHeight() / 2);
                render.setFrameToDraw(new Rect(0, 0, render.getFrameWidth(), render.getFrameHeight()));
                render.setWhereToDraw(new RectF((float)controller.getXPos(), (float)controller.getYPos(), (float)controller.getXPos() + render.getSpriteWidth(), (float)controller.getYPos() + render.getSpriteHeight()));
        }
        controller.setEntity(this);
        controller.setTransition(transition);
        updateBoundingBox();
    }

    @Override
    public void onTouchEvent(SpriteView spriteView, LinkedHashMap.Entry<String, SpriteController> entry, LinkedHashMap<String, SpriteController> controllerMap, boolean move, boolean jump, float xTouchedPos, float yTouchedPos) {

        String transition;
        String ID;
        SpriteController samuraiController = controllerMap.get("SamuraiController");

        if(move || jump) {

            RectF boundingSamurai = this.render.getBoundingBox();

            if (xTouchedPos >= boundingSamurai.left && xTouchedPos <= boundingSamurai.right) {
                /* center of the sprite */
                if (yTouchedPos >= boundingSamurai.top && yTouchedPos <= boundingSamurai.bottom) {

                    /* change the sprite if needed */
                    if(entry.getKey().equals("SprintLeftButtonController")) {

                        /* set samurai */
                        if(!samuraiController.getReacting()) {
                            SpriteCharacter oldSamurai = (SpriteCharacter) samuraiController.getEntity();
                            transition = samuraiController.getTransition();

                            if (transition.equals("idle")) {
                                if(samuraiController.getID().equals("sprint left")) {
                                    transition = "inherit idle";
                                }
                                else {
                                    transition = "reset idle";
                                }
                            } else {
                                transition = "idle";
                            }

                            SpriteCharacter newSamurai = new SamuraiSprint(spriteView, oldSamurai.res, oldSamurai.percentOfScreen, oldSamurai.xRes, oldSamurai.yRes, width, height, samuraiController, "sprint left", transition);
                            newSamurai.setCount(0);
                            samuraiController.setEntity(newSamurai);
                        }

                        /* move character */
                        samuraiController.setXDelta(-25);

                        controllerMap.put("SamuraiController", samuraiController);

                    }
                    else if(entry.getKey().equals("RunLeftButtonController")) {

                        /* set samurai */
                        if(!samuraiController.getReacting()) {
                            SpriteCharacter oldSamurai = (SpriteCharacter) samuraiController.getEntity();
                            transition = samuraiController.getTransition();

                            if (transition.equals("idle")) {
                                if(samuraiController.getID().equals("run left")) {
                                    transition = "inherit idle";
                                }
                                else {
                                    transition = "reset idle";
                                }
                            } else {
                                transition = "idle";
                            }

                            SpriteCharacter newSamurai = new SamuraiRun(spriteView, oldSamurai.res, oldSamurai.percentOfScreen, oldSamurai.xRes, oldSamurai.yRes, width, height, samuraiController, "run left", transition);
                            newSamurai.setCount(0);
                            samuraiController.setEntity(newSamurai);
                        }

                        /* move character */
                        samuraiController.setXDelta(-10);

                        controllerMap.put("SamuraiController", samuraiController);

                    }
                    else if(entry.getKey().equals("StopButtonController")) {

                        /* set samurai */
                        if(!samuraiController.getReacting()) {
                            SpriteCharacter oldSamurai = (SpriteCharacter) samuraiController.getEntity();
                            if(oldSamurai.getController().getID().equals("run right") || oldSamurai.getController().getID().equals("sprint right")  || oldSamurai.getController().getID().equals("idle right")) {
                                ID = "idle right";
                            }
                            else {
                                ID = "idle left";
                            }

                            transition = samuraiController.getTransition();

                            if (transition.equals("idle")) {
                                if(samuraiController.getID().equals("idle right") || samuraiController.getID().equals("idle left")) {
                                    transition = "inherit idle";
                                }
                                else {
                                    transition = "reset idle";
                                }
                            } else {
                                transition = "idle";
                            }

                            SpriteCharacter newSamurai = new SamuraiIdle(spriteView, oldSamurai.res, oldSamurai.percentOfScreen, oldSamurai.xRes, oldSamurai.yRes, width, height, samuraiController, ID, transition);
                            newSamurai.setCount(0);
                            samuraiController.setEntity(newSamurai);
                        }

                        /* move character */
                        samuraiController.setXDelta(0);

                        controllerMap.put("SamuraiController", samuraiController);

                    }
                    else if(entry.getKey().equals("RunRightButtonController")) {

                        /* set samurai */
                        if(!samuraiController.getReacting()) {
                            SpriteCharacter oldSamurai = (SpriteCharacter) samuraiController.getEntity();
                            transition = samuraiController.getTransition();

                            if (transition.equals("idle")) {
                                if(samuraiController.getID().equals("run right")) {
                                    transition = "inherit idle";
                                }
                                else {
                                    transition = "reset idle";
                                }
                            } else {
                                transition = "idle";
                            }

                            SpriteCharacter newSamurai = new SamuraiRun(spriteView, oldSamurai.res, oldSamurai.percentOfScreen, oldSamurai.xRes, oldSamurai.yRes, width, height, samuraiController, "run right", transition);
                            newSamurai.setCount(0);
                            samuraiController.setEntity(newSamurai);
                        }

                        /* move character */
                        samuraiController.setXDelta(10);

                        controllerMap.put("SamuraiController", samuraiController);

                    }
                    else if(entry.getKey().equals("SprintRightButtonController")) {

                        /* set samurai */
                        if(!samuraiController.getReacting()) {
                            SpriteCharacter oldSamurai = (SpriteCharacter) samuraiController.getEntity();
                            transition = samuraiController.getTransition();

                            if (transition.equals("idle")) {
                                if(samuraiController.getID().equals("sprint right")) {
                                    transition = "inherit idle";
                                }
                                else {
                                    transition = "reset idle";
                                }
                            } else {
                                transition = "idle";
                            }

                            SpriteCharacter newSamurai = new SamuraiSprint(spriteView, oldSamurai.res, oldSamurai.percentOfScreen, oldSamurai.xRes, oldSamurai.yRes, width, height, samuraiController, "sprint right", transition);
                            newSamurai.setCount(0);
                            samuraiController.setEntity(newSamurai);
                        }

                        /* move character */
                        samuraiController.setXDelta(25);

                        controllerMap.put("SamuraiController", samuraiController);

                    }

                }
            }
        }
    }

}
