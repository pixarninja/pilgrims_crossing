package com.pixarninja.pilgrims_crossing;

import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.LinkedHashMap;
import java.util.Random;

public class Spider extends Enemy {

    public Spider(Resources res, int width, int height, int xRes, int yRes, double xInit, double yInit, String ID) {

        super();

        this.controller = new SpriteController();

        this.controller.setID(ID);
        this.res = res;
        this.width = width;
        this.height = height;
        this.xRes = xRes;
        this.yRes = yRes;
        this.controller.setXDelta(0);
        this.controller.setYDelta(0);
        this.controller.setXInit(xInit);
        this.controller.setYInit(yInit);
        this.controller.setXPos(xInit);
        this.controller.setYPos(yInit);
        this.controller.setReacting(false);
        this.spriteScale = 1;
        this.xDimension = 1;
        this.yDimension = 1;
        this.spriteScale = 0.23;
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
                case "attack":
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
                    render.setMethod("once");
                    render.setDirection("forwards");
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
                case "charge":
                    controller.setReacting(false);
                    render.setID(ID);
                    render.setXDimension(xDimension);
                    render.setYDimension(yDimension);
                    render.setLeft(left);
                    render.setTop(top);
                    render.setRight(right);
                    render.setBottom(bottom);
                    render.setXFrameCount(4);
                    render.setYFrameCount(1);
                    render.setFrameCount(4);
                    render.setMethod("mirror loop");
                    render.setDirection("forwards");
                    xSpriteRes = xRes * render.getXFrameCount();
                    ySpriteRes = yRes * render.getYFrameCount() * 4;
                    render.setSpriteSheet(decodeSampledBitmapFromResource(res, R.mipmap.spritesheet_spider_charge_mirror_loop, (int)(xSpriteRes * spriteScale), (int)(ySpriteRes * spriteScale)));
                    render.setFrameWidth(render.getSpriteSheet().getWidth() / render.getXFrameCount());
                    render.setFrameHeight(render.getSpriteSheet().getHeight() / render.getYFrameCount());
                    render.setFrameScale((width * spriteScale) / (double)render.getFrameWidth()); // scale = goal width / original width
                    render.setSpriteWidth((int)(render.getFrameWidth() * render.getFrameScale())); // width = original width * scale
                    render.setSpriteHeight((int)(render.getFrameHeight() * render.getFrameScale())); // height = original height * scale
                    render.setWhereToDraw(new RectF((float) controller.getXPos(), (float) controller.getYPos(), (float) controller.getXPos() + render.getSpriteWidth(), (float) controller.getYPos() + render.getSpriteHeight()));
                    break;
                case "idle":
                    attackCount = 0;
                    controller.setReacting(false);
                    render.setID(ID);
                    render.setXDimension(xDimension);
                    render.setYDimension(yDimension);
                    render.setLeft(left);
                    render.setTop(top);
                    render.setRight(right);
                    render.setBottom(bottom);
                    render.setXFrameCount(4);
                    render.setYFrameCount(1);
                    render.setFrameCount(4);
                    render.setMethod("mirror loop");
                    render.setDirection("forwards");
                    xSpriteRes = xRes * render.getXFrameCount();
                    ySpriteRes = yRes * render.getYFrameCount() * 4;
                    render.setSpriteSheet(decodeSampledBitmapFromResource(res, R.mipmap.spritesheet_spider_idle_mirror_loop, (int)(xSpriteRes * spriteScale), (int)(ySpriteRes * spriteScale)));
                    render.setFrameWidth(render.getSpriteSheet().getWidth() / render.getXFrameCount());
                    render.setFrameHeight(render.getSpriteSheet().getHeight() / render.getYFrameCount());
                    render.setFrameScale((width * spriteScale) / (double)render.getFrameWidth()); // scale = goal width / original width
                    render.setSpriteWidth((int)(render.getFrameWidth() * render.getFrameScale())); // width = original width * scale
                    render.setSpriteHeight((int)(render.getFrameHeight() * render.getFrameScale())); // height = original height * scale
                    render.setWhereToDraw(new RectF((float) controller.getXPos(), (float) controller.getYPos(), (float) controller.getXPos() + render.getSpriteWidth(), (float) controller.getYPos() + render.getSpriteHeight()));
                    break;
                case "birth":
                    controller.setReacting(true);
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
                    render.setMethod("once");
                    render.setDirection("backwards");
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
                case "skip":
                    break;
                case "init":
                default:
                    render = new Sprite();
                    refreshEntity("birth");
                    ID = "birth";
                    render.setXCurrentFrame(render.getXFrameCount() - 1);
                    render.setYCurrentFrame(render.getYFrameCount() - 1);
                    render.setCurrentFrame(render.getFrameCount() - 1);
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

        if(attackCount >= 1) {
            controller.setTransition("sizzle");
        }
        else if(controller.getTransition().equals("attack")) {
            attackCount++;
        }

        if(chargeFrameCount >= 200) {
            if(attackFrameCount >= 74) {
                chargeFrameCount = 0;
                attackFrameCount = 0;
                refreshEntity("attack");
            }
            else if(attackFrameCount == 0) {
                attackFrameCount++;
                refreshEntity("charge");
            }
            else {
                attackFrameCount++;
            }
        }
        else {
            chargeFrameCount++;
        }

        controller.setXPos(controller.getXPos() + controller.getXDelta());
        controller.setYPos(controller.getYPos() + controller.getYDelta());

        /* don't update the sprite if it is not on the screen */
        if(((controller.getXPos() >= -render.getSpriteWidth()) || (controller.getXPos() <= width)) && ((controller.getYPos() >= -render.getSpriteHeight()) || (controller.getYPos() <= height))) {
            render.setWhereToDraw(new RectF((float) controller.getXPos(), (float) controller.getYPos(), (float) controller.getXPos() + render.getSpriteWidth(), (float) controller.getYPos() + render.getSpriteHeight()));
            getCurrentFrame();
            updateBoundingBox();
        }

    }

    @Override
    public LinkedHashMap<String, SpriteController> onCollisionEvent(LinkedHashMap.Entry<String, SpriteController> entry, LinkedHashMap<String, SpriteController> controllerMap) {

        LinkedHashMap<String, SpriteController> map = new LinkedHashMap<>();

        if (!controller.getReacting() && (entry.getValue().getEntity().getSprite().getBoundingBox() != null)) {
            RectF entryBox = entry.getValue().getEntity().getSprite().getBoundingBox();
            if (render.getBoundingBox() != null) {
                RectF compareBox = render.getBoundingBox();
                /* if the objects intersect, find where they intersect for the entry bounding box */
                if (entryBox.intersect(compareBox)) {
                    /* increment hit value */
                    hit = hit + 1;
                    if(hit >= 30) {
                         int i = 1;
                         Random random = new Random();
                         int r = random.nextInt(5) % 5;
                         //int r = 1;
                         String[] s = {"fire", "light", "earth", "water", "time"};
                         SpriteEntity entity = new ItemDrop(res, width, height, xRes, yRes, spriteScale, controller.getXPos(), controller.getYPos() - controller.getEntity().getSprite().getSpriteHeight() / 3, "item drop " + s[r], "init");
                         while(controllerMap.get("ItemDrop" + i) != null) {
                             i++;
                         }
                         map.put("ItemDrop" + i, entity.getController());
                         refreshEntity("destroyed");
                         //controller.setID("spider stage3");
                    }
                    else if(hit >= 20) {
                        controller.setID("spider stage3");
                    }
                    else if(hit >= 10) {
                        controller.setID("spider stage2");
                    }
                    else if(hit >= 1) {
                        controller.setID("spider stage1");
                    }
                }
            }
        }

        return map;

    }

}
