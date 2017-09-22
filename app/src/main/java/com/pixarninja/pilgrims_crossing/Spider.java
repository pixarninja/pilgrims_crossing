package com.pixarninja.pilgrims_crossing;

import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.LinkedHashMap;
import java.util.Random;

public class Spider extends SpriteProp {

    public Spider(SpriteView spriteView, Resources res, int width, int height, int xRes, int yRes, double xInit, double yInit, String ID) {

        super();

        this.controller = new SpriteController();
        Random random = new Random();

        this.controller.setID(ID);
        this.spriteView = spriteView;
        this.res = res;
        this.percentOfScreen = 0.1;
        this.width = width;
        this.height = height;
        this.xRes = xRes;
        this.yRes = yRes;
        this.controller.setXDelta(random.nextDouble() * -3 - 7);
        this.controller.setYDelta(0);
        this.controller.setXInit(xInit);
        this.controller.setYInit(yInit);
        this.controller.setXPos(xInit);
        this.controller.setYPos(yInit);
        this.controller.setReacting(false);
        this.xDimension = 1;
        this.yDimension = 1;
        this.spriteScale = 1;
        this.left = 0;
        this.top = 0;
        this.right = 1;
        this.bottom = 1;

        refreshEntity("init");

    }

    @Override
    public void refreshEntity(String ID) {

        int xSpriteRes;
        int ySpriteRes;

        /* setup sprite via parsing */
        ID = parseID(ID);

        try {
            switch (ID) {
                case "destroyed":
                    controller.setReacting(true);
                    controller.setYDelta(0);
                    controller.setXDelta(0);
                    controller.setXPos(controller.getXPos() + render.getSpriteWidth() / 2);
                    render.setID(ID);
                    render.setXDimension(xDimension);
                    render.setYDimension(yDimension);
                    render.setLeft(left);
                    render.setTop(top);
                    render.setRight(right);
                    render.setBottom(bottom);
                    render.setXFrameCount(4);
                    render.setYFrameCount(4);
                    render.setFrameCount(16);
                    render.setMethod("die");
                    render.setDirection("forwards");
                    xSpriteRes = 2 * xRes / render.getXFrameCount();
                    ySpriteRes = 2 * yRes / render.getYFrameCount();
                    render.setSpriteSheet(decodeSampledBitmapFromResource(res, R.mipmap.spritesheet_spider_destroyed, (int) (xSpriteRes * spriteScale), (int) (ySpriteRes * spriteScale)));
                    render.setFrameWidth(render.getSpriteSheet().getWidth() / render.getXFrameCount());
                    render.setFrameHeight(render.getSpriteSheet().getHeight() / render.getYFrameCount());
                    render.setFrameScale(spriteScale * height * percentOfScreen / render.getFrameHeight());
                    render.setSpriteWidth((int) (render.getFrameWidth() * render.getFrameScale() * 1.8));
                    render.setSpriteHeight((int) (render.getFrameHeight() * render.getFrameScale() * 1.8));
                    controller.setXPos(controller.getXPos() - render.getSpriteWidth() / 2);
                    controller.setYPos(controller.getYPos() - 70);
                    render.setWhereToDraw(new RectF((float) controller.getXPos(), (float) controller.getYPos(), (float) controller.getXPos() + render.getSpriteWidth(), (float) controller.getYPos() + render.getSpriteHeight()));
                    break;
                case "idle":
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
                    render.setDirection("forwards");
                    xSpriteRes = 2 * xRes / render.getXFrameCount();
                    ySpriteRes = 2 * yRes / render.getYFrameCount();
                    render.setSpriteSheet(decodeSampledBitmapFromResource(res, R.mipmap.spritesheet_spider_idle_loop, (int) (xSpriteRes * spriteScale), (int) (ySpriteRes * spriteScale)));
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
                    refreshEntity("idle");
                    ID = "idle";
                    render.setXCurrentFrame(0);
                    render.setYCurrentFrame(0);
                    render.setCurrentFrame(0);
                    render.setFrameToDraw(new Rect(0, 0, render.getFrameWidth(), render.getFrameHeight()));
                    render.setWhereToDraw(new RectF((float)controller.getXPos(), (float)controller.getYPos(), (float)controller.getXPos() + render.getSpriteWidth(), (float)controller.getYPos() + render.getSpriteHeight()));
            }
            updateBoundingBox();
            controller.setEntity(this);
            controller.setTransition(ID);
        } catch(NullPointerException e) {
            refreshEntity(ID);
        }
    }

    @Override
    public void updateView() {

        controller.setXPos(controller.getXPos() + controller.getXDelta());
        controller.setYPos(controller.getYPos() + controller.getYDelta());

        render.setWhereToDraw(new RectF((float) controller.getXPos(), (float) controller.getYPos(), (float) controller.getXPos() + render.getSpriteWidth(), (float) controller.getYPos() + render.getSpriteHeight()));
        getCurrentFrame();
        updateBoundingBox();

    }

    @Override
    public LinkedHashMap<String, SpriteController> onCollisionEvent(LinkedHashMap.Entry<String, SpriteController> entry, LinkedHashMap<String, SpriteController> controllerMap) {

        LinkedHashMap<String, SpriteController> map = new LinkedHashMap<>();

        if(!controller.getReacting() && entry.getValue().getEntity().getSprite().getBoundingBox() != null) {
            RectF entryBox = entry.getValue().getEntity().getSprite().getBoundingBox();
            for (LinkedHashMap.Entry<String, SpriteController> test : controllerMap.entrySet()) {
                if (!test.getKey().equals(entry.getKey()) && !test.getValue().getReacting()) {
                    if ((test.getValue().getEntity().getSprite().getBoundingBox() != null) && (test.getKey().equals("PlayerController") || test.getKey().contains("Bridge"))) {
                        RectF compareBox = test.getValue().getEntity().getSprite().getBoundingBox();
                        /* if the objects intersect, find where they intersect for the entry bounding boxes */
                        if (entryBox.intersect(compareBox)) {
                            if(test.getKey().equals("PlayerController")) {
                                entry.getValue().setID("hit player");
                            }
                            else {
                                entry.getValue().setID("hit bridge");
                            }
                            entry.getValue().getEntity().refreshEntity("inherit destroyed");
                        }
                    }
                }
            }
        }

        return map;

    }

}
