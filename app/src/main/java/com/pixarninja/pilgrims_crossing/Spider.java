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
        hit = 0;
        health = 100;
        chargeCount = 0;
        attackCount = 0;
        startAttack = 84;
        startCharge = 84;

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
    public void refreshEntity(String transition) {

        int xSpriteRes;
        int ySpriteRes;

        /* setup sprite via parsing */
        transition = parseID(transition);

        try {
            switch (transition) {
                case "destroyed":
                    controller.setReacting(true);
                    render.setXCurrentFrame(0);
                    render.setYCurrentFrame(0);
                    render.setCurrentFrame(0);
                    controller.setYDelta(0);
                    controller.setXDelta(0);
                    render.setID(transition);
                    render.setXDimension(xDimension);
                    render.setYDimension(yDimension);
                    render.setLeft(left);
                    render.setTop(top);
                    render.setRight(right);
                    render.setBottom(bottom);
                    render.setXFrameCount(4);
                    render.setYFrameCount(2);
                    render.setFrameCount(8);
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
                case "explode":
                    controller.setID("spider stage1");
                    controller.setXPos(controller.getXPos() - render.getSpriteWidth() / 6);
                    controller.setYPos(controller.getYPos() - render.getSpriteHeight() / 2);
                    render.setXCurrentFrame(0);
                    render.setYCurrentFrame(0);
                    render.setCurrentFrame(0);
                    render.setID(transition);
                    render.setXDimension(xDimension);
                    render.setYDimension(yDimension);
                    render.setLeft(left);
                    render.setTop(top);
                    render.setRight(right);
                    render.setBottom(bottom);
                    render.setXFrameCount(4);
                    render.setYFrameCount(2);
                    render.setFrameCount(8);
                    render.setMethod("die");
                    render.setDirection("forwards");
                    spriteScale = 0.30;
                    xSpriteRes = xRes * render.getXFrameCount();
                    ySpriteRes = yRes * render.getYFrameCount();
                    render.setSpriteSheet(decodeSampledBitmapFromResource(res, R.mipmap.spritesheet_explosion, (int)(xSpriteRes * spriteScale), (int)(ySpriteRes * spriteScale)));
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
                    render.setID(transition);
                    render.setXDimension(xDimension);
                    render.setYDimension(yDimension);
                    render.setLeft(left);
                    render.setTop(top);
                    render.setRight(right);
                    render.setBottom(bottom);
                    render.setXFrameCount(1);
                    render.setYFrameCount(1);
                    render.setFrameCount(1);
                    render.setMethod("once");
                    render.setDirection("forwards");
                    xSpriteRes = xRes * render.getXFrameCount();
                    ySpriteRes = yRes * render.getYFrameCount();
                    render.setSpriteSheet(decodeSampledBitmapFromResource(res, R.mipmap.spritesheet_spider_attack, (int)(xSpriteRes * spriteScale), (int)(ySpriteRes * spriteScale)));
                    render.setFrameWidth(render.getSpriteSheet().getWidth() / render.getXFrameCount());
                    render.setFrameHeight(render.getSpriteSheet().getHeight() / render.getYFrameCount());
                    render.setFrameScale((width * spriteScale) / (double)render.getFrameWidth()); // scale = goal width / original width
                    render.setSpriteWidth((int)(render.getFrameWidth() * render.getFrameScale())); // width = original width * scale
                    render.setSpriteHeight((int)(render.getFrameHeight() * render.getFrameScale())); // height = original height * scale
                    render.setWhereToDraw(new RectF((float) controller.getXPos(), (float) controller.getYPos(), (float) controller.getXPos() + render.getSpriteWidth(), (float) controller.getYPos() + render.getSpriteHeight()));
                    break;
                case "charge":
                    controller.setReacting(false);
                    render.setID(transition);
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
                    render.setID(transition);
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
                    render.setID(transition);
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
                    render.setSpriteSheet(decodeSampledBitmapFromResource(res, R.mipmap.spritesheet_spider_destroyed_old, (int)(xSpriteRes * spriteScale), (int)(ySpriteRes * spriteScale)));
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
                    transition = "birth";
                    render.setXCurrentFrame(render.getXFrameCount() - 1);
                    render.setYCurrentFrame(render.getYFrameCount() - 1);
                    render.setCurrentFrame(render.getFrameCount() - 1);
                    render.setFrameToDraw(new Rect(0, 0, render.getFrameWidth(), render.getFrameHeight()));
                    render.setWhereToDraw(new RectF((float)controller.getXPos(), (float)controller.getYPos(), (float)controller.getXPos() + render.getSpriteWidth(), (float)controller.getYPos() + render.getSpriteHeight()));
            }
            updateBoundingBox();
            controller.setEntity(this);
            controller.setTransition(transition);
        } catch(NullPointerException e) {
            refreshEntity(transition);
        }
    }

    @Override
    public void updateView() {

        if(controller.getTransition().equals("attack")) {
            attackCount++;
        }
        else {
            if (chargeCount >= startCharge) {
                if (attackCount >= startAttack) {
                    chargeCount = 0;
                    attackCount = 0;
                    refreshEntity("attack");
                } else if (attackCount == 0) {
                    attackCount++;
                    refreshEntity("charge");
                } else {
                    attackCount++;
                }
            } else if (controller.getTransition().equals("idle")) {
                chargeCount++;
            }
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
    public void getCurrentFrame(){

        long time  = System.currentTimeMillis();
        if ( time > controller.getLastFrameChangeTime() + controller.getFrameRate()) {

            controller.setLastFrameChangeTime(time);

            if(render.getDirection().equals("backwards")) {
                if(count == 0) {
                    delta = -1;
                }
                else {
                    delta = 1;
                }

                if(delta < 0) {
                    render.setCurrentFrame(render.getCurrentFrame() + delta);
                    render.setXCurrentFrame(render.getXCurrentFrame() + delta);
                    if ((render.getXCurrentFrame() < 0) || (render.getCurrentFrame() < 0)) {
                        render.setYCurrentFrame(render.getYCurrentFrame() + delta);
                        if ((render.getYCurrentFrame() < 0) || (render.getCurrentFrame() < 0)) {
                            if(render.getMethod().equals("die")) {
                                controller.setAlive(false);
                                count = 0;
                            }
                            else if(render.getMethod().equals("once")) {
                                if(controller.getTransition().equals("attack")) {
                                    refreshEntity("inherit explode");
                                }
                                else {
                                    refreshEntity("idle");
                                }
                                count = 0;
                            }
                            else if(render.getMethod().equals("mirror") || render.getMethod().equals("poked")) {
                                render.setCurrentFrame(render.getFrameCount() - 1);
                                render.setXCurrentFrame(render.getXFrameCount() - 1);
                                render.setYCurrentFrame(render.getYFrameCount() - 1);
                                count++;
                            }
                            /* loop or idle */
                            else {
                                render.setYCurrentFrame(render.getXFrameCount() - 1);
                                render.setCurrentFrame(render.getFrameCount() - 1);
                                count = 0;
                            }
                        }
                        if (count <= 0) {
                            render.setXCurrentFrame(render.getXFrameCount() - 1);
                        }
                    }
                }
                else if (delta == 0) {
                    render.setCurrentFrame(0);
                    render.setXCurrentFrame(0);
                    render.setYCurrentFrame(0);
                    count++;
                }
                else {
                    render.setCurrentFrame(render.getCurrentFrame() + delta);
                    render.setXCurrentFrame(render.getXCurrentFrame() + delta);
                    if ((render.getXCurrentFrame() >= render.getXFrameCount()) || (render.getCurrentFrame() >= render.getFrameCount())) {
                        render.setYCurrentFrame(render.getYCurrentFrame() + delta);
                        if ((render.getYCurrentFrame() >= render.getYFrameCount()) || (render.getCurrentFrame() >= render.getFrameCount())) {
                            if(controller.getTransition().equals("attack")) {
                                refreshEntity("inherit explode");
                            }
                            else {
                                refreshEntity("idle");
                            }
                            count = 0;
                        }
                        if (count > 0) {
                            render.setXCurrentFrame(0);
                        }
                    }
                }
            }

            else if(render.getDirection().equals("flipped")) {
                if(count == 0) {
                    delta = -1;
                }
                else {
                    delta = 1;
                }

                if(delta < 0) {
                    render.setCurrentFrame(render.getCurrentFrame() - delta);
                    render.setXCurrentFrame(render.getXCurrentFrame() + delta);
                    if ((render.getXCurrentFrame() < 0) || (render.getCurrentFrame() >= render.getFrameCount())) {
                        render.setYCurrentFrame(render.getYCurrentFrame() - delta);
                        if ((render.getYCurrentFrame() >= render.getYFrameCount()) || (render.getCurrentFrame() >= render.getFrameCount())) {
                            if(render.getMethod().equals("die")) {
                                controller.setAlive(false);
                                count = 0;
                            }
                            else if(render.getMethod().equals("once")) {
                                if(controller.getTransition().equals("attack")) {
                                    refreshEntity("inherit explode");
                                }
                                else {
                                    refreshEntity("idle");
                                }
                                count = 0;
                            }
                            else if(render.getMethod().equals("mirror") || render.getMethod().equals("mirror loop") || render.getMethod().equals("poked")) {
                                render.setCurrentFrame(0);
                                render.setXCurrentFrame(0);
                                render.setYCurrentFrame(render.getYFrameCount() - 1);
                                count++;
                            }
                            /* loop or idle */
                            else {
                                render.setYCurrentFrame(0);
                                render.setCurrentFrame(0);
                                count = 0;
                            }
                        }
                        if (count <= 0) {
                            render.setXCurrentFrame(render.getXFrameCount() - 1);
                        }
                    }
                }
                else if (delta == 0) {
                    render.setCurrentFrame(0);
                    render.setXCurrentFrame(render.getXFrameCount() - 1);
                    render.setYCurrentFrame(render.getYFrameCount() - 1);
                    count++;
                }
                else {
                    render.setCurrentFrame(render.getCurrentFrame() + delta);
                    render.setXCurrentFrame(render.getXCurrentFrame() + delta);
                    if ((render.getXCurrentFrame() >= render.getXFrameCount()) || (render.getCurrentFrame() >= render.getFrameCount())) {
                        render.setYCurrentFrame(render.getYCurrentFrame() - delta);
                        if ((render.getYCurrentFrame() >= render.getYFrameCount()) || (render.getCurrentFrame() >= render.getFrameCount())) {
                            if(controller.getTransition().equals("attack")) {
                                refreshEntity("inherit explode");
                            }
                            else {
                                refreshEntity("idle");
                            }
                            count = 0;
                            render.setXCurrentFrame(render.getXFrameCount() - 1);
                        }
                    }
                }
            }

            else {
                if(count == 0) {
                    delta = 1;
                }
                else {
                    delta = -1;
                }

                if(delta > 0) {
                    render.setCurrentFrame(render.getCurrentFrame() + delta);
                    render.setXCurrentFrame(render.getXCurrentFrame() + delta);
                    if ((render.getXCurrentFrame() >= render.getXFrameCount()) || (render.getCurrentFrame() >= render.getFrameCount())) {
                        render.setYCurrentFrame(render.getYCurrentFrame() + delta);
                        if ((render.getYCurrentFrame() >= render.getYFrameCount()) || (render.getCurrentFrame() >= render.getFrameCount())) {
                            if(render.getMethod().equals("die")) {
                                controller.setAlive(false);
                                count = 0;
                            }
                            else if(render.getMethod().equals("once")) {
                                if(controller.getTransition().equals("attack")) {
                                    refreshEntity("inherit explode");
                                }
                                else {
                                    refreshEntity("idle");
                                }
                                count = 0;
                            }
                            else if(render.getMethod().equals("mirror") || render.getMethod().equals("mirror loop") || render.getMethod().equals("poked")) {
                                render.setCurrentFrame(render.getFrameCount() - 1);
                                render.setXCurrentFrame(render.getXFrameCount() - 1);
                                render.setYCurrentFrame(render.getYFrameCount() - 1);
                                count++;
                            }
                            /* loop or idle */
                            else {
                                render.setYCurrentFrame(0);
                                render.setCurrentFrame(0);
                                count = 0;
                            }
                        }
                        if (count <= 0) {
                            render.setXCurrentFrame(0);
                        }
                    }
                }
                else if (delta == 0) {
                    render.setCurrentFrame(render.getFrameCount());
                    render.setXCurrentFrame(render.getXFrameCount() - 1);
                    render.setYCurrentFrame(render.getYFrameCount() - 1);
                    count++;
                }
                else {
                    render.setCurrentFrame(render.getCurrentFrame() + delta);
                    render.setXCurrentFrame(render.getXCurrentFrame() + delta);
                    if ((render.getXCurrentFrame() < 0) || (render.getCurrentFrame() < 0)) {
                        render.setYCurrentFrame(render.getYCurrentFrame() + delta);
                        if ((render.getYCurrentFrame() < 0) || (render.getCurrentFrame() < 0)) {
                            if(render.getMethod().equals("mirror")) {
                                if(controller.getTransition().equals("attack")) {
                                    refreshEntity("inherit explode");
                                }
                                else {
                                    refreshEntity("idle");
                                }
                                count = 0;
                            }
                            else {
                                count = 0;
                                render.setXCurrentFrame(0);
                                render.setYCurrentFrame(0);
                                render.setCurrentFrame(0);
                            }
                        }
                        if (count > 0) {
                            render.setXCurrentFrame(render.getXFrameCount() - 1);
                        }
                    }
                }
            }

        }

        /* update the next frame from the spritesheet that will be drawn */
        Rect rect = new Rect();
        rect.left = render.getXCurrentFrame() * render.getFrameWidth();
        rect.right = rect.left + render.getFrameWidth();
        rect.top = render.getYCurrentFrame() * render.getFrameHeight();
        rect.bottom = rect.top + render.getFrameHeight();
        render.setFrameToDraw(rect);

    }

    @Override
    public LinkedHashMap<String, SpriteController> onCollisionEvent(LinkedHashMap.Entry<String, SpriteController> entry, LinkedHashMap<String, SpriteController> controllerMap) {

        LinkedHashMap<String, SpriteController> map = new LinkedHashMap<>();

        if(!controller.getReacting() && entry.getValue().getEntity().getSprite().getBoundingBox() != null) {
            RectF entryBox = entry.getValue().getEntity().getSprite().getBoundingBox();
            for (LinkedHashMap.Entry<String, SpriteController> test : controllerMap.entrySet()) {
                if (test.getKey().contains("Arrow") || test.getKey().contains("Player") && !test.getValue().getReacting()) {
                    if ((test.getValue().getEntity().getSprite().getBoundingBox() != null)) {
                        RectF compareBox = test.getValue().getEntity().getSprite().getBoundingBox();
                        /* if the objects intersect, find where they intersect for the entry bounding box */
                        if (entryBox.intersect(compareBox)) {

                            if(test.getValue().getID().contains("idle")) {
                                continue;
                            }

                            if(test.getKey().contains("Arrow")) {
                                /* destroy the arrow */
                                test.getValue().getEntity().refreshEntity("inherit destroyed");
                            }

                            /* increment hit value */
                            hit = hit + 1;
                            if(hit >= health) {
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
                                refreshEntity("inherit destroyed");
                                //controller.setID("spider stage3");
                            }
                            else if(hit >= 3 * health / 4) {
                                controller.setID("spider stage3");
                            }
                            else if(hit >= 2 * health / 3) {
                                controller.setID("spider stage2");
                            }
                            else if(hit >= health / 4) {
                                controller.setID("spider stage1");
                            }

                        }
                    }
                }
            }
        }

        return map;

    }

}
