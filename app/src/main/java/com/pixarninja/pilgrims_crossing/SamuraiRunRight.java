package com.pixarninja.pilgrims_crossing;

import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.RectF;
import java.util.LinkedHashMap;
import java.util.Random;

public class SamuraiRunRight extends SpriteCharacter {

    public SamuraiRunRight(SpriteView spriteView, Resources res, double percentOfScreen, int xRes, int yRes, int width, int height, SpriteController controller, String ID) {

        if(controller == null) {
            this.controller = new SpriteController();
        }
        else {
            this.controller = controller;
        }
        this.controller.setXInit(width / 2);
        this.controller.setYInit(height / 2);
        this.controller.setID("run right");
        this.controller.setReacting(false);
        this.spriteView = spriteView;
        this.res = res;
        this.percentOfScreen = percentOfScreen;
        this.xRes = xRes;
        this.yRes = yRes;
        this.width = width;
        this.height = height;
        count = 0;

        refreshEntity(ID);

    }

    @Override
    public void refreshEntity(String ID) {

        int xSpriteRes;
        int ySpriteRes;

        /* setup sprite via parsing */
        ID = parseID(ID);

        try {
            switch (ID) {
                case "bottomLeft":
                case "left":
                case "topLeft":
                    controller.setReacting(true);
                    render.setID(ID);
                    render.setXDimension(6.944);
                    render.setYDimension(2.917);
                    render.setLeft(0);
                    render.setTop(0);
                    render.setRight(6.944);
                    render.setBottom(2.917);
                    render.setXFrameCount(4);
                    render.setYFrameCount(2);
                    render.setFrameCount(8);
                    render.setDirection("forwards");
                    render.setMethod("mirror");
                    xSpriteRes = 2 * xRes / render.getXFrameCount();
                    ySpriteRes = 2 * yRes / render.getYFrameCount();
                    spriteScale = 0.07;
                    render.setSpriteSheet(decodeSampledBitmapFromResource(res, R.mipmap.spritesheet_box_rotate_right_blue_mirror, (int)(xSpriteRes * spriteScale), (int)(ySpriteRes * spriteScale)));
                    render.setFrameWidth(render.getSpriteSheet().getWidth() / render.getXFrameCount());
                    render.setFrameHeight(render.getSpriteSheet().getHeight() / render.getYFrameCount());
                    render.setFrameScale(spriteScale * height * percentOfScreen / render.getFrameHeight());
                    render.setSpriteWidth((int)(render.getFrameWidth() * render.getFrameScale()));
                    render.setSpriteHeight((int)(render.getFrameHeight() * render.getFrameScale()));
                    render.setWhereToDraw(new RectF((float)controller.getXPos(), (float)controller.getYPos(), (float)controller.getXPos() + render.getSpriteWidth(), (float)controller.getYPos() + render.getSpriteHeight()));
                    break;
                case "top":
                    controller.setReacting(true);
                    render.setID(ID);
                    render.setXDimension(5.944);
                    render.setYDimension(3.444);
                    render.setLeft(0);
                    render.setTop(0);
                    render.setRight(5.944);
                    render.setBottom(3.444);
                    render.setXFrameCount(4);
                    render.setYFrameCount(2);
                    render.setFrameCount(8);
                    render.setDirection("forwards");
                    render.setMethod("mirror");
                    xSpriteRes = 2 * xRes / render.getXFrameCount();
                    ySpriteRes = 2 * yRes / render.getYFrameCount();
                    spriteScale = 0.07;
                    render.setSpriteSheet(decodeSampledBitmapFromResource(res, R.mipmap.spritesheet_box_rotate_up_blue_mirror, (int)(xSpriteRes * spriteScale), (int)(ySpriteRes * spriteScale)));
                    render.setFrameWidth(render.getSpriteSheet().getWidth() / render.getXFrameCount());
                    render.setFrameHeight(render.getSpriteSheet().getHeight() / render.getYFrameCount());
                    render.setFrameScale(spriteScale * height * percentOfScreen / render.getFrameHeight());
                    render.setSpriteWidth((int)(render.getFrameWidth() * render.getFrameScale()));
                    render.setSpriteHeight((int)(render.getFrameHeight() * render.getFrameScale()));
                    render.setWhereToDraw(new RectF((float)controller.getXPos(), (float)controller.getYPos(), (float)controller.getXPos() + render.getSpriteWidth(), (float)controller.getYPos() + render.getSpriteHeight()));
                    break;
                case "bottomRight":
                case "right":
                case "topRight":
                    controller.setReacting(true);
                    controller.setReacting(true);
                    render.setID(ID);
                    render.setXDimension(6.889);
                    render.setYDimension(3);
                    render.setLeft(0);
                    render.setTop(0);
                    render.setRight(6.889);
                    render.setBottom(3);
                    render.setXFrameCount(4);
                    render.setYFrameCount(2);
                    render.setFrameCount(8);
                    render.setDirection("forwards");
                    render.setMethod("mirror");
                    xSpriteRes = 2 * xRes / render.getXFrameCount();
                    ySpriteRes = 2 * yRes / render.getYFrameCount();
                    spriteScale = 0.07;
                    render.setSpriteSheet(decodeSampledBitmapFromResource(res, R.mipmap.spritesheet_box_rotate_left_blue_mirror, (int)(xSpriteRes * spriteScale), (int)(ySpriteRes * spriteScale)));
                    render.setFrameWidth(render.getSpriteSheet().getWidth() / render.getXFrameCount());
                    render.setFrameHeight(render.getSpriteSheet().getHeight() / render.getYFrameCount());
                    render.setFrameScale(spriteScale * height * percentOfScreen / render.getFrameHeight());
                    render.setSpriteWidth((int)(render.getFrameWidth() * render.getFrameScale()));
                    render.setSpriteHeight((int)(render.getFrameHeight() * render.getFrameScale()));
                    render.setWhereToDraw(new RectF((float)controller.getXPos(), (float)controller.getYPos(), (float)controller.getXPos() + render.getSpriteWidth(), (float)controller.getYPos() + render.getSpriteHeight()));
                    break;
                case "bottom":
                    controller.setReacting(true);
                    render.setID(ID);
                    render.setXDimension(6.056);
                    render.setYDimension(3.389);
                    render.setLeft(0);
                    render.setTop(0);
                    render.setRight(6.056);
                    render.setBottom(3.389);
                    render.setXFrameCount(4);
                    render.setYFrameCount(2);
                    render.setFrameCount(8);
                    render.setDirection("forwards");
                    render.setMethod("mirror");
                    xSpriteRes = 2 * xRes / render.getXFrameCount();
                    ySpriteRes = 2 * yRes / render.getYFrameCount();
                    spriteScale = 0.07;
                    render.setSpriteSheet(decodeSampledBitmapFromResource(res, R.mipmap.spritesheet_box_rotate_down_blue_mirror, (int)(xSpriteRes * spriteScale), (int)(ySpriteRes * spriteScale)));
                    render.setFrameWidth(render.getSpriteSheet().getWidth() / render.getXFrameCount());
                    render.setFrameHeight(render.getSpriteSheet().getHeight() / render.getYFrameCount());
                    render.setFrameScale(spriteScale * height * percentOfScreen / render.getFrameHeight());
                    render.setSpriteWidth((int)(render.getFrameWidth() * render.getFrameScale()));
                    render.setSpriteHeight((int)(render.getFrameHeight() * render.getFrameScale()));
                    render.setWhereToDraw(new RectF((float)controller.getXPos(), (float)controller.getYPos(), (float)controller.getXPos() + render.getSpriteWidth(), (float)controller.getYPos() + render.getSpriteHeight()));
                    break;
                case "idleRight":
                    controller.setReacting(false);
                    render.setID(ID);
                    render.setXDimension(4.778);
                    render.setYDimension(5);
                    render.setLeft(0);
                    render.setTop(0);
                    render.setRight(4.778);
                    render.setBottom(5);
                    render.setXFrameCount(4);
                    render.setYFrameCount(4);
                    render.setFrameCount(16);
                    render.setDirection("forwards");
                    render.setMethod("idleRight");
                    xSpriteRes = xRes * render.getFrameCount() / 2;
                    ySpriteRes = yRes * render.getFrameCount() / 2;
                    spriteScale = 0.20;
                    render.setSpriteSheet(decodeSampledBitmapFromResource(res, R.mipmap.spritesheet_samurai_run_right_loop_norm, (int)(xSpriteRes * spriteScale), (int)(ySpriteRes * spriteScale)));
                    render.setFrameWidth(render.getSpriteSheet().getWidth() / render.getXFrameCount());
                    render.setFrameHeight(render.getSpriteSheet().getHeight() / render.getYFrameCount());
                    render.setFrameScale(spriteScale * height * percentOfScreen / render.getFrameHeight());
                    render.setSpriteWidth((int)(render.getFrameWidth() * render.getFrameScale()));
                    render.setSpriteHeight((int)(render.getFrameHeight() * render.getFrameScale()));
                    render.setWhereToDraw(new RectF((float)controller.getXPos(), (float)controller.getYPos(), (float)controller.getXPos() + render.getSpriteWidth(), (float)controller.getYPos() + render.getSpriteHeight()));
                    break;
                case "idleLeft":
                    controller.setReacting(false);
                    render.setID(ID);
                    render.setXDimension(4.778);
                    render.setYDimension(5);
                    render.setLeft(0);
                    render.setTop(0);
                    render.setRight(4.778);
                    render.setBottom(5);
                    render.setXFrameCount(4);
                    render.setYFrameCount(4);
                    render.setFrameCount(16);
                    render.setDirection("flipped");
                    render.setMethod("idleLeft");
                    xSpriteRes = xRes * render.getFrameCount() / 2;
                    ySpriteRes = yRes * render.getFrameCount() / 2;
                    spriteScale = 0.20;
                    render.setSpriteSheet(decodeSampledBitmapFromResource(res, R.mipmap.spritesheet_samurai_run_right_loop_norm, (int)(xSpriteRes * spriteScale), (int)(ySpriteRes * spriteScale)));
                    render.setFrameWidth(render.getSpriteSheet().getWidth() / render.getXFrameCount());
                    render.setFrameHeight(render.getSpriteSheet().getHeight() / render.getYFrameCount());
                    render.setFrameScale(spriteScale * height * percentOfScreen / render.getFrameHeight());
                    render.setSpriteWidth((int)(render.getFrameWidth() * render.getFrameScale()));
                    render.setSpriteHeight((int)(render.getFrameHeight() * render.getFrameScale()));
                    render.setWhereToDraw(new RectF((float)controller.getXPos(), (float)controller.getYPos(), (float)controller.getXPos() + render.getSpriteWidth(), (float)controller.getYPos() + render.getSpriteHeight()));
                    break;
                case "skip":
                    break;
                case "init":
                default:
                    render = new Sprite();
                    controller.setXDelta(0);
                    controller.setYDelta(0);
                    refreshEntity("idleRight");
                    ID = "idleRight";
                    controller.setXPos(controller.getXInit() - render.getSpriteWidth() / 2);
                    controller.setYPos(controller.getYInit() - render.getSpriteHeight() / 2 - height / 15);
                    render.setXCurrentFrame(0);
                    render.setYCurrentFrame(0);
                    render.setCurrentFrame(0);
                    render.setFrameToDraw(new Rect(0, 0, render.getFrameWidth(), render.getFrameHeight()));
                    render.setWhereToDraw(new RectF((float)controller.getXPos(), (float)controller.getYPos(), (float)controller.getXPos() + render.getSpriteWidth(), (float)controller.getYPos() + render.getSpriteHeight()));
            }
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

            if(render.getDirection().equals("forwards")) {
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
                            if(render.getMethod().equals("once")) {
                                refreshEntity("idleRight");
                                count = 0;
                            }
                            else if(render.getMethod().equals("mirror") || render.getMethod().equals("poked")) {
                                render.setCurrentFrame(render.getFrameCount());
                                render.setXCurrentFrame(render.getXFrameCount() - 1);
                                render.setYCurrentFrame(render.getYFrameCount() - 1);
                                count++;
                            }
                            /* loop or idle */
                            else {
                                controller.setReacting(false);
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
                            refreshEntity("idleRight");
                            count = 0;
                        }
                        if (count > 0) {
                            render.setXCurrentFrame(render.getXFrameCount() - 1);
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
                    render.setCurrentFrame(render.getCurrentFrame() + delta);
                    render.setXCurrentFrame(render.getXCurrentFrame() + delta);
                    if ((render.getXCurrentFrame() < 0) || (render.getCurrentFrame() < 0)) {
                        render.setYCurrentFrame(render.getYCurrentFrame() + delta);
                        if ((render.getYCurrentFrame() < 0) || (render.getCurrentFrame() < 0)) {
                            if(render.getMethod().equals("once")) {
                                refreshEntity("idleLeft");
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
                                controller.setReacting(false);
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
                            refreshEntity("idleLeft");
                            count = 0;
                        }
                        if (count > 0) {
                            render.setXCurrentFrame(0);
                        }
                    }
                }
            }

            else if(render.getDirection().equals("backwards")) {
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
                            if(render.getMethod().equals("once")) {
                                refreshEntity("idleLeft");
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
                            refreshEntity("idleLeft");
                            count = 0;
                        }
                        if (count > 0) {
                            render.setXCurrentFrame(0);
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
    public void onTouchEvent(SpriteView spriteView, LinkedHashMap.Entry<String, SpriteController> entry, LinkedHashMap<String, SpriteController> controllerMap, boolean move, boolean jump, float xTouchedPos, float yTouchedPos) {}

}