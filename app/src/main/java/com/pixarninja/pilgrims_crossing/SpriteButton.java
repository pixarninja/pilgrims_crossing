package com.pixarninja.pilgrims_crossing;

import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.RectF;
import java.util.LinkedHashMap;

public class SpriteButton extends SpriteEntity {

    private int onID;
    private int pokedID;
    private int offID;
    private LinkedHashMap<String, Double> xDeltaMap;
    private LinkedHashMap<String, Double> yDeltaMap;
    private LinkedHashMap<String, Integer> frameRateMap;

    /* for extending the class */
    public SpriteButton() {}

    public SpriteButton(SpriteView spriteView, Resources res, double percentOfScreen, int width, int height, int xRes, int yRes, int onID, int pokedID, int offID,
                        double xDelta, double yDelta, double xInit, double yInit, int xFrameCount, int yFrameCount, int frameCount,
                        double xDimension, double yDimension, double spriteScale,
                        double left, double top, double right, double bottom, SpriteController controller, String ID, String transition) {

        if(controller == null) {
            this.controller = new SpriteController();
        }
        else {
            this.controller = controller;
        }
        this.controller.setID(ID);
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
                render.setDirection("forwards");
                xSpriteRes = xRes * render.getXFrameCount();
                ySpriteRes = yRes * render.getYFrameCount();
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
                render.setDirection("forwards");
                xSpriteRes = xRes * render.getXFrameCount();
                ySpriteRes = yRes * render.getYFrameCount();
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
                render.setDirection("forwards");
                xSpriteRes = xRes * render.getXFrameCount();
                ySpriteRes = yRes * render.getYFrameCount();
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
        controller.setTransition(transition);
        controller.setEntity(this);
        controller.setTransition(transition);
        updateBoundingBox();
    }

    @Override
    public void onTouchEvent(SpriteView spriteView, LinkedHashMap.Entry<String, SpriteController> entry, LinkedHashMap<String, SpriteController> controllerMap, boolean poke, boolean move, boolean jump, float xTouchedPos, float yTouchedPos) {

        String transition;
        SpriteController playerController = controllerMap.get("PlayerController");

        if(poke || move || jump) {

            RectF boundingBox = this.render.getBoundingBox();

            if (xTouchedPos >= boundingBox.left && xTouchedPos <= boundingBox.right) {
                /* center of the sprite */
                if (yTouchedPos >= boundingBox.top && yTouchedPos <= boundingBox.bottom) {

                    /* direction control buttons */
                    if(poke) {
                        /* flow control button */
                        if(entry.getKey().equals("FlowButtonController")) {
                            /* if the game is currently playing set the frame rate to infinity and store deltas */
                            if(entry.getValue().getTransition().equals("off")) {
                                refreshEntity("on");
                                xDeltaMap = new LinkedHashMap<>();
                                yDeltaMap = new LinkedHashMap<>();
                                frameRateMap = new LinkedHashMap<>();
                                for (LinkedHashMap.Entry<String, SpriteController> controller : controllerMap.entrySet()) {
                                    frameRateMap.put(controller.getKey(), controller.getValue().getFrameRate());
                                    xDeltaMap.put(controller.getKey(), controller.getValue().getXDelta());
                                    yDeltaMap.put(controller.getKey(), controller.getValue().getYDelta());
                                    controller.getValue().setFrameRate(Integer.MAX_VALUE);
                                    controller.getValue().setXDelta(0);
                                    controller.getValue().setYDelta(0);
                                }
                            }
                            /* else set the frame rate to 35 and return deltas */
                            else {
                                refreshEntity("off");
                                for (LinkedHashMap.Entry<String, SpriteController> controller : controllerMap.entrySet()) {
                                    controller.getValue().setFrameRate(frameRateMap.get(controller.getKey()));
                                    controller.getValue().setXDelta(xDeltaMap.get(controller.getKey()));
                                    controller.getValue().setYDelta(yDeltaMap.get(controller.getKey()));
                                }
                            }

                        }
                    }
                    else {
                        if(controllerMap.get("FlowButtonController").getTransition().equals("off")) {
                            /* direction control button */
                            if(entry.getKey().equals("SprintLeftButtonController")) {

                                /* set player */
                                if(!playerController.getReacting()) {
                                    Player oldPlayer = (Player) playerController.getEntity();
                                    transition = playerController.getTransition();

                                    if (transition.equals("idle")) {
                                        if(playerController.getID().equals("player sprint left")) {
                                            transition = "inherit idle";
                                        }
                                        else {
                                            transition = "reset idle";
                                        }
                                    } else {
                                        transition = "idle";
                                    }

                                    Player newPlayer = new PlayerSprint(spriteView, oldPlayer.res, oldPlayer.percentOfScreen, oldPlayer.xRes, oldPlayer.yRes, width, height, playerController, "player sprint left", transition);
                                    newPlayer.setCount(0);
                                    playerController.setEntity(newPlayer);
                                }

                                /* move character */
                                playerController.setXDelta(-25);

                                controllerMap.put("PlayerController", playerController);

                            }
                            else if(entry.getKey().equals("SprintRightButtonController")) {

                                /* set player */
                                if(!playerController.getReacting()) {
                                    Player oldPlayer = (Player) playerController.getEntity();
                                    transition = playerController.getTransition();

                                    if (transition.equals("idle")) {
                                        if(playerController.getID().equals("player sprint right")) {
                                            transition = "inherit idle";
                                        }
                                        else {
                                            transition = "reset idle";
                                        }
                                    } else {
                                        transition = "idle";
                                    }

                                    Player newPlayer = new PlayerSprint(spriteView, oldPlayer.res, oldPlayer.percentOfScreen, oldPlayer.xRes, oldPlayer.yRes, width, height, playerController, "player sprint right", transition);
                                    newPlayer.setCount(0);
                                    playerController.setEntity(newPlayer);
                                }

                                /* move character */
                                playerController.setXDelta(25);

                                controllerMap.put("PlayerController", playerController);

                            }
                        }
                    }

                }
            }
        }
    }

}
