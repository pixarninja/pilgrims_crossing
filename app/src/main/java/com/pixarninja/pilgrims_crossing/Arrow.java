package com.pixarninja.pilgrims_crossing;

import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.RectF;
import java.util.LinkedHashMap;
import java.util.Random;

public class Arrow extends SpriteProp {

    public Arrow(Resources res, int width, int height, int xRes, int yRes, String ID) {

        super();

        this.controller = new SpriteController();
        Random random = new Random();

        this.controller.setID(ID);
        this.res = res;
        this.width = width;
        this.height = height;
        this.xRes = xRes;
        this.yRes = yRes;
        this.controller.setXDelta(0);
        this.controller.setYDelta(23);
        this.controller.setXInit(random.nextDouble() * width);
        this.controller.setYInit(-height);
        this.controller.setXPos(this.controller.getXInit());
        this.controller.setYPos(this.controller.getYInit());
        this.spriteScale = 1;
        this.xDimension = 1;
        this.yDimension = 1;
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
                    render.setXFrameCount(6);
                    render.setYFrameCount(1);
                    render.setFrameCount(6);
                    render.setMethod("die");
                    render.setDirection("forwards");
                    spriteScale = 0.12;
                    xSpriteRes = xRes * render.getXFrameCount();
                    ySpriteRes = yRes * render.getYFrameCount();
                    render.setSpriteSheet(decodeSampledBitmapFromResource(res, R.mipmap.spritesheet_arrow_destroyed, (int)(xSpriteRes * spriteScale), (int)(ySpriteRes * spriteScale)));
                    render.setFrameWidth(render.getSpriteSheet().getWidth() / render.getXFrameCount());
                    render.setFrameHeight(render.getSpriteSheet().getHeight() / render.getYFrameCount());
                    render.setFrameScale((width * spriteScale) / (double)render.getFrameWidth()); // scale = goal width / original width
                    render.setSpriteWidth((int)(render.getFrameWidth() * render.getFrameScale())); // width = original width * scale
                    render.setSpriteHeight((int)(render.getFrameHeight() * render.getFrameScale())); // height = original height * scale
                    controller.setXPos(controller.getXPos() - render.getSpriteWidth() / 2);
                    render.setWhereToDraw(new RectF((float) controller.getXPos(), (float) controller.getYPos(), (float) controller.getXPos() + render.getSpriteWidth(), (float) controller.getYPos() + render.getSpriteHeight()));
                    break;
                case "falling":
                    this.controller.setReacting(false);
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
                    spriteScale = 0.02;
                    xSpriteRes = xRes * render.getXFrameCount();
                    ySpriteRes = yRes * render.getYFrameCount();
                    render.setSpriteSheet(decodeSampledBitmapFromResource(res, R.mipmap.spritesheet_arrow_falling_loop, (int)(xSpriteRes * spriteScale), (int)(ySpriteRes * spriteScale)));
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
                    refreshEntity("falling");
                    ID = "falling";
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

}
