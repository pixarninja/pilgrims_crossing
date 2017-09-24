package com.pixarninja.pilgrims_crossing;

import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.LinkedHashMap;
import java.util.Random;

public class Spider extends Enemy {

    public Spider(SpriteView spriteView, Resources res, int width, int height, int xRes, int yRes, double xInit, double yInit, String ID) {

        super();

        this.controller = new SpriteController();

        this.controller.setID(ID);
        this.spriteView = spriteView;
        this.res = res;
        this.percentOfScreen = 0.1;
        this.width = width;
        this.height = height;
        this.xRes = xRes;
        this.yRes = yRes;
        this.controller.setXDelta(-5);
        this.controller.setYDelta(0);
        this.controller.setXInit(xInit);
        this.controller.setYInit(yInit);
        this.controller.setXPos(xInit);
        this.controller.setYPos(yInit);
        this.controller.setReacting(false);
        this.spriteScale = 1;
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
                    spriteScale = 0.25;
                    xSpriteRes = xRes * render.getXFrameCount();
                    ySpriteRes = yRes * render.getYFrameCount();
                    render.setSpriteSheet(decodeSampledBitmapFromResource(res, R.mipmap.spritesheet_spider_destroyed, (int)(xSpriteRes * spriteScale), (int)(ySpriteRes * spriteScale)));
                    render.setFrameWidth(render.getSpriteSheet().getWidth() / render.getXFrameCount());
                    render.setFrameHeight(render.getSpriteSheet().getHeight() / render.getYFrameCount());
                    render.setFrameScale((width * spriteScale) / (double)render.getFrameWidth()); // scale = goal width / original width
                    render.setSpriteWidth((int)(render.getFrameWidth() * render.getFrameScale())); // width = original width * scale
                    render.setSpriteHeight((int)(render.getFrameHeight() * render.getFrameScale())); // height = original height * scale
                    render.setWhereToDraw(new RectF((float) controller.getXPos(), (float) controller.getYPos(), (float) controller.getXPos() + render.getSpriteWidth(), (float) controller.getYPos() + render.getSpriteHeight()));
                    break;
                case "idle":
                    controller.setReacting(false);
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
                    render.setMethod("loop");
                    render.setDirection("forwards");
                    spriteScale = 0.25;
                    xSpriteRes = xRes * render.getXFrameCount();
                    ySpriteRes = yRes * render.getYFrameCount();
                    render.setSpriteSheet(decodeSampledBitmapFromResource(res, R.mipmap.spritesheet_spider_walking_loop, (int)(xSpriteRes * spriteScale), (int)(ySpriteRes * spriteScale)));
                    render.setFrameWidth(render.getSpriteSheet().getWidth() / render.getXFrameCount());
                    render.setFrameHeight(render.getSpriteSheet().getHeight() / render.getYFrameCount());
                    render.setFrameScale((width * spriteScale) / (double)render.getFrameWidth()); // scale = goal width / original width
                    render.setSpriteWidth((int)(render.getFrameWidth() * render.getFrameScale())); // width = original width * scale
                    render.setSpriteHeight((int)(render.getFrameHeight() * render.getFrameScale())); // height = original height * scale
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
    public LinkedHashMap<String, SpriteController> onCollisionEvent(LinkedHashMap.Entry<String, SpriteController> entry, LinkedHashMap<String, SpriteController> controllerMap) {

        LinkedHashMap<String, SpriteController> map = new LinkedHashMap<>();

        if (!entry.getValue().getReacting() && (entry.getValue().getEntity().getSprite().getBoundingBox() != null)) {
            RectF entryBox = entry.getValue().getEntity().getSprite().getBoundingBox();
            for (LinkedHashMap.Entry<String, SpriteController> test : controllerMap.entrySet()) {
                if (test.getKey().equals("PlayerController") && !test.getValue().getReacting()) {
                    if (test.getValue().getEntity().getSprite().getBoundingBox() != null) {
                        RectF compareBox = test.getValue().getEntity().getSprite().getBoundingBox();
                        /* if the objects intersect, find where they intersect for the entry bounding box */
                        if (entryBox.intersect(compareBox)) {
                            /* increment hit value */
                            hit = hit + 1;
                            if(hit > 15) {
                                int i = 1;
                                SpriteEntity entity = new ItemDrop(spriteView, res, width, height, xRes, yRes, controller.getXPos(), controller.getYPos() - controller.getEntity().getSprite().getSpriteHeight() / 3, "item drop red", "init");
                                while(controllerMap.get("ItemDrop" + i) != null) {
                                    i++;
                                }
                                map.put("ItemDrop" + i, entity.getController());
                                refreshEntity("destroyed");
                            }
                            else if(hit > 10) {
                                controller.setID("enemy stage3");
                            }
                            else if(hit > 5) {
                                controller.setID("enemy stage2");
                            }
                            else if(hit > 0) {
                                controller.setID("enemy stage1");
                            }
                        }
                    }
                }
            }
        }

        return map;

    }

}
