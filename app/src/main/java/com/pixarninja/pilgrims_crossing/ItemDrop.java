package com.pixarninja.pilgrims_crossing;

import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.RectF;

public class ItemDrop extends SpriteProp{

    public ItemDrop(SpriteView spriteView, Resources res, int width, int height, int xRes, int yRes, double xInit, double yInit, String ID, String transition) {

        super();

        this.controller = new SpriteController();

        this.controller.setID(ID);
        this.spriteView = spriteView;
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
        this.xDimension = 1;
        this.yDimension = 1;
        this.spriteScale = 1;
        this.left = 0;
        this.top = 0;
        this.right = 1;
        this.bottom = 1;

        refreshEntity(transition);

    }

    @Override
    public void refreshEntity(String transition) {

        int xSpriteRes;
        int ySpriteRes;

        /* setup sprite via parsing */
        transition = parseID(transition);

        try {
            switch (transition) {
                case "sparkles":
                    this.controller.setReacting(true);
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
                    render.setMethod("die");
                    render.setDirection("forwards");
                    spriteScale = 0.25;
                    xSpriteRes = xRes * render.getXFrameCount();
                    ySpriteRes = yRes * render.getYFrameCount();
                    render.setSpriteSheet(decodeSampledBitmapFromResource(res, R.mipmap.spritesheet_item_drop_sparkles, (int) (xSpriteRes * spriteScale), (int) (ySpriteRes * spriteScale)));
                    render.setFrameWidth(render.getSpriteSheet().getWidth() / render.getXFrameCount());
                    render.setFrameHeight(render.getSpriteSheet().getHeight() / render.getYFrameCount());
                    render.setFrameScale((width * spriteScale) / (double)render.getFrameWidth()); // scale = goal width / original width
                    render.setSpriteWidth((int)(render.getFrameWidth() * render.getFrameScale())); // width = original width * scale
                    render.setSpriteHeight((int)(render.getFrameHeight() * render.getFrameScale())); // height = original height * scale
                    render.setWhereToDraw(new RectF((float) controller.getXPos(), (float) controller.getYPos(), (float) controller.getXPos() + render.getSpriteWidth(), (float) controller.getYPos() + render.getSpriteHeight()));
                    break;
                case "streaks":
                    this.controller.setReacting(true);
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
                    render.setDirection("forwards");
                    spriteScale = 0.25;
                    xSpriteRes = xRes * render.getXFrameCount();
                    ySpriteRes = yRes * render.getYFrameCount();
                    render.setSpriteSheet(decodeSampledBitmapFromResource(res, R.mipmap.spritesheet_item_drop_streaks, (int) (xSpriteRes * spriteScale), (int) (ySpriteRes * spriteScale)));
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
                    refreshEntity("streaks");
                    transition = "streaks";
                    render.setXCurrentFrame(0);
                    render.setYCurrentFrame(0);
                    render.setCurrentFrame(0);
                    render.setFrameToDraw(new Rect(0, 0, render.getFrameWidth(), render.getFrameHeight()));
                    render.setWhereToDraw(new RectF((float)controller.getXPos(), (float)controller.getYPos(), (float)controller.getXPos() + render.getSpriteWidth(), (float)controller.getYPos() + render.getSpriteHeight()));
            }
            controller.setTransition(transition);
            controller.setEntity(this);
            controller.setTransition(transition);
            updateBoundingBox();
        } catch(NullPointerException e) {
            refreshEntity(transition);
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
                                refreshEntity("idle");
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
                            refreshEntity("idle");
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
                                refreshEntity("idle");
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
                            refreshEntity("idle");
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
                                if(render.getID().equals("streaks")) {
                                    refreshEntity("sparkles");
                                }
                                /* else if(render.getID().equals("sparkles")) {
                                    refreshEntity("crystal");
                                }*/
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
                            refreshEntity("idle");
                            count = 0;
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

}
