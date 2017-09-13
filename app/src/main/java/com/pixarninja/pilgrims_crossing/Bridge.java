package com.pixarninja.pilgrims_crossing;

import android.content.res.Resources;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.LinkedHashMap;
import java.util.Random;

public class Bridge extends SpriteProp{

    public Bridge(SpriteView spriteView, Resources res, double percentOfScreen, int width, int height, int xRes, int yRes,
                  double xDelta, double yDelta, double xInit, double yInit, int xFrameCount, int yFrameCount, int frameCount,
                  double xDimension, double yDimension, double spriteScale,
                  double left, double top, double right, double bottom, SpriteController controller, String ID, String transition) {

        super(spriteView, res, percentOfScreen, width, height, xRes, yRes, xDelta, yDelta, xInit, yInit, xFrameCount, yFrameCount, frameCount, xDimension, yDimension, spriteScale, left, top, right, bottom, controller, ID, transition);

        if(controller == null) {
            this.controller = new SpriteController();
        }
        else {
            this.controller = controller;
        }this.controller.setID(ID);
        this.spriteView = spriteView;
        this.res = res;
        this.percentOfScreen = percentOfScreen;
        this.width = width;
        this.height = height;
        this.xRes = xRes;
        this.yRes = yRes;
        this.controller.setXDelta(xDelta);
        this.controller.setYDelta(yDelta);
        this.controller.setXInit(xInit);
        this.controller.setYInit(yInit);
        this.controller.setXPos(xInit);
        this.controller.setYPos(yInit);
        this.controller.setReacting(false);
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
    public void refreshEntity(String ID) {

        int xSpriteRes;
        int ySpriteRes;

        /* setup sprite via parsing */
        ID = parseID(ID);

        try {
            switch (ID) {
                case "destroyed":
                case "complete":
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
                    xSpriteRes = 2 * xRes / render.getXFrameCount();
                    ySpriteRes = 2 * yRes / render.getYFrameCount();
                    render.setSpriteSheet(decodeSampledBitmapFromResource(res, R.mipmap.prop_bridge, (int) (xSpriteRes * spriteScale), (int) (ySpriteRes * spriteScale)));
                    render.setFrameWidth(render.getSpriteSheet().getWidth() / render.getXFrameCount());
                    render.setFrameHeight(render.getSpriteSheet().getHeight() / render.getYFrameCount());
                    render.setFrameScale(spriteScale * height * percentOfScreen / render.getFrameHeight());
                    render.setSpriteWidth(width);
                    render.setSpriteHeight((int) (render.getFrameHeight() * render.getFrameScale()));
                    render.setWhereToDraw(new RectF((float) controller.getXPos(), (float) controller.getYPos(), (float) controller.getXPos() + render.getSpriteWidth(), (float) controller.getYPos() + render.getSpriteHeight()));
                    break;
                case "init":
                default:
                    render = new Sprite();
                    refreshEntity("complete");
                    ID = "complete";
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
    public void getCurrentFrame(){

        long time  = System.currentTimeMillis();
        if ( time > controller.getLastFrameChangeTime() + controller.getFrameRate()) {

            controller.setLastFrameChangeTime(time);
            render.setCurrentFrame(render.getCurrentFrame() + 1);
            render.setXCurrentFrame(render.getXCurrentFrame() + 1);
            if ((render.getXCurrentFrame() >= render.getXFrameCount()) || (render.getCurrentFrame() >= render.getFrameCount())) {
                render.setYCurrentFrame(render.getYCurrentFrame() + 1);
                if ((render.getYCurrentFrame() >= render.getYFrameCount()) || (render.getCurrentFrame() >= render.getFrameCount())) {
                    if(render.getMethod().equals("die")) {
                        controller.setAlive(false);
                    }
                    /* loop */
                    else {
                        render.setYCurrentFrame(0);
                        render.setCurrentFrame(0);
                    }
                }
                render.setXCurrentFrame(0);
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
    public void updateView() {

        controller.setXPos(controller.getXPos() + controller.getXDelta());
        controller.setYPos(controller.getYPos() + controller.getYDelta());

        if(controller.getYPos() > height) {
            Random random = new Random();
            controller.setXPos(random.nextDouble() * width);
            controller.setYPos(-controller.getEntity().getSprite().getSpriteHeight());
            controller.setXDelta(0);
            controller.setYDelta(0);
        }

        if(controller.getYPos() < 0) {
            Random random = new Random();
            if((random.nextInt(100) % 40) == 0) {
                controller.setXDelta(0);
                controller.setYDelta(15);
            }
        }

        render.setWhereToDraw(new RectF((float) controller.getXPos(), (float) controller.getYPos(), (float) controller.getXPos() + render.getSpriteWidth(), (float) controller.getYPos() + render.getSpriteHeight()));
        getCurrentFrame();
        updateBoundingBox();

    }

    @Override
    public LinkedHashMap<String, SpriteController> onCollisionEvent(LinkedHashMap.Entry<String, SpriteController> entry, LinkedHashMap<String, SpriteController> controllerMap) {

        LinkedHashMap<String, SpriteController> map = new LinkedHashMap<>();

        if (!entry.getValue().getReacting() && (entry.getValue().getEntity().getSprite().getBoundingBox() != null)) {
            RectF entryBox = entry.getValue().getEntity().getSprite().getBoundingBox();
            for (LinkedHashMap.Entry<String, SpriteController> test : controllerMap.entrySet()) {
                if (test.getKey().contains("Arrow") && !test.getValue().getReacting()) {
                    if (test.getValue().getEntity().getSprite().getBoundingBox() != null) {
                        RectF compareBox = test.getValue().getEntity().getSprite().getBoundingBox();
                        /* if the objects intersect, find where they intersect for the entry bounding box */
                        if (entryBox.intersect(compareBox)) {
                            /* call the comparison's collision handler */
                            test.getValue().getEntity().onCollisionEvent(test, controllerMap);
                        }
                    }
                }
            }
        }

        return map;

    }

}
