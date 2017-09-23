package com.pixarninja.pilgrims_crossing;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;

public class PlayerIdle extends Player {

    public PlayerIdle(SpriteView spriteView, Resources res, double percentOfScreen, int xRes, int yRes, int width, int height, SpriteController controller, String ID, String transition) {

        if(controller == null) {
            this.controller = new SpriteController();
            this.controller.setXInit(width / 2);
            this.controller.setYInit(height / 2);
        }
        else {
            this.controller = controller;
        }
        this.controller.setID(ID);
        this.controller.setReacting(false);
        this.spriteView = spriteView;
        this.res = res;
        this.percentOfScreen = percentOfScreen;
        this.xRes = xRes;
        this.yRes = yRes;
        this.width = width;
        this.height = height;
        count = 0;

        refreshEntity(transition);

    }

    @Override
    public void refreshEntity(String transition) {

        int xSpriteRes;
        int ySpriteRes;

        /* setup sprite via parsing */
        transition = parseID(transition);

        try {
            switch (controller.getID()) {
                case "idle left":
                    switch (transition) {
                        case "idle":
                            controller.setReacting(false);
                            render.setID(transition);
                            render.setXDimension(3.028);
                            render.setYDimension(5);
                            render.setLeft(0);
                            render.setTop(0);
                            render.setRight(3.028);
                            render.setBottom(5);
                            render.setXFrameCount(4);
                            render.setYFrameCount(1);
                            render.setFrameCount(4);
                            render.setDirection("flipped");
                            render.setMethod("mirror loop");
                            xSpriteRes = xRes * render.getFrameCount() / 2;
                            ySpriteRes = yRes * render.getFrameCount() / 2;
                            Bitmap flipped = decodeSampledBitmapFromResource(res, R.mipmap.spritesheet_samurai_idle_mirror_norm, xSpriteRes, ySpriteRes);
                            Matrix matrix = new Matrix();
                            matrix.postScale(-1, 1);
                            flipped = Bitmap.createBitmap(flipped, 0, 0, flipped.getWidth(), flipped.getHeight(), matrix, true);
                            render.setSpriteSheet(flipped);
                            render.setFrameWidth(render.getSpriteSheet().getWidth() / render.getXFrameCount());
                            render.setFrameHeight(render.getSpriteSheet().getHeight() / render.getYFrameCount());
                            render.setFrameScale((width / 8f) / (double)render.getFrameWidth()); // scale = goal width / original width
                            render.setSpriteWidth((int)(render.getFrameWidth() * render.getFrameScale())); // width = original width * scale
                            render.setSpriteHeight((int)(render.getFrameHeight() * render.getFrameScale())); // height = original height * scale
                            render.setWhereToDraw(new RectF((float)controller.getXPos(), (float)controller.getYPos(), (float)controller.getXPos() + render.getSpriteWidth(), (float)controller.getYPos() + render.getSpriteHeight()));
                            break;
                        case "skip":
                            break;
                        case "init":
                        default:
                            render = new Sprite();
                            controller.setXDelta(0);
                            controller.setYDelta(0);
                            refreshEntity("idle");
                            transition = "idle";
                            controller.setXPos(controller.getXInit() - render.getSpriteWidth() / 2);
                            controller.setYPos(controller.getYInit() - render.getSpriteHeight() / 2 - height / 15);
                            render.setXCurrentFrame(0);
                            render.setYCurrentFrame(0);
                            render.setCurrentFrame(0);
                            render.setFrameToDraw(new Rect(0, 0, render.getFrameWidth(), render.getFrameHeight()));
                    }
                    break;
                case "idle right":
                default:
                    switch (transition) {
                        case "idle":
                            controller.setReacting(false);
                            render.setID(transition);
                            render.setXDimension(3.028);
                            render.setYDimension(5);
                            render.setLeft(0);
                            render.setTop(0);
                            render.setRight(3.028);
                            render.setBottom(5);
                            render.setXFrameCount(4);
                            render.setYFrameCount(1);
                            render.setFrameCount(4);
                            render.setDirection("forwards");
                            render.setMethod("mirror loop");
                            xSpriteRes = xRes * render.getFrameCount() / 2;
                            ySpriteRes = yRes * render.getFrameCount() / 2;
                            render.setSpriteSheet(decodeSampledBitmapFromResource(res, R.mipmap.spritesheet_samurai_idle_mirror_norm, xSpriteRes, ySpriteRes));
                            render.setFrameWidth(render.getSpriteSheet().getWidth() / render.getXFrameCount());
                            render.setFrameHeight(render.getSpriteSheet().getHeight() / render.getYFrameCount());
                            render.setFrameScale((width / 8f) / (double)render.getFrameWidth()); // scale = goal width / original width
                            render.setSpriteWidth((int)(render.getFrameWidth() * render.getFrameScale())); // width = original width * scale
                            render.setSpriteHeight((int)(render.getFrameHeight() * render.getFrameScale())); // height = original height * scale
                            render.setWhereToDraw(new RectF((float) controller.getXPos(), (float) controller.getYPos(), (float) controller.getXPos() + render.getSpriteWidth(), (float) controller.getYPos() + render.getSpriteHeight()));
                            break;
                        case "skip":
                            break;
                        case "init":
                        default:
                            render = new Sprite();
                            controller.setXDelta(0);
                            controller.setYDelta(0);
                            refreshEntity("idle");
                            transition = "idle";
                            controller.setXPos(controller.getXInit() - render.getSpriteWidth() / 2);
                            controller.setYPos(controller.getYInit() - render.getSpriteHeight() / 2 - 2 * height / 15);
                            render.setXCurrentFrame(0);
                            render.setYCurrentFrame(0);
                            render.setCurrentFrame(0);
                            render.setFrameToDraw(new Rect(0, 0, render.getFrameWidth(), render.getFrameHeight()));
                    }
            }
            updateBoundingBox();
            controller.setEntity(this);
            controller.setTransition(transition);
        } catch(NullPointerException e) {
            refreshEntity(transition);
        }

    }

}